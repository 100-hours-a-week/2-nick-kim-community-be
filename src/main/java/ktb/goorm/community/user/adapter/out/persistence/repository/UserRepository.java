package ktb.goorm.community.user.adapter.out.persistence.repository;

import ktb.goorm.community.user.domain.Email;
import ktb.goorm.community.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);

    Optional<User> findByNickname(String nickname);
}
