package com.debbech.divide.services.interfaces;

public interface IFriendshipRegistryService {
    void createFriendRequest(String uid_to) throws Exception;
    void cancelRequest(Long friend_id) throws Exception;

    void unfriendRequest(Long friendshipId) throws Exception;
}
