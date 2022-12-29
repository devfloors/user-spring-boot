package com.boilerplate.user.service;

import com.boilerplate.user.domain.User;
import com.boilerplate.user.exception.LoginApplicationException;
import com.boilerplate.user.fixture.UserFixture;
import com.boilerplate.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입이_정상적으로_동작하는_경우(){
        String userName = "userName";
        String password = "password";

        User fixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(UserFixture.get(userName, password));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void 회원가입시_userName으로_회원가입한_우저가_이미_있는경우(){
        String userName = "userName";
        String password = "password";

        User fixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(LoginApplicationException.class,() -> userService.join(userName, password));
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우(){
        String userName = "userName";
        String password = "password";

        User fixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @Test
    void 로그인시_userName으로_회원가입한_우저가_이미_있는경우(){
        String userName = "userName";
        String password = "password";

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(LoginApplicationException.class,() -> userService.login(userName, password));
    }

    @Test
    void 로그인시_패스워드가_틀린_경우(){
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        User fixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(LoginApplicationException.class,() -> userService.login(userName, wrongPassword));
    }

}
