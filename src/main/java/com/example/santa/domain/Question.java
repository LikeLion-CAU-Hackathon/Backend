package com.example.santa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor

@Entity
@Table(name = "Question") // 테이블 이름 : Question (ERD 상에서의 이름)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_day")
    private LocalDate accessDay;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    public Question(LocalDate accessDay, String content) {
        this.accessDay = accessDay;
        this.content = content;
    }
}
