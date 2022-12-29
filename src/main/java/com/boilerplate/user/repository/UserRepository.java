package com.boilerplate.user.repository;

import com.boilerplate.user.domain.User;
import com.boilerplate.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<UserEntity> findByUserName(String userName);
}
