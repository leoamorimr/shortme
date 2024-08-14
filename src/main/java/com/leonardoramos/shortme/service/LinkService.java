package com.leonardoramos.shortme.service;

import com.leonardoramos.shortme.dto.LinkResponseDTO;
import com.leonardoramos.shortme.exception.ShortUrlNotFoundException;
import com.leonardoramos.shortme.model.Link;
import com.leonardoramos.shortme.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkService {
    private final int SHORT_URL_MIN_LENGTH = 5;
    private final int SHORT_URL_MAX_LENGTH = 10;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponseDTO shortenUrl(String url) {
        var link = new Link(generateShortLink(), url);

        log.info("Saving short link to DB");
        var savedLink = linkRepository.save(link);

        return modelMapper.map(savedLink, LinkResponseDTO.class);
    }

    private String generateShortLink() {
        log.info("Generating short link.");
        var randomUrl = RandomStringUtils.randomAlphabetic(SHORT_URL_MIN_LENGTH, SHORT_URL_MAX_LENGTH);

        if (linkRepository.existsByShortUrl(randomUrl)) {
            // If the generated short URL already exists, generate a new one
            generateShortLink();
        }
        return randomUrl;
    }

    public LinkResponseDTO getOriginalUrl(String shortUrl) {

        log.info("Getting original URL for: {}", shortUrl);
        var link = linkRepository.findByShortUrl(shortUrl);

        if (link == null) {
            throw new ShortUrlNotFoundException("Short URL not found!");
        }

        return modelMapper.map(link, LinkResponseDTO.class);
    }
}