package com.example.CommentsApp.Requests;

import lombok.Data;

@Data
public class LikeCreateRequest {

    Long id;
    Long userId;
    Long postId;
}
