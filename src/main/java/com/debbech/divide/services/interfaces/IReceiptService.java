package com.debbech.divide.services.interfaces;

public interface IReceiptService {
    String startProcessing(String picture) throws Exception;
    boolean checkProcessing(String id) throws Exception;
}
