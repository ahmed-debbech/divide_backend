package com.debbech.divide.services.impl;

import com.debbech.divide.data.FriendshipRegisteryRepo;
import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.FriendshipRegistry;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.utils.AllInputSanitizers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FriendshipRegistryService implements IFriendshipRegistryService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendshipRegisteryRepo friendshipRegisteryRepo;

    @Override
    public boolean createFriendRequest(String uid_to) throws Exception {
        String err = AllInputSanitizers.isUid(uid_to);
        if(!err.equals("")) throw new Exception("incorrect uid");

        if(userRepo.findUserByEmail(uid_to).orElse(null) == null) throw new Exception("could not perform this action!");

        String uid_from = AuthService.getLoggedInUser();
        List<FriendshipRegistry> existing = friendshipRegisteryRepo.checkIfFriendshipIsAlreadyMade(uid_from, uid_to).orElse(null);
        if(existing != null) throw new Exception("You already have existing friendship with " + uid_to);

        FriendshipRegistry fsr = new FriendshipRegistry();

        return false;
    }

}
