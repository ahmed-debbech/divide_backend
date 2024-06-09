package com.debbech.divide.data;

import com.debbech.divide.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE uid = :uid", nativeQuery = true)
    Optional<User> findUserByUid(@Param("uid") String uid);
}
