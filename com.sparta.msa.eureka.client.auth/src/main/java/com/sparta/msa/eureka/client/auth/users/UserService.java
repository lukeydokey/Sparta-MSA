package com.sparta.msa.eureka.client.auth.users;

import com.sparta.msa.eureka.client.auth.core.domain.User;
import com.sparta.msa.eureka.client.auth.core.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Transactional
public class UserService {

    @Value("auth-service")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(@Value("${service.jwt.secret-key}") String secretKey,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public SignInResDto createAccessToken(String userId, UserRole role){
        return new SignInResDto(
                Jwts.builder()
                        .claim("USER_ID", userId)
                        .claim("USER_ROLE", role.toString())
                        .issuer(issuer)
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                        .signWith(secretKey, SignatureAlgorithm.HS512)
                        .compact()
        );
    }

    public UserResDto signUp(UserReqDto requestDto){
        User user = User.builder()
                .userId(requestDto.getUserId())
                .userName(requestDto.getUserName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .userRole(UserRole.MEMBER)
                .build();
        userRepository.save(user);
        return user.toResDto();
    }

    public SignInResDto signIn(SignInReqDto requestDto){
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID or password"));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid user ID or password");
        }

        return createAccessToken(user.getUserId(), user.getUserRole());
    }
}
