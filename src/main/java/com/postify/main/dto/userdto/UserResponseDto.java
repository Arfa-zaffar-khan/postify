package com.postify.main.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
