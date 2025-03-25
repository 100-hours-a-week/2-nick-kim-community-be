package ktb.goorm.community.auth.application.port.in;

public interface AuthUseCase {
    String getPrincipalByHeader(String authorizationHeader);

    void authenticate(String authorizationHeader);
}
