package com.debbech.divide.services.impl;

import com.debbech.divide.data.receipt.ReceiptDataRepo;
import com.debbech.divide.data.receipt.ReceiptItemRepo;
import com.debbech.divide.data.receipt.ReceiptRepo;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.entity.receipt.ReceiptItem;
import com.debbech.divide.processor.OrderExporter;
import com.debbech.divide.processor.OrderProcessor;
import com.debbech.divide.processor.models.Order;
import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.utils.ObjectConverters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Override
    public String startProcessing(String picture) throws Exception {
        String id = orderProcessor.start(picture);
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
        /*for(Map.Entry<String, Order> en: orderExporter.exportedOrders){
            Receipt r = ObjectConverters.convert(en.getKey(), en.getValue());
            for(ReceiptItem ri : r.getReceiptData().getLineItems()){
                receiptItemRepo.save(ri);
            }
            receiptDataRepo.save(r.getReceiptData());
            receiptRepo.save(r);
        }*/
        for(Map.Entry<String, Order> en: orderExporter.exportedOrders) {
            Receipt r = ObjectConverters.convert(en.getKey(), en.getValue());
            //  receiptDataRepo.save(r.getReceiptData());
            receiptRepo.save(r);
        }
        orderExporter.exportedOrders.clear();
    }

}
