package com.fastcampus.devcommunity.domain.comment.repository;

import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository  extends JpaRepository<CommentEntity,Long> {


    @Query("select c from CommentEntity c join fetch c.post p where c.id= :commentId and p.id= :postId")
    Optional<CommentEntity> findByIdAndPostId(Long commentId, Long postId);

    @Query("select c from CommentEntity c join fetch c.user u where c.id = :commentId and c.post.id = :postId")
    Optional<CommentEntity> findWithUserByIdAndPostId(Long commentId, Long postId);

//    @EntityGraph(attributePaths = {"post", "user"})
//    Optional<CommentEntity> findByIdAndPostIda(Long commentId, Long postId);
}
