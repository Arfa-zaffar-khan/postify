package com.postify.main.controllers;

import com.postify.main.dto.postdto.PostPartialRequestDto;
import com.postify.main.dto.postdto.PostRequestDto;
import com.postify.main.dto.postdto.PostResponseDto;
import com.postify.main.entities.Post;
import com.postify.main.entities.Tag;
import com.postify.main.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<PostResponseDto> posts = postService.getAll(page, size, sortDirection, sortBy)
                .map(postService::convertToPostResponseDto);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(name = "value", defaultValue = "") String search
    ) {
        Page<PostResponseDto> posts = postService.searchPosts(page, size, sortDirection, sortBy, search)
                .map(postService::convertToPostResponseDto);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("user/{userId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable int userId,@Valid @RequestBody PostRequestDto postRequestDto) {
        Post post = postService.convertToPost(postRequestDto);
        Post createdPost = postService.create(post,userId);
        return ResponseEntity.status(201).body(postService.convertToPostResponseDto(createdPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.convertToPostResponseDto(postService.getPost(id)));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<PostResponseDto> posts = postService.getByTitle(title, page, size, sortDirection, sortBy)
                .map(postService::convertToPostResponseDto);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<PostResponseDto> posts = postService.getByTag(tag, page, size, sortDirection, sortBy)
                .map(postService::convertToPostResponseDto);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}/users/{userId}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable int id, @Valid @RequestBody PostRequestDto postRequestDto
            ,@PathVariable int userId) {
        Post updatedPost = postService.update(id, postService.convertToPost(postRequestDto),userId);
        return ResponseEntity.ok(postService.convertToPostResponseDto(updatedPost));
    }

    @PatchMapping("/{id}/users/{userId}")
    public ResponseEntity<PostResponseDto> updatePostPartialById(@PathVariable int id, @Valid @RequestBody PostPartialRequestDto postPartialRequestDto
            , @PathVariable int userId) {
        Post post = postService.convertToPost(postPartialRequestDto);
        Post updatedPost = postService.update(id, post,userId);
        return ResponseEntity.ok(postService.convertToPostResponseDto(updatedPost));
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id,@PathVariable int userId) {
        postService.delete(id,userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getUserPostsById(@PathVariable int userId) {
        List<Post> userPosts = postService.getUserPosts(userId);
        List<PostResponseDto> posts = userPosts.stream().map(post -> postService.convertToPostResponseDto(post)).toList();
        return ResponseEntity.ok(posts);
    }
}
