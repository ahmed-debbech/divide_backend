package com.debbech.divide.services.impl;

import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.User;
import com.debbech.divide.entity.UserWithFriendship;
import com.debbech.divide.entity.enumer.FriendshipStatus;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.utils.AllInputSanitizers;
import com.debbech.divide.utils.WrapperFriendship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private IFriendshipRegistryService friendshipRegistryService;

    @Override
    public User searchByUid(String uid) throws Exception {
        String err = AllInputSanitizers.isUid(uid);
        if(!err.equals("")) throw new Exception("incorrect uid");

        User u = userRepo.findUserByUid(uid).orElse(null);
        return u;
    }

    @Override
    public User getCurrentProfile() throws Exception {
        User u = null;
        String uid = AuthService.getLoggedInUser();
        u = userRepo.findUserByUid(uid).orElse(null);
        if(u == null) throw new Exception("can not get your current profile because your uid does not exist");

        return u;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public UserWithFriendship searchByUidWithFriendship(String uid) throws Exception {
        User u = this.searchByUid(uid);
        if(u == null) return null;

        WrapperFriendship fs = friendshipRegistryService.seeFriendship(u);
        UserWithFriendship us = new UserWithFriendship();
        us.setUid(uid);
        us.setFullName(u.getFullName());
        us.setFriendshipStatus(fs.getStatus());
        us.setFriendshipId(fs.getId());
        return us;
    }

    @Override
    public User findById(Long id) throws Exception {
        User u = this.userRepo.findById(id).orElse(null);
        if(u == null) throw new Exception("user is not found");
        return u;
    }
}
