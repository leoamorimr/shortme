package com.leonardoramos.shortme.repository;

import com.leonardoramos.shortme.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByShortUrl(String shortUrl);
    Boolean existsByShortUrl(String shortUrl);
}
