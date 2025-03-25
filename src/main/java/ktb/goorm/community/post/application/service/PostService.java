package ktb.goorm.community.post.application.service;

import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import ktb.goorm.community.image.application.port.out.ImagePersistencePort;
import ktb.goorm.community.post.application.port.in.CommentUseCase;
import ktb.goorm.community.post.application.port.in.LikeUseCase;
import ktb.goorm.community.post.application.port.in.PostUseCase;
import ktb.goorm.community.post.application.port.in.dto.CommentDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentEditCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentRealModel;
import ktb.goorm.community.post.application.port.in.dto.CommentWriteCommand;
import ktb.goorm.community.post.application.port.in.dto.LikeCommand;
import ktb.goorm.community.post.application.port.in.dto.PostBoardResultModel;
import ktb.goorm.community.post.application.port.in.dto.PostDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.PostEditCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingResult;
import ktb.goorm.community.post.application.port.in.dto.PostLookupCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupResult;
import ktb.goorm.community.post.application.port.in.dto.PostWriteCommand;
import ktb.goorm.community.post.application.port.out.CommentPersistencePort;
import ktb.goorm.community.post.application.port.out.LikePersistencePort;
import ktb.goorm.community.post.application.port.out.PostPersistencePort;
import ktb.goorm.community.post.domain.Comment;
import ktb.goorm.community.post.domain.Like;
import ktb.goorm.community.post.domain.Post;
import ktb.goorm.community.user.application.port.out.UserPersistencePort;
import ktb.goorm.community.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService implements PostUseCase, CommentUseCase, LikeUseCase {
    private final PostPersistencePort postPersistencePort;
    private final CommentPersistencePort commentPersistencePort;
    private final LikePersistencePort likePersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final ImagePersistencePort imagePersistencePort;

    @Override
    @Transactional(readOnly = true)
    public PostLookupBoardPagingResult lookupBoardWithCursor(PostLookupBoardPagingCommand command) {
        List<PostBoardResultModel> posts = fetchPostWithCursorAndSize(command.cursor(), command.limit()); // 다음것이 있는지 확인하기 위한 limit + 1

        if (posts.isEmpty()) {
            return new PostLookupBoardPagingResult(null, null);
        }

        Long nextCursor = posts.getLast().postId() + 1;

        //내가 원하는 개수보다 적게 가져왔으면, 다음 것이없는 것
        if (posts.size() < command.limit()) {
            nextCursor = null;
        }

        return new PostLookupBoardPagingResult(posts.subList(0, command.limit()), String.valueOf(nextCursor));
    }

    @Override
    @Transactional(readOnly = true)
    public PostLookupResult lookupPost(PostLookupCommand command) {
        User user = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());
        boolean isLiked = likePersistencePort.existsLikeByUserIdAndPostId(user.getId(), post.getId());

        return convertLookupResultFromPostInfo(post, isLiked);
    }

    @Override
    public void writePost(PostWriteCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        String attachmentImageUrl = imagePersistencePort.save(command.contentImage());

        Post post = new Post(
                command.title(),
                command.content(),
                attachmentImageUrl,
                author
        );

        postPersistencePort.save(post);
    }

    @Override
    public void editPost(PostEditCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());

        checkAuthorOfPost(post, author);

        String attachmentImageUrl = imagePersistencePort.save(command.contentImage());
        post.edit(command.title(), command.content(), attachmentImageUrl);
    }

    @Override
    public void deletePost(PostDeleteCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());

        checkAuthorOfPost(post, author);

        postPersistencePort.delete(post);
    }

    @Override
    public void createComment(CommentWriteCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());

        Comment comment = new Comment(command.content(), author, post);
        commentPersistencePort.save(comment);
    }

    @Override
    public void editComment(CommentEditCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());
        Comment comment = findCommentByIdOrThrow(command.commentId());

        checkAuthorOfComment(comment, author);
        checkCommentOfPost(post, comment);

        comment.editContent(command.content());
    }

    @Override
    public void deleteComment(CommentDeleteCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());
        Comment comment = findCommentByIdOrThrow(command.commentId());

        checkAuthorOfComment(comment, author);
        checkCommentOfPost(post, comment);

        commentPersistencePort.delete(comment);
    }


    @Override
    public void like(LikeCommand command) {
        User author = findUserByEmailOrThrow(command.email());
        Post post = findPostByIdOrThrow(command.postId());

        likePersistencePort.findLikeByUserIdAndPostId(author.getId(), post.getId())
                .ifPresentOrElse(
                        Like::switchDisliked,
                        () -> likePersistencePort.save(new Like(author.getId(), post))
                );
    }

    private PostLookupResult convertLookupResultFromPostInfo(Post post, boolean isLiked) {

        return new PostLookupResult(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAttachmentImageUrl(),
                post.getAuthorNickname(),
                post.getAuthorImageUrl(),
                post.getLikesCount(),
                post.getCommentCount(),
                post.getLookupCount(),
                isLiked,
                post.getCreatedAt(),
                post.getComments().stream()
                        .map(comment -> new CommentRealModel(
                                comment.getId(),
                                comment.getAuthor(),
                                comment.getAuthorImageUrl(),
                                comment.getContent(),
                                comment.getCreatedAt()
                        ))
                        .toList()
        );
    }

    private List<PostBoardResultModel> fetchPostWithCursorAndSize(String cursor, Integer size) {
        return postPersistencePort.findPostWithCursorAndLimit(cursor, size)
                .stream()
                .map(post -> new PostBoardResultModel(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthorNickname(),
                        post.getAuthorImageUrl(),
                        post.getLikesCount(),
                        post.getCommentCount(),
                        post.getLookupCount(),
                        post.getCreatedAt()
                ))
                .toList();
    }

    private void checkAuthorOfPost(Post post, User author) {
        if (!post.matchAuthor(author.getId())) {
            throw new BusinessException(ErrorCodeAndMessage.USER_UNAUTHORIZED);
        }
    }


    private void checkAuthorOfComment(Comment comment, User author) {
        if (!comment.matchAuthor(author.getId())) {
            throw new BusinessException(ErrorCodeAndMessage.USER_UNAUTHORIZED);
        }
    }


    private void checkCommentOfPost(Post post, Comment comment) {
        if (!comment.matchPost(post.getId())) {
            throw new BusinessException(ErrorCodeAndMessage.POST_NOT_FOUND);
        }
    }

    private User findUserByEmailOrThrow(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeAndMessage.USER_NOT_FOUND));
    }

    private Post findPostByIdOrThrow(Long id) {
        return postPersistencePort.findPostById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeAndMessage.POST_NOT_FOUND));
    }

    private Comment findCommentByIdOrThrow(Long id) {
        return commentPersistencePort.findCommentById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodeAndMessage.POST_COMMENT_NOT_FOUND));
    }
}
