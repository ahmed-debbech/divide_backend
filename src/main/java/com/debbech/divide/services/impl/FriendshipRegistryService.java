package com.debbech.divide.services.impl;

import com.debbech.divide.data.FriendshipRegisteryRepo;
import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.FriendshipRegistry;
import com.debbech.divide.entity.User;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.utils.AllInputSanitizers;
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

        fsr.setUnfriendedOn(LocalDateTime.now());
        fsr.setDeleted(true);
        friendshipRegisteryRepo.save(fsr);

    }

}
