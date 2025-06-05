package com.MuharremAslan.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String privateMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    //@PreAuthorize("hasRole('USER')") -> Handled in config
    @GetMapping("/user")
    public String userPriveMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private USER Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    //@PreAuthorize("hasRole('ADMIN')") -> Handled in config
    @GetMapping("/admin")
    public String adminPriveMethod() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private ADMIN Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
