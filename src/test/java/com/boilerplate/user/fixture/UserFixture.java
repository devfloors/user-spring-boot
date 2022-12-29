package com.boilerplate.user.fixture;

import com.boilerplate.user.domain.User;

public class UserFixture {

    public static User get(String userName, String password) {
        User result = new User();
        result.setId(1L);
        result.setUserName(userName);
        result.setPassword(password);

        return result;
    }
}
