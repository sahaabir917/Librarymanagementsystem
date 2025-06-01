package com.infinitycodehubltd.librarymanagement.jwt;




import com.infinitycodehubltd.librarymanagement.user.Member;
import com.infinitycodehubltd.librarymanagement.user.MemberRepository;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, MemberRepository memberRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(memberRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new login request");
        }
        return token;
    }

    public int deleteByUserId(Long userId) {
        Member user = memberRepository.findById(userId).get();
        return refreshTokenRepository.deleteByUser(user);
    }
}

