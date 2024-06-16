package com.debbech.divide.data.receipt;

import com.debbech.divide.entity.receipt.ReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptItemRepo extends JpaRepository<ReceiptItem, Long> {
}
