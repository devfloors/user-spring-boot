package com.boilerplate.user.repository;

import com.boilerplate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
}
