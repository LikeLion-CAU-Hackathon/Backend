package com.example.Santa.repository;

import com.example.Santa.domain.Thumbs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbsRepository extends JpaRepository<Thumbs, Long> {

    boolean existsByAnswer_IdAndUser_Id(Long answerId, Long userId);

    long countByAnswer_Id(Long answerId);

    void deleteByAnswer_IdAndUser_Id(Long answerId, Long userId);
}