package com.debbech.divide.services.interfaces;

import com.debbech.divide.entity.FriendshipRegistry;

import java.util.List;

public interface IFriendshipRegistryService {
    void createFriendRequest(String uid_to) throws Exception;
    void cancelRequest(Long friend_id) throws Exception;

    void unfriendRequest(Long friendshipId) throws Exception;

    void acceptRequest(Long friendshipId) throws Exception;

    List<FriendshipRegistry> getAllIncommingRequests() throws Exception;

    List<FriendshipRegistry> getAllFriends() throws Exception;
}
