package com.debbech.divide.data;

import com.debbech.divide.entity.FriendshipRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRegisteryRepo extends JpaRepository<FriendshipRegistry, Long> {

    @Query(value = "SELECT * FROM friendship_registery WHERE ((uid_from = :uidf and uid_to = :uidt) or" +
            " (uid_from = :uidt and uid_to = :uidf)) " +
            "and (is_deleted = false)"
            , nativeQuery = true)
    Optional<List<FriendshipRegistry>> checkIfFriendshipIsAlreadyMade(@Param("uidf") Long uid_from, @Param("uidt") Long uid_to);

    @Query(value = "SELECT * FROM friendship_registery WHERE id = :id", nativeQuery = true)
    Optional<FriendshipRegistry> getByIdLong(@Param("id") Long friendship_id);

}
