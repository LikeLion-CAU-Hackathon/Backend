package com.example.santa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // -> CreatedDate

@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column (name = "contents", nullable = false, length = 119)
    private String contents;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @Builder
    public Answer(Question question, AppUser appUser, String nickname, String contents) {
        this.question = question;
        this.appUser = appUser;
        this.nickname = nickname;
        this.contents = contents;
    }
}
