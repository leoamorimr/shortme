package com.leonardoramos.shortme.repository;

import com.leonardoramos.shortme.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByShortUrl(String shortUrl);

    Boolean existsByShortUrl(String shortUrl);

    @Modifying
    @Transactional
    @Query("DELETE FROM Link l WHERE l.urlCreatedAt <= :beforeDate")
    void deleteOldLinks(@Param("beforeDate") LocalDateTime beforeDate);
}
