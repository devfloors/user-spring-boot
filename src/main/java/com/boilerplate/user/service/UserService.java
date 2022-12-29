package com.boilerplate.user.service;

import com.boilerplate.user.domain.User;

public interface UserService {

    User join(String userName, String password);
    String login(String userName, String password);

}
