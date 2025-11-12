package com.example.santa.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@NoArgsConstructor
public class DailyNick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adj_id")
    private DailyAdj dailyAdj;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noun_id")
    private DailyNoun dailyNoun;

}
