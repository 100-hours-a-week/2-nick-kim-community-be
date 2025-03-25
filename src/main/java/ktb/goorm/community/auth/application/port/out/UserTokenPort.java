package ktb.goorm.community.auth.application.port.out;

public interface UserTokenPort {
    String createToken(String principal);

    String getPrincipal(String token);

    boolean validateToken(String token);
}
