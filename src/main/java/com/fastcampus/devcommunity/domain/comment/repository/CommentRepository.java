package com.fastcampus.devcommunity.domain.comment.repository;

import com.fastcampus.devcommunity.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {



    @Query("select c from CommentEntity c join fetch c.postEntity p join fetch c.userEntity u where c.id = :commentId and p.id = :postId")
    Optional<CommentEntity> findByIdAndPostId(@Param("commentId") Long commentId, @Param("postId") Long postId);
}
