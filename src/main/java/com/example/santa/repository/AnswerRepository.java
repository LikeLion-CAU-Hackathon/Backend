package com.example.santa.repository;

import com.example.santa.domain.Answer;
import com.example.santa.domain.AppUser;
import com.example.santa.domain.Question;
import com.example.santa.dto.response.AnswerListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByQuestionAndAppUser(Question question, AppUser user);

    @Query("SELECT a.question.id FROM Answer a WHERE a.appUser = :user")
    List<Long> findAnsweredQuestionIdsByAppUser(@Param("user") AppUser appUser);

    List<Answer> findByQuestionId(long questionId);

    @Query("""
    select new com.example.santa.dto.response.AnswerListDto(
        a.id,
        a.contents,
        u.name,
        a.createdTime,
        (select count(t) from Thumbs t where t.answer.id = a.id),
        (select count(r) from Reply  r where r.answer.id  = a.id)
    )
    from Answer a
    join a.appUser u
    where a.question.id = :questionId
    order by a.createdTime desc
    """)
    List<AnswerListDto> findAnswersWithCountsByQuestionId(@Param("questionId") Long questionId);
}
