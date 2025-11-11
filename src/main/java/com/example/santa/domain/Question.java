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

    @Column(name = "content",nullable = false, length=30)
    private String content;

    @Column(name = "access_day")
    private LocalDate accessDay;

}
