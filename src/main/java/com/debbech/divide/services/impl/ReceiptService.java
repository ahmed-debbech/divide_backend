package com.debbech.divide.services.impl;

import com.debbech.divide.data.receipt.ReceiptDataRepo;
import com.debbech.divide.data.receipt.ReceiptItemRepo;
import com.debbech.divide.data.receipt.ReceiptRepo;
import com.debbech.divide.entity.User;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.processor.OrderExporter;
import com.debbech.divide.processor.OrderProcessor;
import com.debbech.divide.processor.models.Order;
import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.utils.ObjectConverters;
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
    private ReceiptItemRepo receiptItemRepo;
    @Autowired
    private ReceiptDataRepo receiptDataRepo;
    @Autowired
    private IUserService userService;


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

        return r;
    }

}
