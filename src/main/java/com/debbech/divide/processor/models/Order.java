package com.debbech.divide.processor.models;

import lombok.Data;

@Data
public class Order {

    private String receiptImageFileName;
    private Processing isProcessing;
}
