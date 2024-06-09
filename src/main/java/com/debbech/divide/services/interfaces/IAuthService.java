package com.debbech.divide.services.interfaces;

public interface IAuthService {
    boolean startAuthentication(String uid, String email) throws Exception;

    String finishAuthentication(String uid, String email, String code) throws Exception;

    void firstTimeSignup(String email, String fullName) throws Exception;

    String validateSignup(String email, String code) throws Exception;
}
