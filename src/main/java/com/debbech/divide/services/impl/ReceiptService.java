package com.debbech.divide.services.impl;


import com.debbech.divide.data.receipt.ReceiptRepo;
import com.debbech.divide.divisor.DivisionStepsExecutor;
import com.debbech.divide.entity.User;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.enumer.Processing;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.processor.OrderExporter;
import com.debbech.divide.processor.OrderProcessor;
import com.debbech.divide.processor.models.Order;
import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.utils.Base64Parser;
import com.debbech.divide.utils.ImageProcessor;
import com.debbech.divide.utils.ObjectConverters;
import com.debbech.divide.utils.SystemCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class ReceiptService implements IReceiptService {

    @Autowired
    private OrderProcessor orderProcessor;
    @Autowired
    private OrderExporter orderExporter;
    @Autowired
    private ReceiptRepo receiptRepo;
    @Autowired
    private IUserService userService;
    @Autowired
    private SystemCall systemCall;

    private DivisionStepsExecutor divisionStepsExecutor;

    @Override
    public String startProcessing(String picture) throws Exception {
        String uidInitiator = AuthService.getLoggedInUser();
        String id = orderProcessor.start(uidInitiator,picture);
        return id;
    }

    @Override
    public boolean checkProcessing(String id) throws Exception {
        return orderProcessor.check(id);
    }

    @Scheduled(fixedDelay = 1000)
    @Override
    public void persistToDb() {
        if(orderExporter.exportedOrders.isEmpty()) return;

        for(Map.Entry<String, Order> en: orderExporter.exportedOrders) {
            Receipt r = ObjectConverters.convert(en.getKey(), en.getValue());
            try {
                User u = userService.searchByUid(r.getInitiator().getUid());
                r.setInitiator(u);
                r.setCreatedAt(LocalDateTime.now());
                receiptRepo.save(r);
            } catch (Exception e) {
                return;
            }
        }
        orderExporter.exportedOrders.clear();
    }

    @Override
    public Receipt getOne(String id) throws Exception {

        Receipt r = receiptRepo.findByOurReference(id).orElse(null);
        if (r == null) throw new Exception("can't find receipt");
        User you = userService.searchByUid(AuthService.getLoggedInUser());
        if(!r.getInitiator().getUid().equals(you.getUid())) throw new Exception("not your receipt");

        if(r.getIsProcessing().equals(Processing.FAILED)) return r;

        try {
            r.getReceiptData().setThumbnailBytes(Base64Parser.bytesToBase64(ImageProcessor.getThumbnail(systemCall.getFile(you.getUid() + "-" + id))));
        }catch (Exception e){
            r.getReceiptData().setThumbnailBytes(null);
        }
        return r;
    }

    @Override
    public Receipt getOneById(Long id) throws Exception {

        Receipt r = receiptRepo.findById(id).orElse(null);
        if (r == null) throw new Exception("can't find receipt");

        return r;
    }

    @Override
    public void divide(Long id, Division division) throws Exception {
        divisionStepsExecutor = new DivisionStepsExecutor();
        divisionStepsExecutor.executeStepsInOrder(id, division);
    }

    @Override
    public void save(Receipt r) {
        receiptRepo.save(r);
    }

}
