package com.infinitycodehubltd.librarymanagement.jwt;



import com.infinitycodehubltd.librarymanagement.jwt.JwtService;
import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Step 3: Generate JWT token
        String token = jwtService.generateToken(member);

        // Step 4: Return token
        return new AuthResponse(token, member.getEmail(), member.getId(), member.getPhone());
    }

    @PostMapping("/register")
    public String register(@RequestBody Member member) {
        member.setPassword(encoder.encode(member.getPassword()));
        memberRepo.save(member);
        return "User registered";
    }
}
