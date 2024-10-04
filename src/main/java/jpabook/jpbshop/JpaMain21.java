package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa18domain.Member18Domain;
import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain21 {

    public static void main(String[] args) {

        // JPQL : Java Persistence Query Language
        // 기본 문법과 기능
        // JPQL 은 객체지향 쿼리 언어이다 따라서 테이블을 대상으로 쿼리하는것이 아니라 엔티티를 대상으로 쿼리
        // JPQL 은 SQL 을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다
        // JPQL 은 결국 SQL 로 변환된다

        // select m from Member as m where m.age > 18
        // 엔티티와 속성은 대소문자 구분 O (Member, age)
        // JPQL 키워드는 대소문자 구분 X (Select, From)
        // 엔티티 이름 사용, 테이블 이름이 아님 (Member)
        // 엔티티 이름 = @Entity(name= "엔티티이름") 인데,
        // 생략하면 기본이 Class 이름이다 (보통 생략함)

        // 집합과 정렬
        // select
        //  COUNT(m), // 회원수
        //  SUM(m.age) // 나이 합
        //  AVG(m.age) // 평균 나이
        //  MAX(m.age) // 최대 나이
        //  MIN(m.age) // 최소 나이
        // from Member m
        // GROUP BY, HAVING, ORDER BY 다 똑같이 사용 가능

        // TypeQuery, Query
        // TypeQuery : 반환 타입이 명확할 때 사용
        // Query : 반환 타입이 명확하지 않을 때

        // TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class) // 타입으로 올 쿼리 명시
        // TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class) // 이런 경우는 String으로 받으면됨
        // Query query3 = em.createQuery("select m.username, m.age from Member m") // 이런 경우는 타입을 명기하기가 애매함 이 때 Query 로 받음

        // 결과 조회 API
        // query.getResultList() : List 컬렉션 반환
        // 결과가 없으면 빈 리스트 반환 NPE 걱정 X
        // query.getSingleResult() : 단건 반환, 결과가 정확하게 하나가 나와야 한다
        // SingleResult 반환이 없는 경우 : NoResultException 반환
        // SingleResult 여러개 반환 되면 : NonUniqueResultException 반환

        // 여러 개면 오류일수도 있다고 봐야되는데
        // 없는 거는 굳이 오류로 봐야되나 싶어서 논란이 많긴함
        // Sptring Data JPA는 Null 또는 Optinal 로 받을 수 있게 되어있음
        // 근데 이것도 내부를 까보면 자체적으로 저거를 호출하는데 try catch 처리하도록 되어있음
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member19 member19 = new Member19();
            member19.setUsername("member21");
            em.persist(member19);

//            TypedQuery<Member19> selectMFromMember19M = em.createQuery("select m from Member19 m", Member19.class);
//            List<Member19> result = selectMFromMember19M.getResultList();
//            for (Member19 member191 : result) {
//                System.out.println(member191.getUsername());
//            }

            TypedQuery<Member19> selectMFromMember19M2 = em.createQuery("select m from Member19 m where m.username=:username", Member19.class);
            selectMFromMember19M2.setParameter("username", "member21");
            Member19 result2 = selectMFromMember19M2.getSingleResult();
            System.out.println(result2.getUsername());

            // 근데 보통 아래처럼 체이닝으로 만들어서 쓴다
            Member19 channingMember = em.createQuery("select m from Member19 m where m.username=:username", Member19.class)
                    .setParameter("username", "member21")
                    .getSingleResult();
            System.out.println(channingMember.getUsername());

            // 위치기반 파라미터도 제공은 해주지만 안 쓰는게 낫다
//            "select m from Member19 m where m.username=?1"
//             query.setParameter(1, usernameParam);

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
