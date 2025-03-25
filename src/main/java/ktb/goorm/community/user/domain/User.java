package ktb.goorm.community.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE user SET deleted = true where id = ?")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Getter
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Getter
    @Column(name = "profile_image_url", nullable = true)
    private String profileImageUrl;

    private Boolean deleted;

    public User(String email, String password, String nickname, String profileImageUrl) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.deleted = Boolean.FALSE;
    }

    public void updateUserInfo(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void updatePassword(String password) {
        this.password.updatePassword(password);
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getPassword() {
        return this.password.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Long) {
            return Objects.equals(this.getId(), (Long) o);
        }
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
