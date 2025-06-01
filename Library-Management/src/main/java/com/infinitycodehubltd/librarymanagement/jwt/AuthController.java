//package com.infinitycodehubltd.librarymanagement.jwt;
//
//
//import com.infinitycodehubltd.librarymanagement.user.Member;
//import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authManager;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private MemberRepository memberRepo;
//
//
//    @Autowired
//    private RefreshTokenService refreshTokenService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//    @PostMapping("/login")
//    public String login(@RequestBody AuthRequest authRequest) {
//        authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
//        );
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
//        Member member = memberRepo.findByEmail(authRequest.getEmail()).get();
//        return jwtUtil.generateToken(member);
//    }
//
//
//
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public String register(@RequestBody Member member) {
//        member.setPassword(passwordEncoder.encode(member.getPassword()));
//        memberRepo.save(member);
//        return "User registered successfully.";
//    }
//
//
//    @PostMapping("/refreshToken")
//    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
//        String requestRefreshToken = request.get("refreshToken");
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(user -> {
//                    String token = jwtUtil.generateToken(user.getEmail());
//                    Map<String, String> response = new HashMap<>();
//                    response.put("accessToken", token);
//                    response.put("refreshToken", requestRefreshToken);
//                    return ResponseEntity.ok(response);
//                })
//                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
//    }
//}
//
//class AuthRequest {
//    private String email;
//    private String password;
//
//    // Getters and setters
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
//

package com.infinitycodehubltd.librarymanagement.jwt;

import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member) {
        if (memberRepository.findMemberByEmail(member.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.get("email"), credentials.get("password"))
        );

        Member member = memberRepository.findMemberByEmail(credentials.get("email")).get();
        String accessToken = jwtUtil.generateToken(member.getEmail(), member.getRole().name());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(member.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("token", accessToken);
        response.put("refreshToken", refreshToken.getToken());
        response.put("role", member.getRole().name());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                    Map<String, String> response = new HashMap<>();
                    response.put("accessToken", token);
                    response.put("refreshToken", requestRefreshToken);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
