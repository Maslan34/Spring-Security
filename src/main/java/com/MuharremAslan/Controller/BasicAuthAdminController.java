package com.MuharremAslan.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class BasicAuthAdminController {

    @GetMapping
    public String privateAdmin() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return "Private Basic Auth-ADMIN Endpoint. Welcome "+username+"\nAuth Object: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }


}
