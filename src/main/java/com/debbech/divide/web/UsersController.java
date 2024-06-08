package com.debbech.divide.web;

import com.debbech.divide.services.impl.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping()
    public void s(){
        String uuid = AuthService.getLoggedInUser();
        System.err.println(uuid);
    }
}
