package com.leonardoramos.shortme.controller;

import com.leonardoramos.shortme.dto.LinkRequest;
import com.leonardoramos.shortme.dto.LinkResponse;
import com.leonardoramos.shortme.service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;

@RestController()
@Slf4j
public class LinkController {

    @Autowired
    public LinkService linkService;

    @Value("${server.port}")
    String serverPort;

    @Value("${SERVER_URL}")
    String serverAddress;

    @PostMapping
    public ResponseEntity<LinkResponse> createShortLink(@RequestBody @Valid LinkRequest request) throws UnknownHostException {
        LinkResponse generatedUrl = linkService.shortenUrl(request.getUrlOriginal());

        String shortUrl = serverAddress + ":" + serverPort + "/r/" + generatedUrl.getUrlShort();

        LinkResponse response = new LinkResponse(generatedUrl.getUrlOriginal(), shortUrl, generatedUrl.getUrlCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/r/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        try {
            LinkResponse urlOriginal = linkService.getOriginalUrl(shortUrl);

            if (urlOriginal != null) {
                response.sendRedirect(urlOriginal.getUrlOriginal());
            } else {
                log.info("Original URL not found  for: {}", shortUrl);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
