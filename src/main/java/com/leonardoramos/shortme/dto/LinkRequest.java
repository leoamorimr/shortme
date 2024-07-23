package com.leonardoramos.shortme.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequest {
    @NotNull(message = "URL original is required")
    @NotEmpty(message = "URL original is required")
    private String urlOriginal;
}
