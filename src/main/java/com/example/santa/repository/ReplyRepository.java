package com.example.santa.repository;

import com.example.santa.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    long countByAnswer_Id(Long answerId); // answer_id 기준으로 카운트
}