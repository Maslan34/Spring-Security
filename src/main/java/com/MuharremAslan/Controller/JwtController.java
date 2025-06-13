package com.MuharremAslan.Controller;


import com.MuharremAslan.Model.AuthRequest;
import com.MuharremAslan.Model.CreateUserRequest;
import com.MuharremAslan.Model.User;
import com.MuharremAslan.Service.JwtService;
import com.MuharremAslan.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class JwtController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);

    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        }
        log.info("username or password not valid" + request.getUsername());
        throw new UsernameNotFoundException("invalid username or password" + request.getUsername());

    }

    @GetMapping("/user")
    public String getUser(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        return "This is User. Your Jwt Token: " + jwt;
    }

    @GetMapping("/admin")
    public String getAdmin(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        return "This is Admin.Your Jwt Token: " + jwt;
    }


}
