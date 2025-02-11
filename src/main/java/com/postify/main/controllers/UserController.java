package com.postify.main.controllers;

import com.postify.main.dto.userdto.UserPartialRequestDto;
import com.postify.main.dto.userdto.UserRequestDto;
import com.postify.main.dto.userdto.UserResponseDto;
import com.postify.main.entities.User;
import com.postify.main.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<UserResponseDto> allUsers = userService.getAll(page, size, sortDirection, sortBy)
                .map(user -> userService.convertToUserResponseDto(user));
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.convertToUser(userRequestDto);
        User createdUser = userService.create(user);
        UserResponseDto userResponse = userService.convertToUserResponseDto(createdUser);
        return ResponseEntity.status(201).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id){
        User user = userService.getById(id);
        UserResponseDto userResponse = userService.convertToUserResponseDto(user);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id){
        userService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable int id,@Valid @RequestBody UserRequestDto userRequestDto){
        User user = userService.convertToUser(userRequestDto);
        User updatedUser = userService.updateById(id, user);
        UserResponseDto userResponse = userService.convertToUserResponseDto(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updatePartialUserById(@PathVariable int id,@Valid @RequestBody UserPartialRequestDto userPartialRequestDto){
        User user = userService.convertToUser(userPartialRequestDto);
        User updatedUser = userService.updateById(id, user);
        UserResponseDto userResponse = userService.convertToUserResponseDto(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

}
