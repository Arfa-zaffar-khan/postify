package com.postify.main.services;

import com.postify.main.dto.tagdto.TagRequestDto;
import com.postify.main.dto.tagdto.TagResponseDto;
import com.postify.main.entities.Post;
import com.postify.main.entities.Tag;
import com.postify.main.repository.PostRepository;
import com.postify.main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public Tag updateTag(String name, TagRequestDto tagRequestDto) {
        Tag tag = tagRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag with name: " + name + " not found"));
        tag.setName(tagRequestDto.getName());
        return tagRepository.save(tag);
    }

    public void deleteTagByName(String name) {
        Tag tag = tagRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag with name: " + name + " not found"));
        Page<Post> posts = postRepository.findByTagsName(name, Pageable.unpaged());
        if (!posts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tag with name: " + name + " has post references, so you cannot delete it");
        }
        tagRepository.deleteByName(name);
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag create(Tag tag) {
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Tag with name: " + tag.getName() + " already exists");
        }
        return tagRepository.save(tag);
    }

    public Tag convertToTag(TagRequestDto tagRequestDto) {
        return new Tag(0, tagRequestDto.getName());
    }

    public TagResponseDto convertToTagResponseDto(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName());
    }
}
