package com.debbech.divide.web.models;

import com.debbech.divide.entity.User;
import com.debbech.divide.entity.enumer.Processing;
import com.debbech.divide.entity.receipt.ReceiptData;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiptDto {
    private Long id;
    private String ourReference;
    private Processing isProcessing;
    private String failureReason;
    private LocalDateTime createdAt;
    private String initiator;
    private ReceiptData receiptData;
}
