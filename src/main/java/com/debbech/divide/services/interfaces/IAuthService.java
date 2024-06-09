package com.debbech.divide.services.interfaces;

public interface IAuthService {
    boolean startAuthentication(String uid, String email) throws Exception;

    String finishAuthentication(String uid, String email, String code) throws Exception;
}
