package com.debbech.divide.processor.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ExtractedData {
    private String reference_number;
    private String imgTumbUrl;
    private String deliveryDate;
    private Double discount;
    private Double subtotal;
    private Double total;
    private String vendorName;
    private List<ExtractedItem> lineItems;
}
