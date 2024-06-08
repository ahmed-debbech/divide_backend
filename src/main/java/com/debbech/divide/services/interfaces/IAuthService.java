package com.debbech.divide.services.interfaces;

public interface IAuthService {
    boolean startAuthentication(String uid, String email) throws Exception;
}
