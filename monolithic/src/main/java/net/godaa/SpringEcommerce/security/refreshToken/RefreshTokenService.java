package net.godaa.SpringEcommerce.security.refreshToken;

import net.godaa.SpringEcommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class RefreshTokenService {
    @Value("${SpringEcommerce.app.jwtRefreshExpirationMs}")
     Long refreshTokenDurationMs;
    @Autowired
     RefreshTokenRepo refreshTokenRepo;
    @Autowired
    UserRepo userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please make a new signIn request");
        }
        return token;
    }


}
