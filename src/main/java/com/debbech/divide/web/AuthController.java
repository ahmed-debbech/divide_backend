package com.debbech.divide.web;

import com.debbech.divide.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {


    @PostMapping("/login")
    public ResponseEntity<LoginResp> login(@RequestBody LoginReq req){
        log.info("{} ", req.getUid() + " is trying to login");
        LoginResp lr = new LoginResp(true, "");
        return ResponseEntity.ok().body(lr);
    }
    @PostMapping("/login/valid")
    public ResponseEntity<ValidateLoginResp> validateLogin(@RequestBody ValidateLoginReq req){
        log.info("{} is trying to validate login", req.getUid());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResp> firstTime(@RequestBody SignupReq req){
        log.info("a new user is signing up with email {}", req.getEmail());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/signup/valid")
    public ResponseEntity<ValidateLoginResp> validSignup(@RequestBody SignupOtpReq req){
        log.info("a new user is validating their sign up with email {}", req.getEmail());
        return ResponseEntity.ok().body(null);
    }

    
}
