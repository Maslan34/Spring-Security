package com.MuharremAslan.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Implementing SecurityFilterChain
@EnableMethodSecurity // provide security of controllers
public class SecurityConfig {

    // ### In-Memory-Security

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("muharrem")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("omer")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); // Service Instance so, UserDetails Service implemented.


    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                //This sets which endponies will be allowed to pass.
                .authorizeHttpRequests(x -> x.requestMatchers("/public/**", "/auth/**").permitAll())
                // Alternative of @PreAuthorize
                .authorizeHttpRequests(x -> x.requestMatchers("/private/user/**").hasRole("USER"))
                .authorizeHttpRequests(x -> x.requestMatchers("/private/admin/**").hasRole("ADMIN"))
                //Requests outside these endpoints will be authorized.
                .authorizeHttpRequests(x -> x.anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults()); // posting username and pass via form
        //.formLogin(AbstractHttpConfigurer::disable) // here sent with a header

        return http.build();
    }

    // ### In-Memory-Security ###
}
