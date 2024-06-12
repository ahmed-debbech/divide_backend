package com.debbech.divide.services.impl;

import com.debbech.divide.processor.OrderProcessor;
import com.debbech.divide.services.interfaces.IReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiptService implements IReceiptService {

    @Autowired
    private OrderProcessor orderProcessor;

    @Override
    public String startProcessing(String picture) {
        String id = orderProcessor.launch(picture);
        return id;
    }
}
