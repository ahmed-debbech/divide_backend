package com.debbech.divide.data.receipt;

import com.debbech.divide.entity.receipt.ReceiptData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptDataRepo extends JpaRepository<ReceiptData, Long> {
}
