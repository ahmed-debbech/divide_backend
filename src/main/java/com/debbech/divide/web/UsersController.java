package com.debbech.divide.web;

import com.debbech.divide.entity.User;
import com.debbech.divide.services.impl.AuthService;
import com.debbech.divide.services.interfaces.IUserService;
import com.debbech.divide.web.models.GeneralMessage;
import com.debbech.divide.web.models.UserFromSearchResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUserService userService;

    @GetMapping("/search/{uid}")
    public ResponseEntity<Object> s(@PathVariable("uid") String uid){
        //String uuid = AuthService.getLoggedInUser();
        try {
            User u = userService.searchByUid(uid);
            if (u == null){
                GeneralMessage ufsr = new GeneralMessage("User with id "+uid+ " do not exist.", false);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ufsr);
            }
            UserFromSearchResp ufsr = new UserFromSearchResp(u.getFullName());
            return ResponseEntity.ok().body(ufsr);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }
}
