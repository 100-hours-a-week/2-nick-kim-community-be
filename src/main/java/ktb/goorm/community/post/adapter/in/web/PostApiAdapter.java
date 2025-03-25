package ktb.goorm.community.post.adapter.in.web;

import ktb.goorm.community.auth.application.port.in.AuthUseCase;
import ktb.goorm.community.common.dto.BaseResponse;
import ktb.goorm.community.common.dto.ResponseCodeAndMessage;
import ktb.goorm.community.post.adapter.in.web.dto.CommentEditRequest;
import ktb.goorm.community.post.adapter.in.web.dto.CommentWriteRequest;
import ktb.goorm.community.post.adapter.in.web.dto.PostLookupBoardPagingResponse;
import ktb.goorm.community.post.adapter.in.web.dto.PostLookupResponse;
import ktb.goorm.community.post.adapter.in.web.dto.PostModifyRequest;
import ktb.goorm.community.post.adapter.in.web.dto.PostWriteRequest;
import ktb.goorm.community.post.application.port.in.CommentUseCase;
import ktb.goorm.community.post.application.port.in.LikeUseCase;
import ktb.goorm.community.post.application.port.in.PostUseCase;
import ktb.goorm.community.post.application.port.in.dto.CommentDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentEditCommand;
import ktb.goorm.community.post.application.port.in.dto.CommentWriteCommand;
import ktb.goorm.community.post.application.port.in.dto.LikeCommand;
import ktb.goorm.community.post.application.port.in.dto.PostDeleteCommand;
import ktb.goorm.community.post.application.port.in.dto.PostEditCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupBoardPagingResult;
import ktb.goorm.community.post.application.port.in.dto.PostLookupCommand;
import ktb.goorm.community.post.application.port.in.dto.PostLookupResult;
import ktb.goorm.community.post.application.port.in.dto.PostWriteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_COMMENT_DELETE_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_COMMENT_MODIFY_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_COMMENT_WRITE_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_DELETE_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_LOOKUP_BOARD_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_LOOKUP_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_MODIFY_SUCCESS;
import static ktb.goorm.community.common.dto.ResponseCodeAndMessage.POST_WRITE_SUCCESS;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiAdapter {
    private final PostUseCase postUseCase;
    private final LikeUseCase likeUseCase;
    private final CommentUseCase commentUseCase;
    private final AuthUseCase authUseCase;

    private final String AUTHORIZATION_HEADER = "Authorization";

    @GetMapping
    public ResponseEntity<BaseResponse> lookupBoardWithPaging(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @RequestParam(value = "cursor", defaultValue = "0") String cursor,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        authUseCase.authenticate(authorizationHeader);
        PostLookupBoardPagingResult result = postUseCase.lookupBoardWithCursor(new PostLookupBoardPagingCommand(cursor, limit));
        PostLookupBoardPagingResponse response = PostLookupBoardPagingResponse.from(result);
        return ResponseEntity.ok(new BaseResponse(POST_LOOKUP_BOARD_SUCCESS, response));
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<BaseResponse> lookUpPost(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId
    ) {
        String email = authUseCase.getPrincipalByHeader(authorizationHeader);
        PostLookupResult result = postUseCase.lookupPost(new PostLookupCommand(email, postId));
        PostLookupResponse response = PostLookupResponse.from(result);
        return ResponseEntity.ok(new BaseResponse(POST_LOOKUP_SUCCESS, response));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> writePost(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @RequestPart("request") PostWriteRequest request,
            @RequestPart("contentImage") MultipartFile file
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        postUseCase.writePost(new PostWriteCommand(request.title(), request.content(), file, principal));
        return ResponseEntity.ok(new BaseResponse(POST_WRITE_SUCCESS, null));
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> modifyPost(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId,
            @RequestPart("request") PostModifyRequest request,
            @RequestPart("contentImage") MultipartFile file

    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        postUseCase.editPost(new PostEditCommand(postId, request.title(), request.content(), file, principal));
        return ResponseEntity.ok(new BaseResponse(POST_MODIFY_SUCCESS, null));
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<BaseResponse> deletePost(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        postUseCase.deletePost(new PostDeleteCommand(postId, principal));
        return ResponseEntity.ok(new BaseResponse(POST_DELETE_SUCCESS, null));
    }


    @PostMapping(value = "/{postId}/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> writeComment(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId,
            @RequestBody CommentWriteRequest request
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        commentUseCase.createComment(new CommentWriteCommand(postId, principal, request.content()));
        return ResponseEntity.ok(new BaseResponse(POST_COMMENT_WRITE_SUCCESS, null));
    }

    @PatchMapping(value = "/{postId}/comment/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> modifyComment(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentEditRequest request
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        commentUseCase.editComment(new CommentEditCommand(postId, commentId, principal, request.content()));
        return ResponseEntity.ok(new BaseResponse(POST_COMMENT_MODIFY_SUCCESS, null));
    }

    @DeleteMapping(value = "/{postId}/comment/{commentId}")
    public ResponseEntity<BaseResponse> deleteComment(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        commentUseCase.deleteComment(new CommentDeleteCommand(postId, commentId, principal));
        return ResponseEntity.ok(new BaseResponse(POST_COMMENT_DELETE_SUCCESS, null));
    }

    @PostMapping(value = "/{postId}/like")
    public ResponseEntity<BaseResponse> like(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader,
            @PathVariable("postId") Long postId
    ) {
        String principal = authUseCase.getPrincipalByHeader(authorizationHeader);
        likeUseCase.like(new LikeCommand(principal, postId));
        return ResponseEntity.ok(new BaseResponse(ResponseCodeAndMessage.POST_LIKE_SUCCESS, null));
    }
}
