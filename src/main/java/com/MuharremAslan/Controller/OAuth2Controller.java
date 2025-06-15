package com.MuharremAslan.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {


    @GetMapping("/")
    public String index() {
        return "You have successfully logged out of Google.";
    }

    @GetMapping("/google")
    public String google(@AuthenticationPrincipal OAuth2User principal) {
        return "Authenticated with Google Welcome Back "+principal.getAttribute("name") + " !"+
                " Sub: "+principal.getAttribute("sub")
                +" Email: "+principal.getAttribute("email")
                +" id: "+principal.getAttribute("id");
    }
}