package ktb.goorm.community.auth.adapter.out;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ktb.goorm.community.auth.application.port.out.UserTokenPort;
import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import ktb.goorm.community.common.property.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserJwtAdapter implements UserTokenPort {

    private final JwtProperties jwtProperties;

    @Override
    public String createToken(String principal) {
        Date validity = new Date(new Date().getTime() + jwtProperties.expireLength());

        return getJwtBuilderWithPrincipal(principal)
                .setExpiration(validity)
                .compact();
    }

    @Override
    public String getPrincipal(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.secretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCodeAndMessage.JWT_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeAndMessage.JWT_INVALID);
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.secretKey()).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCodeAndMessage.JWT_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeAndMessage.JWT_INVALID);
        }
    }

    private JwtBuilder getJwtBuilderWithPrincipal(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey());
    }
}

