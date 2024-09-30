package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa18domain.Member18Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain20 {

    public static void main(String[] args) {
        // JPA 는 다양한 쿼리 방법을 지원
        // JPQL
        // JPA Criteria
        // Query DSL
        // 네이티브 SQL : 쌩쿼리
        // JDBC API 직접 사용, Mybatis, SpringJdbcTemplate 사용

        // JPQL 소개
        // 가장 단순한 조회 방법
        // EntityManager.find()
        // 객체 그래프 탐색 (a.getB().getC())
        // 나이가 18살 이상인 회원을 모두 검색하고 싶다면?

        // JPA를 사용하면 엔티티 객체를 중심으로 개발
        // 문제는 검색 쿼리
        // 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색

        // 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
        // (데이터를 다 메모리에 올려서 사용한다는건 불가능)
        // 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요

        // JPA는 SQL을 추상화한 JPQL 이라는 객체 지향 쿼리 언어 제공
        // SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
        // ANSI 표준이 지원하는 문법은 전부 지원

        // JPQL은 엔티티 객체를 대상으로 쿼리
        // SQL은 데이터베이스 테이블을 대상으로 쿼리

        // 검색

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 엔티티를 대상으로 쿼리, from 절 보면 Member18Domain 이다
            // 그리고 다른 차이는 select 다음에 *이나 m.username 이런 식으로 쓰는데
            // alias m만 씀 (m 전체를 조회해와라 라는 의미)
            List<Member18Domain> result = em.createQuery("select m from Member18Domain m where m.username like '%kim'", Member18Domain.class).getResultList();

            for (Member18Domain member18Domain : result) {
                System.out.println(member18Domain);
            }

            // SQL 을 추상화해서 특정 데이터베이스 SQL에 의존 X
            // JPQL 을 한마디로 정의하면 객체 지향 SQL


            // Criteria 크라이테리아 (자바 표준 스펙에 있음)
            // JPQL 은 사실 단순한 스트링이다 그렇다보니 동적 쿼리 생성이 어려움
            // String sqlString = "select m from Member18Domain m"
            // 복잡한 분기 조건에 따라 where 절이 다르다고 가정
            // String where = "where m.username like '%kim'"
            // sqlString += where;
            // 이런 식으로 처리하는데 String 이라 띄워쓰기 등도 신경써야됨
            
            // 예제
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member18Domain> query = cb.createQuery(Member18Domain.class);
            Root<Member18Domain> m = query.from(Member18Domain.class);
            CriteriaQuery<Member18Domain> cg = query.select(m).where(cb.equal(m.get("username"), "kim"));
            em.createQuery(cg).getResultList();

            // 간단한 예제이지만 복잡한 쿼리에 사용 하면 혼란스러움
            // 장점 : 컴파일 시 오류 확인 가능하고 동적 쿼리 짜기 편하다
            // CriteriaQuery<Member18Domain> cg = query.select(m);
            // if(조건) cg = cg.where(cb.equal(m.get("username"), "kim"))
            // JPQL 빌더 역할
            // 안 쓰는게 편하다 너무 복잡하고 유지 보수가 어렵다
            // Criteria 대신에 QueryDSL 실무 사용 권장

            // List<Member> result = queryFactory
//                     .select(m)
//                     .from(m)
//                     .where(m.name.like("kim"))
//                     .orderBy(m.id.desc())
//                     .fetch();

            // JPQL 을 잘 알아두면 QueryDSL 은 금방 익힌다

            // 네이티브 SQL
            // JPA가 제공하는 SQL을 직접 사용하는 기능
            // JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
            // 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트
            // 사용 예제
            String sql = "select member_id, username, city, street, zipCode from Member18Domain";
            List resultList = em.createNativeQuery(sql).getResultList();
            
            // JDBC 직접 사용, (JDBC템플릿, 마바)
            // 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
            // 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시
            // member를 생성해서 persist 하고
            // 네이티브 쿼리로 조회하면? commit 전이라 조회가 안됨 강제로 플러시 해주고 조회해야 조회 가능
            // *실제로 createNativeQuery 는 쿼리 나가기전에 flush가 먼저 나가서 따로 안 해줘도 된다

            // 디비커넥션을 직접 가져와서
            // 이런 식으로 dbconn.executeQuery("select * from member);
            // 이런 상황에서 위와 같은 문제 발생

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            emf.close();
        }
        em.close();


    }

}
