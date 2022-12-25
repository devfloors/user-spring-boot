package com.boilerplate.login.controller.Response;

import com.boilerplate.login.model.User;
import com.boilerplate.login.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private String token;

}
