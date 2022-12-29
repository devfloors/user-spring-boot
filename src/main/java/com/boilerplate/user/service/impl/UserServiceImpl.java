package com.boilerplate.user.service.impl;

import com.boilerplate.user.domain.User;
import com.boilerplate.user.exception.ErrorCode;
import com.boilerplate.user.exception.LoginApplicationException;
import com.boilerplate.user.repository.UserRepository;
import com.boilerplate.user.service.UserService;
import com.boilerplate.user.util.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    @Override
    public User join(String userName, String password) {
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new LoginApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        User user = new User();
        user.setUserName(userName);
        user.setPassword(encoder.encode(password));

        return userRepository.save(user);

    }

    @Override
    public String login(String userName, String password) {
        // 회원가입 여부 체크
        User userEntity = userRepository.findByUserName(userName).orElseThrow(() -> new LoginApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // 비밀번호 체크
        if(!encoder.matches(password,userEntity.getPassword())) {
            throw new LoginApplicationException(ErrorCode.INVALID_PASSWORD,"");
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);

        return token;
    }
}
