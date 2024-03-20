package com.example.CommentsApp.Services;

import com.example.CommentsApp.Entities.Comment;
import com.example.CommentsApp.Entities.Like;
import com.example.CommentsApp.Entities.Post;
import com.example.CommentsApp.Entities.User;
import com.example.CommentsApp.Repos.LikeRepository;
import com.example.CommentsApp.Requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private LikeRepository likeRepository;

    private UserService userService;
    private PostService postService;
    public LikeService(LikeRepository likeRepository,
                       UserService userService,
                       PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Like> getAllLikesWithParam(Optional<Long> userId,
                                           Optional<Long> postId) {

        if(userId.isPresent() && postId.isPresent()){
            return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            return  likeRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            return  likeRepository.findByPostId(postId.get());
        }
        else return likeRepository.findAll();
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());

        if(user != null && post != null){
            Like toSave = new Like();
            toSave.setId(request.getId());
            toSave.setPost(post);
            toSave.setUser(user);
            return likeRepository.save(toSave);
        }

        return null;
    }
}
