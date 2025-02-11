package com.postify.main.dto.postdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPartialRequestDto {
    @Size(min = 3, max = 100, message = "title must be between 3 and 100 characters")
    private String title;

    @Size(min = 5, max = 255, message = "description must be between 5 and 255 characters")
    private String description;
    private Set<String> tags=new HashSet<>();
}