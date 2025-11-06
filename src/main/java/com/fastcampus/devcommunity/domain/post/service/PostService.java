package com.fastcampus.devcommunity.domain.post.service;

import com.fastcampus.devcommunity.common.exception.BizException;
import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.GetPostResponse;
import com.fastcampus.devcommunity.domain.post.dto.response.ListGetPostResponse;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.post.exception.PostErrorCode;
import com.fastcampus.devcommunity.domain.post.repository.PostRepository;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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
        return new GetPostResponse(postEntity.getTitle(), postEntity.getContent());
    }
}
