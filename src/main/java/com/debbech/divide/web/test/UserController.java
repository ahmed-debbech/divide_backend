package com.debbech.divide.web.test;


import com.debbech.divide.entity.User;
import com.debbech.divide.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ResponseEntity<Object> s() {
        List<User> uss =  userService.getAll();

        return ResponseEntity.ok().body(uss);
    }
}
