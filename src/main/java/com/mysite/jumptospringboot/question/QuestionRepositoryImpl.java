package com.mysite.jumptospringboot.question;

import com.mysite.jumptospringboot.answer.QAnswer;
import com.mysite.jumptospringboot.user.QSiteUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.mysite.jumptospringboot.answer.QAnswer.answer;
import static com.mysite.jumptospringboot.question.QQuestion.*;
import static com.mysite.jumptospringboot.user.QSiteUser.siteUser;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    /*
    * ("select "
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
            *
    * */
    @Override
    public Page<Question> findAllByKeywordQDSL(String kw, Pageable pageable) {
        QQuestion q = new QQuestion("q");
        QSiteUser u1 = new QSiteUser("u1");
        QSiteUser u2 = new QSiteUser("u2");
        QAnswer a = new QAnswer("a");

        // select distinct join where
        JPAQuery<Question> query = queryFactory
                .selectFrom(q)
                .distinct()
                .leftJoin(q.author, u1)
                .leftJoin(a).on(a.question.eq(q))
                .leftJoin(a.author, u2)
                .where(
                        q.subject.contains(kw)
                        .or(q.content.contains(kw))
                        .or(u1.username.contains(kw))
                        .or(a.content.contains(kw))
                        .or(u2.username.contains(kw))
                );

        // OrderBy(sort)
        if(!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Question> path = new PathBuilder<>(q.getType(), q.getMetadata());
                query.orderBy(
                        new OrderSpecifier<>(
                                order.isAscending() ? Order.ASC : Order.DESC,
                                path.get(order.getProperty(), Comparable.class) // Expression에 Comparable타입 추가
                        )
                );
            }
        }

        // 페이징 처리
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Question> result = query.fetch();

        // Count쿼리 최적화(정렬 삭제)
        Long total = queryFactory
                .select(q.countDistinct())
                .from(q)
                .leftJoin(q.author, u1)
                .leftJoin(a).on(a.question.eq(q))
                .leftJoin(a.author, u2)
                .where(
                        q.subject.contains(kw)
                        .or(q.content.contains(kw))
                        .or(u1.username.contains(kw))
                        .or(a.content.contains(kw))
                        .or(u2.username.contains(kw))
                )
                .fetchOne();
        if(total==null) {
            total = 0L;
        }
//        return PageableExecutionUtils.getPage(con)
        return new PageImpl<>(result, pageable, total);
    }
}
