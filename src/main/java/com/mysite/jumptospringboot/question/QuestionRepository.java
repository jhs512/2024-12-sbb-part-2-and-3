package com.mysite.jumptospringboot.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer>, QuestionRepositoryCustom {
    Question findBySubject(String subject);
    Optional<Question> findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); // JPA에 더 가까운 방법은 containing
    List<Question> findBySubjectContaining(String subject); // 이게 더 JPA스타일이다. 자동으로 SQL 쿼리를 최적화 하므로 실수를 줄일 수 있따.
    Page<Question> findAll(Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeywordJPQL(@Param("kw") String kw, Pageable pageable);
}

/*
* question에 siteUser
* a1.question)id, id, author_id content , create
* answer을 계속 찾음 where question_id=?으로
* 아마 left outer join Answer a on a.question=q 이부분인 것 같은데
* q에 따라서 하나씩 실행됨
 * */