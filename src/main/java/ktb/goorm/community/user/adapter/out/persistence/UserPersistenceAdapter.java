package ktb.goorm.community.user.adapter.out.persistence;

import ktb.goorm.community.user.adapter.out.persistence.repository.UserRepository;
import ktb.goorm.community.user.application.port.out.UserPersistencePort;
import ktb.goorm.community.user.domain.Email;
import ktb.goorm.community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;

    @Override
    public boolean existByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public boolean existByNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(new Email(email));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
