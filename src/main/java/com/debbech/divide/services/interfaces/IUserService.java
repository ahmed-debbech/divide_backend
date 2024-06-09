package com.debbech.divide.services.interfaces;


import com.debbech.divide.entity.User;

public interface IUserService {
    User searchByUid(String uid) throws Exception;
}
