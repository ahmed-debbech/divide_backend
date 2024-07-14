package com.debbech.divide.services.interfaces;


import com.debbech.divide.entity.User;
import com.debbech.divide.entity.UserWithFriendship;

import java.util.List;

public interface IUserService {
    User searchByUid(String uid) throws Exception;
    User getCurrentProfile() throws Exception;

    List<User> getAll();

    UserWithFriendship searchByUidWithFriendship(String uid) throws Exception;

    User findById(Long id) throws Exception;
}
