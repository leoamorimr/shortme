package com.leonardoramos.shortme.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AppErrorDTO {

    private String message;
    private List<String> errors;
    private LocalDateTime timestamp = LocalDateTime.now();

    public AppErrorDTO(String message) {
        this.message = message;
    }

    public AppErrorDTO(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
