package com.MuharremAslan.Controller;

import com.MuharremAslan.Core.utils.Google2FAUtils;
import com.MuharremAslan.Model.User;
import com.MuharremAslan.Service.TwoFAService;
import com.MuharremAslan.Service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class TwoFAController {

    private final UserService userService;
    private final TwoFAService twoFAService;

    public TwoFAController(TwoFAService twoFAService, UserService userService) {
        this.twoFAService = twoFAService;
        this.userService = userService;
    }

    @GetMapping("/2fa-init")
    public String twoFAInit(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        Optional<User> user = userService.getByUsername(username);

        GoogleAuthenticatorKey secret = twoFAService.generateSecretKey();
        userService.setUserSecret(username, secret.getKey());

        // There is no need to query again here as the user reaches this stage after authenticating.
        String qrUrl = twoFAService.getQRBarcodeURL(user.get().getUsername(), secret);


        model.addAttribute("qrUrl", qrUrl);
        model.addAttribute("secret", user.get().getSecretKey());
        return "2fa-init";
    }

    @GetMapping("/2fa-check")
    public String handle2FACheck(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        if (twoFAService.is2FASetupNeeded(username)) {
            return "redirect:/2fa-init";
        }
        return "2fa-check-google";
    }

    @PostMapping("/verify-2fa")
    public String verify2faCode(
            @RequestParam("code") String code
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.getByUsername(username);
        System.out.println(username);
        System.out.println(user);
        System.out.println(code);
        String secretKey = user.get().getSecretKey();

        boolean isValid = Google2FAUtils.verifyCode(secretKey, code);

        // Update authentication if necessary
        if (isValid) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            UsernamePasswordAuthenticationToken fullAuth = new UsernamePasswordAuthenticationToken(
                    auth.getPrincipal(), auth.getCredentials(), auth.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(fullAuth);

            return "redirect:/2fa-check?success"; // redirect after successful login
        } else {
            return "redirect:/2fa-check?error"; // if the code is wrong
        }
    }
}

