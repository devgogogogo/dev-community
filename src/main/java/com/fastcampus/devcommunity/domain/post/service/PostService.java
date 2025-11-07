package com.fastcampus.devcommunity.domain.post.service;

import com.fastcampus.devcommunity.common.exception.BizException;
import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.request.UpdatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.ListGetPostResponse;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.post.exception.PostErrorCode;
import com.fastcampus.devcommunity.domain.post.repository.PostRepository;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(UserEntity entity, CreatePostRequest request) {
        PostEntity postEntity = new PostEntity(request.title(), request.content(),entity);
        PostEntity saved = postRepository.save(postEntity);
        return new CreatePostResponse(saved.getTitle(), saved.getContent());
    }

    public List<ListGetPostResponse> getAllPosts() {
        List<PostEntity> entities = postRepository.findAll();
        return entities.stream().map(ListGetPostResponse::from).toList();
    }

    public GetPostResponse getPostById(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        return new GetPostResponse(postEntity.getTitle(), postEntity.getContent(),postEntity.getAuthor().getNickname());
    }

    public GetPostResponse updatePost(Long postId, Long requesterKakaoId, UpdatePostRequest request) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));

        Long authorKakaoId = postEntity.getAuthor().getKakaoId();
        if (!authorKakaoId.equals(requesterKakaoId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        postEntity.update(request.title(), request.content());
        return new GetPostResponse(postEntity.getTitle(), postEntity.getContent(),postEntity.getAuthor().getNickname());
    }

    @Transactional
    public void deletePost(Long postId, Long requesterKakaoId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));

        Long authorKakaoId = post.getAuthor().getKakaoId(); // 필드명이 entity면: post.getEntity().getKakaoId()
        if (!authorKakaoId.equals(requesterKakaoId)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post); // 하드 삭제
    }
}
