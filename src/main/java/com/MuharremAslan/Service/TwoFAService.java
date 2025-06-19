package com.MuharremAslan.Service;

import com.MuharremAslan.Model.User;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TwoFAService {

    private final UserService userService;
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    public TwoFAService(UserService userService) {
        this.userService = userService;
    }


    public GoogleAuthenticatorKey generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key;
    }

    public String getQRBarcodeURL(String userEmail, GoogleAuthenticatorKey secretKey) {
        String issuer = "Spring-Security";
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, userEmail, secretKey);
    }

    public boolean is2FASetupNeeded(String username) {
        Optional<User> user = userService.getByUsername(username);
        if (user.isPresent()) {
            return user.get().getSecretKey() == null || user.get().getSecretKey().isBlank();
        }
        else{
            throw new RuntimeException("User not found");
        }
    }
}