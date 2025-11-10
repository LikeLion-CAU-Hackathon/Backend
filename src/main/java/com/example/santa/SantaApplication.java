package com.example.Santa;

import com.example.Santa.domain.DailyAdj;
import com.example.Santa.domain.DailyNoun;
import com.example.Santa.repository.DailyAdjRepository;
import com.example.Santa.repository.DailyNounRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling; // NickName 자동 업뎃

import java.util.*;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantaApplication.class, args);
	}
	/** 애플리케이션 시작 시 초기 데이터를 "하드코딩" (적재)하는 메서드
	 * @param adjRepository  형용사 리포지토리
	 * @param nounRepository 명사 리포지토리
	 * @return CommandLineRunner 실행 로직
	 */
	@Bean
	public CommandLineRunner initData(DailyAdjRepository adjRepository, DailyNounRepository nounRepository) {
		return args -> {

			if (adjRepository.count() == 0) {
				System.out.println("===== 형용사 초기 데이터 25개 삽입 시작 =====");
				List<String> adjectives = Arrays.asList(
						"잘생긴", "예쁜", "행복한", "건강한", "병든", "바보","무례한","분노한", "못된", "시끄러운", "우아한"
				);

				adjectives.forEach(adjStr -> {
					DailyAdj adjEntity = new DailyAdj();
					adjEntity.setAdjective(adjStr);
					adjRepository.save(adjEntity);
				});
				System.out.println("===== 형용사 초기 데이터 삽입 완료 =====");
			}

			if (nounRepository.count() == 0) {
				System.out.println("===== 명사 초기 데이터 25개 삽입 시작 =====");
				List<String> nouns = Arrays.asList(
						"산타", "루돌프", "눈사람", "요정", "붕어빵", "개발자", "디자이너","기획자","대학생", "독재자","으른사자"
				);

				nouns.forEach(nounStr -> {
					DailyNoun nounEntity = new DailyNoun();
					nounEntity.setNoun(nounStr);
					nounRepository.save(nounEntity);
				});
				System.out.println("===== 명사 초기 데이터 삽입 완료 =====");
			}
		};
	}
}
