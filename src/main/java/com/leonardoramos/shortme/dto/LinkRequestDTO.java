package com.leonardoramos.shortme.dto;

import com.leonardoramos.shortme.validation.ValidUrl;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequestDTO {
    @NotNull(message = "URL is required")
    @NotEmpty(message = "URL is required")
    @ValidUrl
    private String longUrl;
}
