package com.example.tech.springblog.service;


import com.example.tech.springblog.controller.PostDto;
import com.example.tech.springblog.exception.PostNotFoundException;
import com.example.tech.springblog.model.Post;
import com.example.tech.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    public void creatPost(PostDto postDto){
        Post post = mapFromDtoToPost(postDto);
//        post.setTitle(post.getTitle());
//        post.setContent(post.getContent());
//        User username = authService.getCurrentUser().orElseThrow(()->
//                new IllegalArgumentException("No user logged in"));
//        post.setUsername(username.getUsername());
        postRepository.save(post);
//
//        post.setCreatedOn(Instant.now());

    }

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        // loop through the list - JAVA 8 streams
        // any data between the client and server should be done through the dto
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUsername(post.getUsername());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User username = authService.getCurrentUser().orElseThrow(()->
                new IllegalArgumentException("No user logged in"));
        post.setUsername(username.getUsername());
        postRepository.save(post);
        post.setCreatedOn(Instant.now());
        return post;
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id" + id));
        return mapFromPostToDto(post);
    }
}
