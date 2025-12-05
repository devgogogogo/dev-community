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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public void deletePost(UserEntity userEntity, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new BizException(PostErrorCode.POST_NOT_FOUND));
        if (!postEntity.getUserEntity().getId().equals(userEntity.getId())) {
            throw new BizException(PostErrorCode.POST_CONFLICT);
        }
        postRepository.delete(postEntity);
    }

    @Transactional(readOnly = true)
    public Page<GetPostResponse> getPosts(Pageable pageable) {
        //fetch join은 페이징을 지원하지 않는다 그래서 일단 List로 뽑아낸후에 new PageImpl을 구현해야한다.
        List<PostEntity> posts = postRepository.findAllWithUser(pageable);
        long totalCount = postRepository.countPosts();
        PageImpl<PostEntity> postPage = new PageImpl<>(posts, pageable, totalCount);
        return postPage.map(GetPostResponse::from);
    }
}
