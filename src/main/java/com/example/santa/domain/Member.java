package com.example.santa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String name;
    @Column(unique = true, length = 50)
    private String email;

    @Builder
    public Member(String name, String email){
        this.name = name;
        this.email = email;
    }
}
