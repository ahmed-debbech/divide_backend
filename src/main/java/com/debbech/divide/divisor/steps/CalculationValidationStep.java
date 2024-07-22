package com.debbech.divide.divisor.steps;

import com.debbech.divide.ApplicationContextProvider;
import com.debbech.divide.entity.division.DivItem;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.division.Participant;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.entity.receipt.ReceiptItem;
import com.debbech.divide.services.impl.ReceiptService;
import com.debbech.divide.services.interfaces.IReceiptService;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//the class that does all the calculation validations of a division for a certain receipt
public class CalculationValidationStep implements IDivisionStep {

    private IReceiptService receiptService;

    public CalculationValidationStep(){
        this.receiptService = ApplicationContextProvider.getApplicationContext().getBean(ReceiptService.class);
    }

    @Override
    public void execute(Long id, Division division) throws Exception {
        Receipt r = this.receiptService.getOneById(id);

        //parse every item participant
            // check if sum of all participants amount is equal to receipt item

        //check if all participant amounts == the receipt total

        for(ReceiptItem ri : r.getReceiptData().getLineItems()){
            for(DivItem di : division.getDivisionItems()){
                if(ri.getId().equals(di.getReceiptItem().getId())){
                    getAllAmountsOfDistinctParticiapnts(di.getParticipantsList());
                }
            }
        }
    }
    private List<Double> getAllAmountsOfDistinctParticiapnts(List<Participant> participantsInOneItem) throws Exception {
        List<Double> amounts = new ArrayList<>(participantsInOneItem.size());

        for(int i =0; i<=participantsInOneItem.size()-1; i++){
            String id = getParticipantId(participantsInOneItem.get(i));
            amounts.add(i, participantsInOneItem.get(i).getAmount());

            for(int j =0; j<=participantsInOneItem.size()-1; j++){
                if(i != j){
                    String id1 = getParticipantId(participantsInOneItem.get(j));
                    if(id.equals(id1)){
                        amounts.add(i, amounts.get(i) + participantsInOneItem.get(j).getAmount());
                    }
                }
            }
        }
        return amounts;
    }
    private String getParticipantId(Participant p) throws Exception{
        if(p.getRegistredUser() == null) return p.getNonExistingUserName();
        if(p.getNonExistingUserName() == null) return String.valueOf(p.getRegistredUser().getId());
        throw new Exception("could not identify user"); //you may not reach this exception because it is already checked in InputValidationStep
    }
}
