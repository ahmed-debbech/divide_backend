package com.debbech.divide.services.interfaces;

import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.processor.models.Order;

public interface IReceiptService {
    String startProcessing(String picture) throws Exception;
    boolean checkProcessing(String id) throws Exception;

    void persistToDb();

    Receipt getOne(String id) throws Exception;
}
