package com.leonardoramos.shortme.controller;

import com.leonardoramos.shortme.dto.LinkRequestDTO;
import com.leonardoramos.shortme.dto.LinkResponseDTO;
import com.leonardoramos.shortme.service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/")
@Slf4j
public class LinkController {

    @Value("${server.port}")
    String serverPort;

    @Value("${api.shortme.redirect.url}")
    String apiRedirectUrl;

    @Autowired
    private LinkService linkService;

    @PostMapping
    public ResponseEntity<LinkResponseDTO> createShortLink(@RequestBody @Valid LinkRequestDTO request) {
        LinkResponseDTO generatedUrl = linkService.shortenUrl(request.getLongUrl());

        String shortUrl = apiRedirectUrl + "/r/" + generatedUrl.getShortUrl();

        LinkResponseDTO response = new LinkResponseDTO(generatedUrl.getLongUrl(), shortUrl, generatedUrl.getUrlCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/r/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws Exception {
        LinkResponseDTO urlOriginal = linkService.getOriginalUrl(shortUrl);

        if (urlOriginal != null) {
            response.sendRedirect(urlOriginal.getLongUrl());
        } else {
            log.info("Original URL not found  for: {}", shortUrl);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
