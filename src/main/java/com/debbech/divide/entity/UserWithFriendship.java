package com.debbech.divide.entity;

import com.debbech.divide.entity.enumer.FriendshipStatus;
import lombok.Data;

@Data
public class UserWithFriendship {
    private String uid;
    private String fullName;

    private FriendshipStatus friendshipStatus;

}
