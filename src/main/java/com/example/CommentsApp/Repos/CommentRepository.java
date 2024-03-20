package com.example.CommentsApp.Repos;

import com.example.CommentsApp.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>
{

}
