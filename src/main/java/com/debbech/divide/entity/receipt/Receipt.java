package com.debbech.divide.entity.receipt;

import com.debbech.divide.entity.User;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.enumer.Processing;
import com.debbech.divide.processor.models.ExtractedData;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5)
    private String ourReference;
    @Column(length = 512)
    private String receiptImageFileName;
    @Enumerated(EnumType.STRING)
    private Processing isProcessing;
    @Column(length = 512)
    private String failureReason;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "uid_initiator")
    private User initiator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_data_id", referencedColumnName = "id")
    private ReceiptData receiptData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id", referencedColumnName = "id")
    private Division division;
}
