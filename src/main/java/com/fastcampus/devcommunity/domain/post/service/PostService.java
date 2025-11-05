package com.fastcampus.devcommunity.domain.post.service;

import com.fastcampus.devcommunity.domain.post.dto.request.CreatePostRequest;
import com.fastcampus.devcommunity.domain.post.dto.response.CreatePostResponse;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostEntity postEntity = new PostEntity(request.title(), request.content());
        PostEntity saved = postRepository.save(postEntity);
        return new CreatePostResponse(saved.getTitle(), saved.getContent());
    }


}
