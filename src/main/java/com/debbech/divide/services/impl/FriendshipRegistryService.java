package com.debbech.divide.services.impl;

import com.debbech.divide.data.FriendshipRegisteryRepo;
import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.FriendshipRegistry;
import com.debbech.divide.entity.User;
import com.debbech.divide.entity.enumer.FriendshipStatus;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.utils.AllInputSanitizers;
import com.debbech.divide.utils.WrapperFriendship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FriendshipRegistryService implements IFriendshipRegistryService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendshipRegisteryRepo friendshipRegisteryRepo;

    @Override
    public void createFriendRequest(String uid_to) throws Exception {
        String err = AllInputSanitizers.isUid(uid_to);
        if(!err.isEmpty()) throw new Exception("incorrect uid");

        User uidt = userRepo.findUserByUid(uid_to).orElse(null);
        if(uidt== null) throw new Exception("requested user does not exist");

        String uid_from = AuthService.getLoggedInUser();

        if(uid_from.equals(uid_to)) throw new Exception("you cant send requests to your self!");

        User uidf = userRepo.findUserByUid(uid_from).orElse(null);
        List<FriendshipRegistry> existing = friendshipRegisteryRepo.checkIfFriendshipIsAlreadyMade(uidf.getId(), uidt.getId()).orElse(null);
        if(existing != null && !existing.isEmpty()) throw new Exception("You already have existing friendship with " + uid_to);

        FriendshipRegistry fsr = new FriendshipRegistry();
        fsr.setTo(uidt);
        fsr.setFrom(uidf);
        fsr.setUnfriendedOn(null);
        fsr.setDeleted(false);
        fsr.setCanceledOn(null);
        fsr.setAcceptedOn(null);
        fsr.setMadeOn(LocalDateTime.now());
        friendshipRegisteryRepo.save(fsr);
    }

    @Override
    public void cancelRequest(Long friend_id) throws Exception {

        FriendshipRegistry fsr = friendshipRegisteryRepo.getByIdLong(friend_id).orElse(null);
        if(fsr == null) throw new Exception("This friendship does not exist");

        if(fsr.getUnfriendedOn() != null) throw new Exception("friendship has already been unfriended") ;
        if(fsr.getCanceledOn() != null) throw new Exception("friendship has already been canceled") ;

        if(fsr.getAcceptedOn() != null) throw new Exception("friendship can not get canceled because it is already accepted");

        fsr.setCanceledOn(LocalDateTime.now());
        fsr.setDeleted(true);
        friendshipRegisteryRepo.save(fsr);
    }

    @Override
    public void unfriendRequest(Long friendshipId) throws Exception {

        FriendshipRegistry fsr = friendshipRegisteryRepo.getByIdLong(friendshipId).orElse(null);
        if(fsr == null) throw new Exception("This friendship does not exist");

        if(fsr.getCanceledOn() != null) throw new Exception("friendship has already been canceled") ;
        if(fsr.getUnfriendedOn() != null) throw new Exception("friendship has already been unfriended") ;

        if(fsr.getAcceptedOn() == null) throw new Exception("friendship can not get be unfriended because it is not accepted");

        fsr.setUnfriendedOn(LocalDateTime.now());
        fsr.setDeleted(true);
        friendshipRegisteryRepo.save(fsr);

    }

    @Override
    public void acceptRequest(Long friendshipId) throws Exception {

        FriendshipRegistry fsr = friendshipRegisteryRepo.getByIdLong(friendshipId).orElse(null);
        if(fsr == null) throw new Exception("This friendship does not exist");

        if(fsr.getCanceledOn() != null) throw new Exception("friendship has already been canceled") ;
        if(fsr.getUnfriendedOn() != null) throw new Exception("friendship has already been unfriended") ;

        String auth = AuthService.getLoggedInUser();

        if(!fsr.getTo().getUid().equals(auth)) throw new Exception("You can not accept your own request to this user.");

        fsr.setAcceptedOn(LocalDateTime.now());
        fsr.setDeleted(false);
        friendshipRegisteryRepo.save(fsr);
    }

    @Override
    public List<FriendshipRegistry> getAllIncommingRequests() throws Exception {

        String uid = AuthService.getLoggedInUser();
        User uidt = userRepo.findUserByUid(uid).orElse(null);
        if(uidt== null) throw new Exception("requested user does not exist");

        List<FriendshipRegistry> fsr = friendshipRegisteryRepo.getIncomming(uidt.getId()).orElse(null);
        if(fsr == null) throw new Exception("error retrieving list");

        return fsr;
    }

    @Override
    public List<FriendshipRegistry> getAllFriends() throws Exception {

        String uid = AuthService.getLoggedInUser();
        User uidt = userRepo.findUserByUid(uid).orElse(null);
        if(uidt== null) throw new Exception("requested user does not exist");

        List<FriendshipRegistry> fsr = friendshipRegisteryRepo.getFriends(uidt.getId()).orElse(null);
        if(fsr == null) throw new Exception("error retrieving list");

        return fsr;
    }

    @Override
    public WrapperFriendship seeFriendship(User friend) throws Exception {
        String uid = AuthService.getLoggedInUser();
        User you_user = userRepo.findUserByUid(uid).orElse(null);
        if(you_user == null) throw new Exception("Something went wrong.");

        FriendshipRegistry fr = friendshipRegisteryRepo.getRelationshipBetween(you_user.getId(), friend.getId()).orElse(null);
        if(fr == null) return new WrapperFriendship(0L, FriendshipStatus.NOT_FRIENDS);
        if(fr.getAcceptedOn() == null && fr.getFrom().getUid().equals(you_user.getUid())) {
            return new WrapperFriendship(fr.getId(), FriendshipStatus.CANCELABLE);
        }
        if(fr.getAcceptedOn() == null && fr.getTo().getUid().equals(you_user.getUid())) {
            return new WrapperFriendship(fr.getId(), FriendshipStatus.ACCEPTABLE);
        }
        return new WrapperFriendship(fr.getId(), FriendshipStatus.FRIENDS);
    }

}
