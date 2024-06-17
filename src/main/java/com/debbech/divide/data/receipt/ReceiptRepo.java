package com.debbech.divide.data.receipt;

import com.debbech.divide.entity.receipt.Receipt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Long> {

    @EntityGraph(attributePaths = {"receiptData", "receiptData.lineItems"})
    //@Query(value = "select * from receipt where our_reference=:id limit 1",nativeQuery = true)
    Optional<Receipt> findByOurReference(@Param("id") String id);

}
