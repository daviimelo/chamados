package projeto.chamados.core.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                    "minha-chave-ultra-secreta-de-pelo-menos-256-bits!".getBytes()
            );

    @Override
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .subject(authentication.getName())
                .claim("authorities",
                        authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }
}
