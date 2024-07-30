package com.debbech.divide.divisor.steps;

import com.debbech.divide.ApplicationContextProvider;
import com.debbech.divide.entity.division.DivItem;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.services.impl.DivisionService;
import com.debbech.divide.services.impl.ReceiptService;
import com.debbech.divide.services.interfaces.IDivisionService;
import com.debbech.divide.services.interfaces.IReceiptService;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavingStep implements IDivisionStep {

    private IReceiptService receiptService;
    private IDivisionService divisionService;

    public SavingStep(){
        this.receiptService = ApplicationContextProvider.getApplicationContext().getBean(ReceiptService.class);
        this.divisionService = ApplicationContextProvider.getApplicationContext().getBean(DivisionService.class);
    }

    @Override
    public void execute(Long id, Division division) throws Exception {
        Receipt r = receiptService.getOneById(id);
        division.setSubmissionTs(String.valueOf(Instant.now().toEpochMilli()));
        Division div = divisionService.save(division);
        r.setDivision(div);
        receiptService.save(r);
    }
}
