package com.leonardoramos.shortme.service;

import com.leonardoramos.shortme.dto.LinkResponse;
import com.leonardoramos.shortme.model.Link;
import com.leonardoramos.shortme.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LinkService {
    private final int SHORT_URL_MIN_LENGTH = 5;
    private final int SHORT_URL_MAX_LENGTH = 10;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse shortenUrl(String url) {
        var link = Link.builder().urlShort(generateShortLink()).urlOriginal(url).urlCreatedAt(LocalDateTime.now()).build();

        var savedLink = linkRepository.save(link);
        log.info("Short link created: " + savedLink.getUrlShort());

        return modelMapper.map(savedLink, LinkResponse.class);
    }

    private String generateShortLink() {
        log.info("Generating short link.");
        return RandomStringUtils.randomAlphabetic(SHORT_URL_MIN_LENGTH, SHORT_URL_MAX_LENGTH);
    }

    public LinkResponse getOriginalUrl(String shortUrl) throws RuntimeException {
        try {
            log.info("Getting original URL for: " + shortUrl);
            var link = linkRepository.findByUrlShort(shortUrl);
            log.info("Original URL found: " + link.getUrlOriginal());

            return modelMapper.map(link, LinkResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error getting the original URL.", e);
        }
    }
}