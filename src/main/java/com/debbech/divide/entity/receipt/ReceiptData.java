package com.debbech.divide.entity.receipt;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "receipt_data")
public class ReceiptData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String reference_number;
    @Column(length = 512)
    private String imgTumbUrl;
    private String thumbnailBytes;
    @Column(length = 512)
    private String deliveryDate;
    private Double discount;
    private Double subtotal;
    private Double total;
    @Column(length = 512)
    private String vendorName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_data_id")
    private List<ReceiptItem> lineItems;
}
