package com.debbech.divide.entity.division;


import com.debbech.divide.entity.receipt.ReceiptItem;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "div_items")
public class DivItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "div_item_id")
    private List<Participant> participantsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_item_id", referencedColumnName = "id")
    private ReceiptItem receiptItem;
}
