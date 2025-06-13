package com.MuharremAslan.Controller;

import com.MuharremAslan.Model.User;
import com.MuharremAslan.Model.CreateUserRequest;
import com.MuharremAslan.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicBasic")
public class PublicBasicAuthController {

    private final UserService userService;

    public PublicBasicAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String publicMethod() {
        return "Public Basic-Auth Endpoint";
    }


    // Creating User Model
    @PostMapping("/createUser")
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
