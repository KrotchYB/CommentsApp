package com.example.CommentsApp.Controllers;

import com.example.CommentsApp.Entities.Comment;
import com.example.CommentsApp.Requests.CommentCreateRequest;
import com.example.CommentsApp.Requests.CommentUpdateRequest;
import com.example.CommentsApp.Services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComment(@RequestParam Optional<Long> userId,
                                       @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId, postId);
    }



    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,
                                    @RequestParam CommentUpdateRequest request){
        return  commentService.updateOneCommentById(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOnePostById(commentId);
    }
}
