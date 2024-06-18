package com.debbech.divide.services.impl;

import com.debbech.divide.data.UserRepo;
import com.debbech.divide.entity.User;
import com.debbech.divide.security.JwtService;
import com.debbech.divide.services.interfaces.IAuthService;
import com.debbech.divide.utils.AllInputSanitizers;
import com.debbech.divide.utils.OTP;
import com.debbech.divide.utils.UID;
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
    @Autowired
    private UserRepo userRepo;

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
        User userDb = null;
        if(isUidUsed) userDb = userRepo.findUserByUid(uid).orElse(null);
        if(!isUidUsed) userDb = userRepo.findUserByEmail(email).orElse(null);

        //grab associated email
        if (userDb == null) throw new Exception("could not find user");
        String emailfound = userDb.getEmail();

        //generate OTP
        String otp = OTP.generate();
        //store OTP in database to the user
        userDb.setLastOtp(otp);
        userDb.setOtpValidated(false);
        userRepo.save(userDb);

        //TODO send email

        //finish return back
        return false;
    }

    @Override
    public String finishAuthentication(String uid, String email, String code) throws Exception {
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
        User userDb = null;
        if(isUidUsed) userDb = userRepo.findUserByUid(uid).orElse(null);
        if(!isUidUsed) userDb = userRepo.findUserByEmail(email).orElse(null);

        if (userDb == null) throw new Exception("could not find user");
        if (userDb.isOtpValidated()) throw new Exception("otp code is already validated");

        String lastOtp = userDb.getLastOtp();
        if(!lastOtp.equals(code)) throw new Exception("wrong otp");

        String token = jwtService.createJwt(userDb.getUid());
        userDb.setOtpValidated(true);
        userRepo.save(userDb);

        return token;
    }

    @Override
    public void firstTimeSignup(String email, String fullName) throws Exception {
        if(!AllInputSanitizers.isValidEmail(email)) throw new Exception("Incorrect email");
        if(fullName.isEmpty() || fullName.length() > 100) throw new Exception("full name is not acceptable");

        if(isEmailExist(email)) throw new Exception("email is already used");

        User u = new User();
        u.setEmail(email);
        //create uid
        String uid = null;
        do{
            uid = UID.generate();
        }while(isUidExist(uid));

        u.setUid(uid);
        u.setFullName(fullName);
        u.setOtpValidated(false);
        String otp = OTP.generate();
        u.setLastOtp(otp);
        userRepo.save(u);

        //TODO send email to new account
    }

    @Override
    public String validateSignup(String email, String code) throws Exception {
        if(!AllInputSanitizers.isValidEmail(email)) throw new Exception("Incorrect email");

        User userDb = userRepo.findUserByEmail(email).orElse(null);

        if (userDb == null) throw new Exception("could not find user");
        if (userDb.isOtpValidated()) throw new Exception("otp code is already validated");

        String lastOtp = userDb.getLastOtp();
        if(!lastOtp.equals(code)) throw new Exception("wrong otp");


        String token = jwtService.createJwt(userDb.getUid());

        userDb.setOtpValidated(true);
        userRepo.save(userDb);

        return token;
    }

    public boolean isEmailExist(String email) {
        User u = userRepo.findUserByEmail(email).orElse(null);
        return u != null;
    }
    public boolean isUidExist(String uid) {
        User u = userRepo.findUserByUid(uid).orElse(null);
        return u != null;
    }
}
