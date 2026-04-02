package com.example.MediaTracker.repository;

import com.example.MediaTracker.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    boolean existsBytitle(String title);
}
