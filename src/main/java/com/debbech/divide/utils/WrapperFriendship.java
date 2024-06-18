package com.debbech.divide.utils;

import com.debbech.divide.entity.enumer.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrapperFriendship {
    private Long id;
    private FriendshipStatus status;
}
