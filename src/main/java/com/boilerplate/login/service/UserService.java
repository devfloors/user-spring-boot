package com.boilerplate.login.service;

import com.boilerplate.login.exception.ErrorCode;
import com.boilerplate.login.exception.LoginApplicationException;
import com.boilerplate.login.model.User;
import com.boilerplate.login.model.entity.UserEntity;
import com.boilerplate.login.repository.UserEntityRepository;
import com.boilerplate.login.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User join(String userName, String password){

        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new LoginApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new LoginApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // 비밀번호 체크
        if(!encoder.matches(password,userEntity.getPassword())) {
            throw new LoginApplicationException(ErrorCode.INVALID_PASSWORD,"");
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);

        return token;
    }
}
