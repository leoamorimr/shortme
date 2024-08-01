package com.leonardoramos.shortme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppErrorDTO {

    private String message;
    private List<String> errors;


}
