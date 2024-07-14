package com.debbech.divide.divisor.steps;

import com.debbech.divide.ApplicationContextProvider;
import com.debbech.divide.entity.FriendshipRegistry;
import com.debbech.divide.entity.User;
import com.debbech.divide.entity.division.DivItem;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.division.Participant;
import com.debbech.divide.entity.enumer.FriendshipStatus;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.entity.receipt.ReceiptItem;
import com.debbech.divide.services.impl.AuthService;
import com.debbech.divide.services.impl.FriendshipRegistryService;
import com.debbech.divide.services.impl.ReceiptService;
import com.debbech.divide.services.impl.UserService;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.utils.WrapperFriendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

public class ObjectExistanceCheckerStep implements IDivisionStep{

    private IReceiptService receiptService;
    private IUserService userService;
    private IFriendshipRegistryService friendshipRegistryService;


    public ObjectExistanceCheckerStep(){
        this.receiptService = ApplicationContextProvider.getApplicationContext().getBean(ReceiptService.class);
        this.userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
        this.friendshipRegistryService = ApplicationContextProvider.getApplicationContext().getBean(FriendshipRegistryService.class);

    }


    @Override
    public void execute(Long id, Division division) throws Exception {

        Receipt r = isReceiptExists(id);

        isDivisionMadeByInitiator(r);

        areDivisionItemsEqualReceiptItems(r, division);

        areDivisionItemsIdenticalToReceiptItems(r, division);

        areParticipantsExistAndFriends(division);
    }
    private Receipt isReceiptExists(Long id) throws Exception {
        return receiptService.getOneById(id);
    }
    private void isDivisionMadeByInitiator(Receipt r) throws Exception{
        User u = userService.getCurrentProfile();
        if(!r.getInitiator().getId().equals(u.getId()))
            throw new Exception("this receipt doesn't belong to you");
    }
    private void areDivisionItemsEqualReceiptItems(Receipt r, Division d) throws Exception {
        if(r.getReceiptData().getLineItems().size() != d.getDivisionItems().size())
            throw new Exception("receipt and division object items are not equal");
    }
    private void areDivisionItemsIdenticalToReceiptItems(Receipt r, Division d) throws Exception{
        for(DivItem di : d.getDivisionItems()){
            boolean found = false;
            for(ReceiptItem ri : r.getReceiptData().getLineItems()){
                if(Objects.equals(di.getReceiptItem().getId(), ri.getId())){
                    found = true;
                    break;
                }
            }
            if(!found) throw new Exception("receipt items and division items are not identical.");
        }
    }
    private void areParticipantsExistAndFriends(Division division) throws Exception {
        for(DivItem di : division.getDivisionItems()){
            for(Participant p : di.getParticipantsList()){
                if(p.getRegistredUser() != null){
                    User u = this.userService.findById(p.getRegistredUser().getId());
                    String uid = AuthService.getLoggedInUser();
                    if(uid.equals(u.getUid())) break;
                    WrapperFriendship wf = this.friendshipRegistryService.seeFriendship(u);
                    if(!wf.getStatus().equals(FriendshipStatus.FRIENDS))
                        throw new Exception("this participant is not a friend with the initiator");
                }
            }
        }
    }
}
