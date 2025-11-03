package com.example.Santa.domain;

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

    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(length = 30)
    private String nickname;
    @Column(nullable = false)
    private String password;
    //나중에 다른 엔티티와 연결해야됨

    @Builder
    public Member(String name, String email, String nickname, String password){
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
