package com.debbech.divide.processor.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class ExtractedItem {

    private String description;
    private Double discount;
    private Double total;
    private String fullDescription;
    private String text;
    private Double quantity;
    private Double weight;
    private Double tax;

    public ExtractedItem(ExtractedItem extractedItem) {
        this.description = extractedItem.getDescription();
        this.discount = extractedItem.getDiscount();
        this.total = extractedItem.getTotal();
        this.fullDescription = extractedItem.getFullDescription();
        this.text = extractedItem.getText();
        this.quantity = extractedItem.getQuantity();
        this.weight = extractedItem.getWeight();
        this.tax = extractedItem.getTax();
    }
}
