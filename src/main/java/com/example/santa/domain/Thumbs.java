package com.example.santa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "thumbs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"answer_id", "user_id"})
)
public class Thumbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbs_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
}