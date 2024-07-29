package com.debbech.divide.divisor.steps;

import com.debbech.divide.ApplicationContextProvider;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.services.impl.DivisionService;
import com.debbech.divide.services.impl.ReceiptService;
import com.debbech.divide.services.interfaces.IDivisionService;
import com.debbech.divide.services.interfaces.IReceiptService;

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
        Division div = divisionService.save(division);
        r.setDivision(div);
        receiptService.save(r);
    }
}
