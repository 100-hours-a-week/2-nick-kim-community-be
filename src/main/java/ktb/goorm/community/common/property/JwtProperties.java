package ktb.goorm.community.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt.token")
public record JwtProperties(
        String secretKey,
        long expireLength
) {
}
