package com.debbech.divide.processor;

import com.debbech.divide.processor.data.IOrderLiveDb;
import com.debbech.divide.processor.models.Order;
import com.debbech.divide.processor.models.Processing;
import com.debbech.divide.processor.scanner.IScanner;
import com.debbech.divide.services.impl.AuthService;
import com.debbech.divide.utils.Base64Parser;
import com.debbech.divide.utils.SystemCall;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class OrderProcessor {

    @Autowired
    private IOrderLiveDb liveDb;
    @Autowired
    private SystemCall systemCall;
    @Autowired
    private IScanner scanner;

    public String start(String picture) throws Exception {
        String id = OrderProcessorUtils.generateId();

        byte[] img = Base64Parser.base64ToBytes(picture);
        String fileName = OrderProcessorUtils.generateFileName();
        systemCall.createFile(fileName, img);

        Order order = new Order();
        order.setIsProcessing(Processing.NOT_READY);
        order.setReceiptImageFileName(fileName);

        liveDb.set(id, order);
        return id;
    }

    public boolean check(String id) throws Exception {
        Order order = liveDb.get(id);

        if(order == null) return true;
        if(order.getIsProcessing() == Processing.DONE) return true;

        return false;
    }

    @Scheduled(cron = "*/10 * * * * *") //every 10 secs
    public void executeAll(){
        log.info("started executing all orders");
        List<Map.Entry<String,Order>> list = liveDb.getAll();
        if(list == null){
            log.info("done executing all orders .. list is empty");
            return;
        }

        for(int i =0; i<=list.size()-1; i++){
            Order or = list.get(i).getValue();
            if(or.getIsProcessing() == Processing.NOT_READY){
                or.setIsProcessing(Processing.ONGOING);
                log.info("setting order {} to ONGOING", list.get(i).getKey());
                liveDb.set(list.get(i).getKey(), or);
                try {
                    JSONObject result = scanner.execute(systemCall.getFullPath(or.getReceiptImageFileName()));
                    or.setIsProcessing(Processing.DONE);
                    log.info("setting order {} to DONE", list.get(i).getKey());
                    liveDb.set(list.get(i).getKey(), or);
                } catch (Exception e) {
                    log.info("error while scanning order {}", list.get(i).getKey());
                    or.setIsProcessing(Processing.FAILED);
                    log.info("setting order {} to FAILED", list.get(i).getKey());
                    liveDb.set(list.get(i).getKey(), or);
                }
            }
        }
        log.info("done executing all orders");
    }

    @Scheduled(cron = "0 * * * * *") //every 1 min
    public void cleanUp(){
        log.info("cleaning up...");
        List<Map.Entry<String,Order>> list = liveDb.getAll();
        if(list == null){
            log.info("finish cleaning up....");
            return;
        }

        for(Map.Entry<String,Order> o : list){
            if(o.getValue().getIsProcessing() == Processing.DONE){
                log.info("removing {} order from live db", o.getKey());
                liveDb.delete(o.getKey());
            }
        }
        log.info("finish cleaning up....");
    }

}
