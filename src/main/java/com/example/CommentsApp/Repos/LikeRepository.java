package com.example.CommentsApp.Repos;

import com.example.CommentsApp.Entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
