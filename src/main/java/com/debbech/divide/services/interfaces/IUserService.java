package com.debbech.divide.services.interfaces;


import com.debbech.divide.entity.User;

import java.util.List;

public interface IUserService {
    User searchByUid(String uid) throws Exception;
    User getCurrentProfile() throws Exception;

    List<User> getAll();
}
