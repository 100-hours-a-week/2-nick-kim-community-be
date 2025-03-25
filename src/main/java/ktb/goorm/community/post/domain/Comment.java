package ktb.goorm.community.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import ktb.goorm.community.common.entity.BaseTimeEntity;
import ktb.goorm.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE comment SET deleted = true where id = ?")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean deleted;

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.deleted = Boolean.FALSE;
    }

    public boolean matchAuthor(Long id) {
        return user.equals(id);
    }

    public boolean matchPost(Long id) {
        return post.getId().equals(id);
    }

    public void editContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return this.user.getNickname();
    }

    public String getAuthorImageUrl() {
        return this.user.getProfileImageUrl();
    }
}
