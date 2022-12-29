package com.boilerplate.user.controller;

import com.boilerplate.user.controller.response.Response;
import com.boilerplate.user.controller.response.UserJoinResponse;
import com.boilerplate.user.controller.response.UserLoginResponse;
import com.boilerplate.user.controller.request.UserJoinRequest;
import com.boilerplate.user.controller.request.UserLoginRequest;
import com.boilerplate.user.domain.User;
import com.boilerplate.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        log.debug("Rest request to join user: {}",request);

        User user = userServiceImpl.join(request.getUserName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(user);
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        log.debug("Rest request to join user: {}",request);

        String token = userServiceImpl.login(request.getUserName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
