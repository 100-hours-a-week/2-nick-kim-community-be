package ktb.goorm.community.auth.application;

import ktb.goorm.community.auth.application.port.in.AuthUseCase;
import ktb.goorm.community.auth.application.port.out.UserTokenPort;
import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserTokenPort userTokenPort;

    private final String BEARER = "Bearer";

    @Override
    public String getPrincipalByHeader(String authorizationHeader) {
        authenticate(authorizationHeader);
        String token = extractBearerToken(authorizationHeader);
        return userTokenPort.getPrincipal(token);
    }

    @Override
    public void authenticate(String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (!userTokenPort.validateToken(token)) {
            throw new BusinessException(ErrorCodeAndMessage.UNAUTHORIZED);
        }
    }

    private String extractBearerToken(String value) {
        if ((value.startsWith(BEARER))) {
            String authHeaderValue = value.substring(BEARER.length()).trim();
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return authHeaderValue;
        }

        return Strings.EMPTY;
    }
}
