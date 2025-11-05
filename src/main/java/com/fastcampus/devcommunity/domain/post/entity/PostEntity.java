package com.fastcampus.devcommunity.domain.post.entity;

import com.fastcampus.devcommunity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "posts")
@Entity
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public PostEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
