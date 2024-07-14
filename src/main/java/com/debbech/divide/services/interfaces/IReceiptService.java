package com.debbech.divide.services.interfaces;

import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.receipt.Receipt;

public interface IReceiptService {
    String startProcessing(String picture) throws Exception;
    boolean checkProcessing(String id) throws Exception;

    void persistToDb();

    Receipt getOne(String id) throws Exception;

    Receipt getOneById(Long id) throws Exception;

    void divide(Long id, Division division) throws Exception;
}
