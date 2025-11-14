package com.example.santa;

import com.example.santa.domain.Question;
import com.example.santa.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class QuestionLoader implements CommandLineRunner {
    private final QuestionRepository questionRepository;

    public QuestionLoader(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (questionRepository.count() > 0) {
            return;
        }

        List<Question> questions = List.of(
                new Question(LocalDate.parse("2025-12-01"), """
                이번 12월,
                이루고 싶은 목표 한 가지!
                """),
                new Question(LocalDate.parse("2025-12-02"), """
                당신은 얼마나 오랫동안
                산타클로스를 믿었나요?
                """),
                new Question(LocalDate.parse("2025-12-03"), """
                겨울이 오면 꼭 듣는,
                여러분의 최애 캐럴을 공유해 주세요!
                """),
                new Question(LocalDate.parse("2025-12-04"), """
                멋사에서 가장
                '꼰대'인 사람은 누구인가요?
                """),
                new Question(LocalDate.parse("2025-12-05"), """
                        새로 사귄 동료들 중,
                        특히 인상 깊었던 사람이 있나요?
                        """),
                new Question(LocalDate.parse("2025-12-06"), """
                        함께 프로젝트를 진행한 팀원 중,
                        호흡이 가장 잘 맞았던 사자는?
                        """),
                new Question(LocalDate.parse("2025-12-07"), """
                        첫인상과 지금의 이미지가
                        다른 사자가 있나요?
                        """),
                new Question(LocalDate.parse("2025-12-08"), """
                        멋사에서 가장 멋쟁이인 사자를
                        한 명만 고르자면?
                        """),
                new Question(LocalDate.parse("2025-12-09"), """
                        멋사에서 배운 것 중
                        가장 유용했던 것은 무엇인가요?
                        """),
                new Question(LocalDate.parse("2025-12-10"), """
                        올해 가장 고마웠던 사자에게
                        한마디 남겨주세요
                        """),
                new Question(LocalDate.parse("2025-12-11"), """
                        평생 윈도우(Windows)만 쓰기
                        vs 평색 맥(Mac)만 쓰기
                        """),
                new Question(LocalDate.parse("2025-12-12"), """
                        익명을 빌려, 평소 말하지 못했던
                        이야기를 적어보세요.
                        """),
                new Question(LocalDate.parse("2025-12-13"), """
                        올해 2월로 돌아간다면
                        멋사 지원한다 vs 안한다
                        """),
                new Question(LocalDate.parse("2025-12-14"), """
                        기·디·프·백 중, 프로젝트의
                        메인이라고 생각하는 파트는?
                        """),
                new Question(LocalDate.parse("2025-12-15"), """
                        실력 1등 + 잠수 타는 팀원
                        vs 실력 부족 + 성실 1등 팀원
                        """),
                new Question(LocalDate.parse("2025-12-16"), """
                        가장 바람둥이일 것 같은 사자는
                        누구인가요?
                        """),
                new Question(LocalDate.parse("2025-12-17"), """
                        멋사가 아니었다면 절대
                        못해봤을 것 같은 경험이 있나요?
                        """),
                new Question(LocalDate.parse("2025-12-18"), """
                        올해 가장 크게 성장했다고
                        느끼는 부분은 무엇인가요?
                        """),
                new Question(LocalDate.parse("2025-12-19"), """
                        친해지고 싶었지만 기회가 없어
                        아쉬웠던 사자가 있나요?
                        """),
                new Question(LocalDate.parse("2025-12-20"), """
                        10년 뒤, 우리 기수에서
                        가장 크게 성공할 것 같은 사자는?
                        """),
                new Question(LocalDate.parse("2025-12-21"), """
                        새해 첫날, 가장 먼저
                        듣고 싶은 노래는 무엇인가요?
                        """),
                new Question(LocalDate.parse("2025-12-22"), """
                        남들이 보면 놀랄 만한,
                        나만의 극단적인 작업 스타일이 있나요?
                        """),
                new Question(LocalDate.parse("2025-12-23"), """
                        내일 크리스마스 이브, 계획은?
                        (솔직히 약속 있다 vs 없다)
                        """),
                new Question(LocalDate.parse("2025-12-24"), """
                        2025년을 마무리하며
                        나 자신에게 해주고 싶은 말은?
                        """)
        );

        questionRepository.saveAll(questions);

        System.out.println("===== 질문 데이터 삽인 완료 =====");
    }
}
