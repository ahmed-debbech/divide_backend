package com.debbech.divide.processor;

import com.debbech.divide.processor.data.IOrderLiveDb;
import com.debbech.divide.processor.models.Order;
import com.debbech.divide.processor.scanner.IScanner;
import com.debbech.divide.services.impl.AuthService;
import com.debbech.divide.utils.Base64Parser;
import com.debbech.divide.utils.SystemCall;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
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
        String id = OrderProcessor.generateId();
        byte[] img = Base64Parser.base64ToBytes(picture);
        String fileName = generateFileName();
        systemCall.createFile(fileName, img);
        Order order = new Order();
        order.setReceiptImageFileName(fileName);
        liveDb.set(id, order);
        return id;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void executeAll(){

    }

    public String execute(String id) throws Exception {
        Order order = liveDb.get(id);
        String name = order.getReceiptImageFileName();
        String image = systemCall.getFullPath(name);
        scanner.execute(image);
        return id;
    }

    public String cleanUp(String picture) throws Exception {
        String id = OrderProcessor.generateId();
        byte[] img = Base64Parser.base64ToBytes(picture);
        String fileName = generateFileName();
        systemCall.createFile(fileName, img);
        Order order = new Order();
        order.setReceiptImageFileName(fileName);
        liveDb.set(id, order);
        return id;
    }

    private String generateFileName(){
        String uid = AuthService.getLoggedInUser();
        return uid + LocalDateTime.now();
    }


    private static String generateId(){
        StringBuilder sb = new StringBuilder();
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = lowercase.toUpperCase();
        String digits = "0123456789";

        String allChars = lowercase + uppercase + digits;

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
