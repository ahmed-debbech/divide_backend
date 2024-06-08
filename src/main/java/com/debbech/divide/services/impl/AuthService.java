package com.debbech.divide.services.impl;

import com.debbech.divide.security.JwtService;
import com.debbech.divide.services.interfaces.IAuthService;
import com.debbech.divide.utils.AllInputSanitizers;
import com.debbech.divide.utils.OTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements IAuthService {

    @Autowired
    private JwtService jwtService;

    public static String getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal();
    }

    @Override
    public boolean startAuthentication(String uid, String email) throws Exception {
        //check if it is email or uid
        if(!uid.equals("") && !email.equals("")) throw new Exception("You should set either email or UID.");
        boolean isUidUsed = true;
        if(uid.equals("")) {
            if (!AllInputSanitizers.isValidEmail(email)) throw new Exception("Incorrect email");
            isUidUsed = false;
        }else {
            String error = AllInputSanitizers.isUid(uid);
            if (!error.equals("")) throw new Exception(error);
        }
        //search for it in database
        //if(isUidUsed) //search with uid for email in db
        //if(!isUidUsed) //search with email for email in db

        //grab associated email

        //generate OTP
        String otp = OTP.generate();
        //store OTP in database to the user

        //send email

        //return back
        return false;
    }
}
