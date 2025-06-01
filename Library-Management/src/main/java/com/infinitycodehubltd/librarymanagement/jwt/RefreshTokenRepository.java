package com.infinitycodehubltd.librarymanagement.jwt;



import com.infinitycodehubltd.librarymanagement.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(Member user);
}

