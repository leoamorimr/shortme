package com.leonardoramos.shortme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {
    String urlOriginal;
    String urlShort;
    LocalDateTime urlCreatedAt;
}
