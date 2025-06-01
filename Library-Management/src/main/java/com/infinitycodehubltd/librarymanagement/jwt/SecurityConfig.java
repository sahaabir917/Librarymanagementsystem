//package com.infinitycodehubltd.librarymanagement.jwt;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.access.AccessDeniedHandler;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////@Configuration
////@EnableMethodSecurity
////public class SecurityConfig {
////
////    private final JwtRequestFilter jwtRequestFilter;
////    private AccessDeniedHandler CustomAccessDeniedHandler;
////
////
////    // ✅ Manual constructor
////    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
////        this.jwtRequestFilter = jwtRequestFilter;
////    }
////
////    // ✅ Allow access based on roles and token
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http.csrf().disable()
//////                .authorizeHttpRequests(auth -> auth
//////                        .requestMatchers("/auth/**").permitAll()
//////                        .requestMatchers("/admin/**").hasRole("ADMIN")
//////                        .requestMatchers("/staff/**").hasRole("STAFF")
//////                        .requestMatchers("/user/**").hasRole("USER")
//////                        .anyRequest().authenticated()
//////                )
//////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//////
//////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//////
//////        return http.build();
//////    }
////
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http.csrf().disable()
//////                .authorizeHttpRequests(auth -> auth
//////                        .requestMatchers("/auth/**").permitAll()
//////                        .requestMatchers("/admin/**").hasRole("ADMIN")
//////                        .requestMatchers("/staff/**").hasRole("STAFF")
//////                        .requestMatchers("/user/**").hasRole("USER")
//////                        .anyRequest().authenticated()
//////                )
//////                .exceptionHandling(exception -> exception
//////                        .accessDeniedHandler(CustomAccessDeniedHandler) //
//////                )
//////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//////
//////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//////        return http.build();
////
////
////        http.csrf().disable()
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/auth/**").permitAll()
////                        .requestMatchers("/admin/**").hasRole("ADMIN")
////                        .requestMatchers("/staff/**").hasRole("STAFF")
////                        .requestMatchers("/user/**").hasRole("USER")
////                        .anyRequest().authenticated()
////                )
////                .exceptionHandling(exception -> exception
////                        .accessDeniedHandler(new CustomAccessDeniedHandler())
////                )
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////
////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
////        return http.build();
////    }
////
////
////    // ✅ BCrypt password encoder
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    // ✅ Authentication manager bean
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
////        return config.getAuthenticationManager();
////    }
////}
//
//
//
//
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity   // enables @PreAuthorize (replaces @EnableGlobalMethodSecurity):contentReference[oaicite:5]{index=5}
//public class SecurityConfig {
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
//                                                       org.springframework.security.core.userdetails.UserDetailsService userDetailsService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Disable CSRF, stateless session
//        http.csrf(csrf -> csrf.disable())
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        // Custom handlers for auth exceptions -> return JSON
//        http.exceptionHandling(ex -> ex
//                .accessDeniedHandler(accessDeniedHandler)
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                    Map<String,Object> body = Map.of(
//                            "code", HttpStatus.UNAUTHORIZED.value(),
//                            "message", "Unauthorized"
//                    );
//                    new ObjectMapper().writeValue(response.getWriter(), body);
//                })
//        );
//
//        // URL-based authorization rules
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/staff/**").hasRole("STAFF")
//                .requestMatchers("/user/**").hasRole("USER")
//                .anyRequest().authenticated()
//        );
//
//        // Add JWT filter before Spring's authentication filter
//        http.addFilterBefore(jwtRequestFilter,
//                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}



package com.infinitycodehubltd.librarymanagement.jwt;

import com.infinitycodehubltd.librarymanagement.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**").hasRole("STAFF")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
