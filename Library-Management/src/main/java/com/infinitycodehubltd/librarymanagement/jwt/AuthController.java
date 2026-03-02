package com.infinitycodehubltd.librarymanagement.jwt;



import com.infinitycodehubltd.librarymanagement.jwt.JwtService;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;
//    private BCryptPasswordEncoder encoder;

    @PostMapping(value = "/login", produces = "application/json")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // Step 1: Check user exists
        Member member = memberRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(" User not found"));

        // Step 2: Match password
        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException(" Invalid credentials");
        }

        // Step 3: Generate JWT access and refresh tokens
        String token = jwtService.generateToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        // Step 4: Return tokens
        return new AuthResponse(token, refreshToken, member.getEmail(), member.getId(), member.getPhone());
    }

    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        member.setPassword(encoder.encode(member.getPassword()));
        memberRepo.save(member);
        return "User registered";
    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            String email = jwtService.extractEmail(refreshToken);

            if (!jwtService.isRefreshToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid token type", "Provided token is not a refresh token."));
            }

            Member member = memberRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken = jwtService.generateToken(member);
            String newRefreshToken = jwtService.generateRefreshToken(member);

            return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken, member.getEmail(), member.getId(), member.getPhone()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Refresh token expired", "Your session has expired. Please log in again."));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid refresh token", "The refresh token is invalid."));
        }
    }
}
