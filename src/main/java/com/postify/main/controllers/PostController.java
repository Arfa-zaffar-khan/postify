package com.postify.main.controllers;

import com.postify.main.enteties.Post;
import com.postify.main.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

//    @GetMapping("")
//    public ResponseEntity<Collection<Post>> getPosts(){
//        return new ResponseEntity<Collection<Post>>(postService.getAll(), HttpStatus.OK);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<String> createPost(@RequestBody Post post){
//        postService.create(post);
//        return new ResponseEntity<String> ("post created successfully",HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable int id){
//        return new ResponseEntity<Post>(postService.getPost(id),HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updatePostById(@PathVariable int id,@RequestBody Post post){
//        postService.update(id,post);
//        return new ResponseEntity<String>("post updated successfully",HttpStatus.OK);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<String> updatePostPartialById(@PathVariable int id,@RequestBody Post post){
//       Post oldPost=postService.getPost(id);
//       oldPost.setTitle(post.getTitle()!=null?post.getTitle(): oldPost.getTitle());
//       oldPost.setDescription(post.getDescription()!=null?post.getDescription():oldPost.getDescription());
//       oldPost.setTags(post.getTags()!=null?post.getTags():oldPost.getTags());
//       postService.update(id,oldPost);
//        return new ResponseEntity<String>("post updated successfully",HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deletePostById(@PathVariable int id){
//        postService.delete(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
