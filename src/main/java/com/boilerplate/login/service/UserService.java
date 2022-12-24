package com.boilerplate.login.service;

import com.boilerplate.login.exception.LoginApplicationException;
import com.boilerplate.login.model.User;
import com.boilerplate.login.model.entity.UserEntity;
import com.boilerplate.login.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    // TODO: implement
    public User join(String userName, String password){

        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

        userEntityRepository.save(new UserEntity());
        return new User();
    }

    // TODO: implement
    public String login(String userName, String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new LoginApplicationException());

        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new LoginApplicationException();
        }

        // 토큰 생성
        return "";
    }
}
