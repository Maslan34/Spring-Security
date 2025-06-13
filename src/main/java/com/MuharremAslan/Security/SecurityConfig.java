package com.MuharremAslan.Security;

import com.MuharremAslan.Model.ROLE;
import com.MuharremAslan.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity //Implementing SecurityFilterChain
@EnableMethodSecurity // provide security of controllers
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // ### In-Memory-Security
  /*
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

 */


    /*
// ### Basic-Auth Security


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable X-Frame-Options header (e.g., required for H2-console)
                // you can use with defaults
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())// Activating CORS
                // Defining authorization rules
                .authorizeHttpRequests(
                        x ->x
                                // Allow all requests to "/publicBasic/**"
                                .requestMatchers("/publicBasic/**").permitAll()
                                // Only users with ROLE_USER authority can access "/privateBasic/user"
                                .requestMatchers("/privateBasic/user").hasAuthority(ROLE.ROLE_USER.getAuthority())
                                // Only users with ROLE_ADMIN authority can access "/privateBasic/admin"
                                .requestMatchers("/privateBasic/admin").hasAuthority(ROLE.ROLE_ADMIN.getAuthority())
                                // Only users with ROLE_MODERATOR authority can access "/privateBasic/mod"
                                .requestMatchers("/privateBasic/mod").hasAuthority(ROLE.ROLE_MODERATOR.getAuthority())
                                // All users with ROLE_ADMIN, ROLE_MODERATOR, or ROLE_USER can access "/privateBasic/**"
                                .requestMatchers("/privateBasic/**").hasAnyAuthority(ROLE.ROLE_ADMIN.getAuthority(),ROLE.ROLE_MODERATOR.getAuthority(),ROLE.ROLE_USER.getAuthority())


                        // Setting  session creation policy: create session if required (not stateless)
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                //.formLogin(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        //.formLogin(AbstractHttpConfigurer::disable)

        return http.build();
    }

    // ### Basic-Auth Security ###


     */

    //### JWT AUTH

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        return http
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers("/auth/addNewUser/**", "/auth/generateToken/**").permitAll()
                                .requestMatchers("/auth/user/**").hasAuthority(ROLE.ROLE_USER.getAuthority())
                                .requestMatchers("/auth/admin/**").hasAuthority(ROLE.ROLE_ADMIN.getAuthority())

                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT de STATELESS KULLANIYORUZ.
                // Here we specify what the authentication provider will be like.
                .authenticationProvider(authenticationProvider())
                // It will pass through the jwt filter before authenticating. If it returns negative, then the authenticate process will be performed.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //.formLogin(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()).build();
        //.formLogin(AbstractHttpConfigurer::disable)


    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // Why dao? Because it will go to db and check this using userdetails service.
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //### JWT AUTH ###


}
