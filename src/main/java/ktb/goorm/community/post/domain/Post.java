package ktb.goorm.community.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import ktb.goorm.community.common.entity.BaseTimeEntity;
import ktb.goorm.community.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete(columnName = "deleted")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String title;

    @Getter
    private String content;

    @Getter
    private String attachmentImageUrl;

    @Getter
    private Integer lookupCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String attachmentImageUrl, User user) {
        this.title = title;
        this.content = content;
        this.attachmentImageUrl = attachmentImageUrl;
        this.lookupCount = 0;
        this.user = user;
    }

    public boolean matchAuthor(Long id) {
        return this.user.getId().equals(id);
    }

    public void edit(String title, String content, String attachmentImageUrl) {
        this.title = title;
        this.content = content;
        this.attachmentImageUrl = attachmentImageUrl;
    }

    public void increaseLookupCount() {
        this.lookupCount++;
    }

    public int getCommentCount() {
        return comments.size();
    }

    public int getLikesCount() {
        return likes.size();
    }

    public String getAuthorNickname() {
        return user.getNickname();
    }

    public String getAuthorImageUrl() {
        return user.getProfileImageUrl();
    }

}
