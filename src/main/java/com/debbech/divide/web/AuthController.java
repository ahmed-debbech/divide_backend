package com.debbech.divide.web;

import com.debbech.divide.security.JwtService;
import com.debbech.divide.services.interfaces.IAuthService;
import com.debbech.divide.web.models.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {


    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResp> login(@RequestBody LoginReq req){
        log.info("{} ", req.getUid() + " is trying to login");
        try {
            authService.startAuthentication(req.getUid(), req.getEmail());
            LoginResp lr = new LoginResp(true, "");
            return ResponseEntity.ok().body(lr);
        } catch (Exception e) {
            LoginResp lr = new LoginResp(false, e.getMessage());
            return ResponseEntity.ok().body(lr);
        }
    }
    @PostMapping("/login/valid")
    public ResponseEntity<ValidateLoginResp> validateLogin(@RequestBody ValidateLoginReq req, HttpServletRequest rawReq){
        log.info("{} is trying to validate login", req.getUid());
        try {
            String token  = authService.finishAuthentication(req.getUid(), req.getEmail(), req.getCode());
            ValidateLoginResp lr = new ValidateLoginResp(token, true, "");
            return ResponseEntity.ok().body(lr);
        } catch (Exception e) {
            ValidateLoginResp lr = new ValidateLoginResp("", false, e.getMessage());
            return ResponseEntity.ok().body(lr);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResp> firstTime(@RequestBody SignupReq req){
        log.info("a new user is signing up with email {} with name {} ", req.getEmail(), req.getFullName());
        try {
            authService.firstTimeSignup(req.getEmail(), req.getFullName());
            SignUpResp lr = new SignUpResp(true, "");
            return ResponseEntity.ok().body(lr);
        } catch (Exception e) {
            SignUpResp lr = new SignUpResp(false, e.getMessage());
            return ResponseEntity.ok().body(lr);
        }
    }

    @PostMapping("/signup/valid")
    public ResponseEntity<ValidateLoginResp> validSignup(@RequestBody SignupOtpReq req){
        log.info("a new user is validating their sign up with email {}", req.getEmail());
        try {
            String token = authService.validateSignup(req.getEmail(), req.getCode());
            ValidateLoginResp lr = new ValidateLoginResp(token ,true, "");
            return ResponseEntity.ok().body(lr);
        } catch (Exception e) {
            ValidateLoginResp lr = new ValidateLoginResp("",false, e.getMessage());
            return ResponseEntity.ok().body(lr);
        }
    }


}
