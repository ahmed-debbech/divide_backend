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
import java.util.HashMap;
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
        List<Double> amounts = new ArrayList<>();
        List<Double> finalAmounts = new ArrayList<>();

        for(ReceiptItem ri : r.getReceiptData().getLineItems()){
            for(DivItem di : division.getDivisionItems()){
                if(ri.getId().equals(di.getReceiptItem().getId())){
                    amounts = getAllAmountsOfDistinctParticiapnts(di.getParticipantsList());
                    Double allAmountsOfParticipants = amounts.stream().mapToDouble(f -> f).sum();
                    if(!allAmountsOfParticipants.equals(ri.getTotal())) throw new Exception("division total for a receipt item " +ri.getId()+ " is bigger than its total");
                    finalAmounts.add(allAmountsOfParticipants);
                }
            }
        }
        Double fAmount = finalAmounts.stream().mapToDouble(f -> f).sum();
        Double gAmount = r.getReceiptData().getLineItems().stream().mapToDouble(f -> f.getTotal()).sum();

        if(!fAmount.equals(gAmount)) throw new Exception("sum of division and receipt items are not equal");

    }
    private List<Double> getAllAmountsOfDistinctParticiapnts(List<Participant> participantsInOneItem) throws Exception {
        HashMap<String, Double> distinctParticipants = new HashMap<>();

        //initialization...
        for (int i = 0; i <= participantsInOneItem.size()-1; i++) {
            distinctParticipants.put(getParticipantId(participantsInOneItem.get(i)), (double) 0 );
        }


        for(int i =0; i<=participantsInOneItem.size()-1; i++){
            String id = getParticipantId(participantsInOneItem.get(i));
            Double d = distinctParticipants.get(id);
            distinctParticipants.put(id, d + participantsInOneItem.get(i).getAmount());
        }
        return new ArrayList<>(distinctParticipants.values());
    }
    private String getParticipantId(Participant p) throws Exception{
        if(p.getRegistredUser() == null) return p.getNonExistingUserName();
        if(p.getNonExistingUserName() == null) return String.valueOf(p.getRegistredUser().getId());
        throw new Exception("could not identify user"); //you may not reach this exception because it is already checked in InputValidationStep
    }
}
