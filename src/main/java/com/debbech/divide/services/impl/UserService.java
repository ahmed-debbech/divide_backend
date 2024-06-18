package com.debbech.divide.services.impl;

import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.User;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.utils.AllInputSanitizers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

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
}
