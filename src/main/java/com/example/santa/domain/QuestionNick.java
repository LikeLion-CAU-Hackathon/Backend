package com.example.santa.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "question_nick",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "question_id"})
)
@NoArgsConstructor
@Getter
public class QuestionNick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_adj_id", nullable = false)
    private DailyAdj dailyAdj;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_noun_id", nullable = false)
    private DailyNoun dailyNoun;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Builder
    public QuestionNick(AppUser appUser, Question question,
                        DailyAdj dailyAdj, DailyNoun dailyNoun,
                        String nickname) {
        this.appUser = appUser;
        this.question = question;
        this.dailyAdj = dailyAdj;
        this.dailyNoun = dailyNoun;
        this.nickname = nickname;
    }
}
