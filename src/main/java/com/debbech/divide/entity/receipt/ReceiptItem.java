package com.debbech.divide.entity.receipt;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "receipt_item")
public class ReceiptItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String description;
    private Double discount;
    private Double total;
    @Column(length = 512)
    private String fullDescription;
    @Column(length = 512)
    private String text;
    private Double quantity;
    private Double weight;
    private Double tax;

}
