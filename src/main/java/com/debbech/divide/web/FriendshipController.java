package com.debbech.divide.web;

import com.debbech.divide.entity.FriendshipRegistry;
import com.debbech.divide.entity.User;
import com.debbech.divide.services.impl.AuthService;
import com.debbech.divide.services.interfaces.IFriendshipRegistryService;
import com.debbech.divide.web.models.FriendshipRegistryDto;
import com.debbech.divide.web.models.GeneralMessage;
import com.debbech.divide.web.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private IFriendshipRegistryService friendshipRegistryService;

    private UserDto getOppositeUser(User user1, User user2){
        String uid = AuthService.getLoggedInUser();
        if(user1.getUid().equals(uid)){
            return new UserDto(user2.getUid(), user2.getFullName());
        }
        if(user2.getUid().equals(uid)){
            return new UserDto(user1.getUid(), user1.getFullName());
        }
        return null;
    }

    @GetMapping("/incomming")
    public ResponseEntity<Object> getRequest(){

        try {
            List<FriendshipRegistry> res = friendshipRegistryService.getAllIncommingRequests();
            List<FriendshipRegistryDto> dtos = new ArrayList<>();
            for(int i = 0; i<=res.size()-1; i++){
                dtos.add(new FriendshipRegistryDto(res.get(i).getId(),
                        new UserDto(res.get(i).getFrom().getUid(), res.get(i).getFrom().getFullName()),
                        new UserDto(res.get(i).getTo().getUid(), res.get(i).getTo().getFullName()),
                        getOppositeUser(res.get(i).getTo(), res.get(i).getFrom()),
                        res.get(i).getMadeOn(),
                        res.get(i).getAcceptedOn(),
                        res.get(i).getUnfriendedOn(),
                        res.get(i).getCanceledOn()
                        ));
            }
            return ResponseEntity.ok().body(dtos);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<Object> getFriends(){
        try {
            List<FriendshipRegistry> res = friendshipRegistryService.getAllFriends();
            List<FriendshipRegistryDto> dtos = new ArrayList<>();
            for(int i = 0; i<=res.size()-1; i++){
                dtos.add(new FriendshipRegistryDto(res.get(i).getId(),
                        new UserDto(res.get(i).getFrom().getUid(), res.get(i).getFrom().getFullName()),
                        new UserDto(res.get(i).getTo().getUid(), res.get(i).getTo().getFullName()),
                        getOppositeUser(res.get(i).getTo(), res.get(i).getFrom()),
                        res.get(i).getMadeOn(),
                        res.get(i).getAcceptedOn(),
                        res.get(i).getUnfriendedOn(),
                        res.get(i).getCanceledOn()
                ));
            }
            return ResponseEntity.ok().body(dtos);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }

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

    @PutMapping("/{friendship_id}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable("friendship_id") Long friendshipId){
        try {
            friendshipRegistryService.cancelRequest(friendshipId);
            GeneralMessage ufsr = new GeneralMessage("", true);
            return ResponseEntity.ok().body(ufsr);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }

    @PutMapping("/{friendship_id}/unfriend")
    public ResponseEntity<Object> unfriendRequest(@PathVariable("friendship_id") Long friendshipId){
        try {
            friendshipRegistryService.unfriendRequest(friendshipId);
            GeneralMessage ufsr = new GeneralMessage("", true);
            return ResponseEntity.ok().body(ufsr);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }

    @PutMapping("/{friendship_id}/accept")
    public ResponseEntity<Object> acceptRequest(@PathVariable("friendship_id") Long friendshipId){
        try {
            friendshipRegistryService.acceptRequest(friendshipId);
            GeneralMessage ufsr = new GeneralMessage("", true);
            return ResponseEntity.ok().body(ufsr);
        }catch (Exception e){
            GeneralMessage ufsr = new GeneralMessage(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ufsr);
        }
    }

}
