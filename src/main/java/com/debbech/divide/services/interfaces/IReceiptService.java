package com.debbech.divide.services.interfaces;

public interface IReceiptService {
    String startProcessing(String picture) throws Exception;
    String finishProcessing(String picture) throws Exception;
}
