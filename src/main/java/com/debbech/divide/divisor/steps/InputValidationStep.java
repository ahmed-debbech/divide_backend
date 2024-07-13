package com.debbech.divide.divisor.steps;

import com.debbech.divide.entity.division.DivItem;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.division.Participant;
import org.springframework.stereotype.Service;

@Service
public class InputValidationStep implements IDivisionStep {

    @Override
    public void execute(Long id, Division division) throws Exception {

        if(id <= 0L) throw new Exception("incorrect id");
        if(division.getDivisionItems() == null) throw new Exception("division items are all null");
        if(division.getDivisionItems().isEmpty()) throw new Exception("division is empty");
        for(DivItem di : division.getDivisionItems()){
            if(di.getReceiptItem() == null) throw new Exception("receipt item is unknown");
            if(di.getReceiptItem().getId() <= 0L) throw new Exception("receipt item id is incorrect");
            if(di.getParticipantsList() == null) throw new Exception("participant list in item is null");
            if(di.getParticipantsList().isEmpty()) throw new Exception("participant list is empty");
            for(Participant p : di.getParticipantsList()){
                if(p.getRegistredUser() == null && p.getNonExistingUserName() == null) throw new Exception("no linked user to this item");
                if(p.getRegistredUser() == null && p.getNonExistingUserName().equals("")) throw new Exception("no linked user to this item");
                if(p.getRegistredUser() != null && p.getNonExistingUserName() != null)
                            throw new Exception("the participant can't be two users at the same time.");

                if((p.getNonExistingUserName() == null || p.getNonExistingUserName().equals(""))
                        && p.getRegistredUser().getId() <= 0L) throw new Exception("registred user id is incorrect");

                if(p.getAmount() <= 0) throw new Exception("amount is incorrect");
            }
        }
    }
}
