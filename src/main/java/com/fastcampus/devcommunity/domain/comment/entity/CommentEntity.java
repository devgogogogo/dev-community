package com.fastcampus.devcommunity.domain.comment.entity;

import com.fastcampus.devcommunity.common.BaseEntity;
import com.fastcampus.devcommunity.domain.post.entity.PostEntity;
import com.fastcampus.devcommunity.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@Entity
@Getter
@NoArgsConstructor
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Entity_id")
    private PostEntity post;

    public CommentEntity(String content, UserEntity user, PostEntity post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
    public void updateContent(String content) {
        this.content = content;
    }
}
