package com.debbech.divide.web;

import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.web.models.GeneralMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private IFriendshipRegistryService friendshipRegistryService;

    @PutMapping("/{uid_to}/request")
    public ResponseEntity<Object> sendRequest(@PathVariable("uid_to") String uid){
        try {
            friendshipRegistryService.createFriendRequest(uid);
            GeneralMessage ufsr = new GeneralMessage("", true);
            return ResponseEntity.ok().body(ufsr);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }
}
