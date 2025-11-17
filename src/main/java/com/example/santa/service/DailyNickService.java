//package com.example.santa.service;
//
//import com.example.santa.domain.AppUser;
//import com.example.santa.domain.DailyAdj;
//import com.example.santa.domain.DailyNick;
//import com.example.santa.domain.DailyNoun;
//import com.example.santa.repository.AppUserRepository;
//import com.example.santa.repository.DailyAdjRepository;
//import com.example.santa.repository.DailyNickRepository;
//import com.example.santa.repository.DailyNounRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor // Lombok 의존성
//public class DailyNickService {
//    private final DailyNickRepository dailynickRepository;
//    private final AppUserRepository appUserRepository;
//    private final DailyAdjRepository dailyAdjRepository;
//    private final DailyNounRepository dailyNounRepository;
//
//    private final Random random = new Random();
//
//    @Scheduled(cron= "0 0 0 * * *") // 자동 닉네임 업데이트
//    @Transactional
//    public void saveDailynick() {
//        System.out.println("save dailynick");
//
//        List<DailyAdj> adjectives = dailyAdjRepository.findAll();
//        List<DailyNoun> nouns = dailyNounRepository.findAll();
//
//        if (adjectives.isEmpty() || nouns.isEmpty()) {
//            System.out.println("no adjectives and no nouns");
//            return;
//        }
//        List<AppUser> allUsers = appUserRepository.findAll();
//        for(AppUser appUser : allUsers) {
//            DailyAdj randomAdj = adjectives.get(random.nextInt(adjectives.size()));
//            DailyNoun randomNoun = nouns.get(random.nextInt(nouns.size()));
//
//            DailyNick dailyNick = dailynickRepository.findByAppUserId(appUser.getId()).orElse(new DailyNick());
//
//            // 새로운 닉네임 정보 설정
//            dailyNick.setAppUser(appUser);
//            dailyNick.setDailyAdj(randomAdj);
//            dailyNick.setDailyNoun(randomNoun);
//
//            dailynickRepository.save(dailyNick); //업뎃
//
//            String newNickname = randomAdj.getAdjective()+" "+randomNoun.getNoun();
//
//            appUser.setNickname(newNickname);
//
//        }
//        System.out.println("총 " + allUsers.size() + "명 업뎃 완");
//    }
//}
