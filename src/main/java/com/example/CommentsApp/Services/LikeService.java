package com.example.CommentsApp.Services;

import com.example.CommentsApp.Entities.Comment;
import com.example.CommentsApp.Entities.Like;
import com.example.CommentsApp.Entities.Post;
import com.example.CommentsApp.Entities.User;
import com.example.CommentsApp.Repos.LikeRepository;
import com.example.CommentsApp.Requests.LikeCreateRequest;
import com.example.CommentsApp.Responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId,
                                                   Optional<Long> postId) {
        List<Like> likeList;
        if(userId.isPresent() && postId.isPresent()){
            likeList = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            likeList =  likeRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            likeList =  likeRepository.findByPostId(postId.get());
        }
        else likeList = likeRepository.findAll();

        return likeList.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
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
