package com.example.CommentsApp.Services;

import com.example.CommentsApp.Entities.Comment;
import com.example.CommentsApp.Entities.Post;
import com.example.CommentsApp.Entities.User;
import com.example.CommentsApp.Repos.CommentRepository;
import com.example.CommentsApp.Requests.CommentCreateRequest;
import com.example.CommentsApp.Requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId,
                                                 Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            return  commentRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            return  commentRepository.findByPostId(postId.get());
        }
        else return commentRepository.findAll();
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());

        if(user != null && post != null){
            Comment toSave = new Comment();
            toSave.setId(request.getId());
            toSave.setPost(post);
            toSave.setUser(user);
            toSave.setText(request.getText());
            return commentRepository.save(toSave);
        }

        return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment toUpdate = comment.get();
            toUpdate.setText(request.getText());
            return  commentRepository.save(toUpdate);
        }

        return null;
    }

    public void deleteOnePostById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
