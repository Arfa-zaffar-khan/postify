package com.postify.main.services;


import com.postify.main.enteties.Post;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;


@Service
public class PostService {

//    public void create(Post post) {
//        posts.put(post.getId(), post);
//    }

//    public Post getPost(int id) {
//        Post post = posts.get(id);
//        if (post == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with id " + id + " not found");
//        }
//        return post;
//    }
//
//    public void update(int id, Post post) {
//        getPost(id);
//        post.setId(id);
//        posts.put(id, post);
//    }
//
//    public void delete(int id) {
//        getPost(id);
//        posts.remove(id);
//    }
//
//
//    public Collection<Post> getAll() {
//        return posts.values();
//    }
}
