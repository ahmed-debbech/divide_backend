package com.debbech.divide.processor.models;

import lombok.Data;

@Data
public class ExtractedItem {

    private String description;
    private Double discount;
    private Double total;
    private String fullDescription;
    private String text;
    private Double quantity;
    private Double weight;
    private Double tax;
}
