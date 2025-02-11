package com.postify.main.services;

import com.postify.main.dto.userdto.UserPartialRequestDto;
import com.postify.main.dto.userdto.UserRequestDto;
import com.postify.main.dto.userdto.UserResponseDto;
import com.postify.main.entities.User;
import com.postify.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    public User create(User user) {
        Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username:" + user.getUserName() + " is already exist!!");
        }
        return userRepository.save(user);
    }

    public Page<User> getAll(int page, int size, String sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return userRepository.findAll(pageable);
    }

    public User getById(int id) {
        // Check if the user is in Redis
        String redisKey = "user:" + id;
        User user = redisService.retrieveData(redisKey, User.class);

        if (user != null) {
            // User found in Redis, return it
            return user;
        }

        // User not found in Redis, query the database
        user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found"));

        // Store the user in Redis
        redisService.storeData(redisKey, user,1L, TimeUnit.MINUTES);

        // Return the user
        return user;
    }

    public void deleteById(int id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public User updateById(int id, User user) {
        User existingUser = getById(id);
        if (user.getUserName() != null) {
            existingUser.setUserName(user.getUserName());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        return userRepository.save(existingUser);
    }


    public User convertToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUserName(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        return user;
    }
    public User convertToUser(UserPartialRequestDto userPartialRequestDto) {
        User user = new User();
        user.setUserName(userPartialRequestDto.getUsername());
        user.setPassword(userPartialRequestDto.getPassword());
        user.setEmail(userPartialRequestDto.getEmail());
        return user;
    }

    public UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUserName());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedDate(user.getCreatedDate());
        userResponseDto.setLastModifiedDate(user.getLastModifiedDate());
        return userResponseDto;
    }


}
