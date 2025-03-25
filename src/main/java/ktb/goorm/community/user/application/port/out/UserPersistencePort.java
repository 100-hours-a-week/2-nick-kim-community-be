package ktb.goorm.community.user.application.port.out;

import ktb.goorm.community.user.domain.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findByEmail(String email);

    boolean existByEmail(String email);

    boolean existByNickname(String nickname);

    void save(User user);

    void deleteUser(User user);
}
