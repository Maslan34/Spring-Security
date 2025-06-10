package com.MuharremAslan.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privateBasic")
public class PrivateBasicAuthController {

    @GetMapping("/**")
    public String privateMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Basic-Auth Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    //@PreAuthorize("hasRole('USER')") -> Handled in config
    @GetMapping("/user")
    public String userPriveMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Basic-Auth USER Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    //@PreAuthorize("hasRole('ADMIN')") -> Handled in config
    @GetMapping("/admin")
    public String adminPriveMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Basic Auth-ADMIN Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    //@PreAuthorize("hasRole('MODERATOR')") -> Handled in config
    @GetMapping("/mod")
    public String moderatorPriveMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Basic Auth-MOD Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
