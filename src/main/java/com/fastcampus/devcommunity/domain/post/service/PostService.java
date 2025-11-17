package com.fastcampus.devcommunity.domain.post.service;

import com.fastcampus.devcommunity.common.exception.BizException;
import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.request.PutPostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.PutPostResponse;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.post.exception.PostErrorCode;
import com.fastcampus.devcommunity.domain.post.repository.PostRepository;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request) {
        PostEntity postEntity = new PostEntity(request.title(), request.content(), userEntity);
        PostEntity saved = postRepository.save(postEntity);
        return CreatePostResponse.from(saved);
    }

    @Transactional
    public GetPostResponse getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        return GetPostResponse.from(postEntity);
    }

    @Transactional
    public PutPostResponse putPost(UserEntity userEntity, Long postId, PutPostRequest request) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        if (!postEntity.getUserEntity().getId().equals(userEntity.getId())) {
            throw new BizException(PostErrorCode.POST_CONFLICT);
        }
        postEntity.updatePost(request.title(),request.content());
        return PutPostResponse.from(postEntity);
    }
}
