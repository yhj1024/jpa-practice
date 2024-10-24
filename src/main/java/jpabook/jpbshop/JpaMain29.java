package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain29 {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        Member19 member19 = new Member19();
//        member19.setAge(67);
//        member19.setUsername("홍길동입니다");
//        em.persist(member19);
//
//        Member19 member_ = new Member19();
//        member_.setAge(67);
//        member_.setUsername("박길동입니다");
//        em.persist(member_);

        // 경로 표현식
        // 점을 찍어 객체 그래프를 탐색하는 것

        // select m.username -> 상태필드
        // from Member m
        // join m.team t -> 단일 값 연관 필드 (many to one)
        // join m.orders o -> 컬렉션 값 연관 필드 (one to many)
        // where t.name = '팀A'

        // 경로 표현식 용어 정리
        // 상태 필드 : 단순히 값을 저장하기 위한 필드 (ex m.username)
        // 연관 필드 : 연관 관계를 위한 필드
        //  단일 값 연관 필드 : @ManyToOne, OneToOne : 대상이 엔티티 (ex: m.team)
        //  컬렉션 값 연관 필드 : @OneToMany, @ManyToMany : 대상이 컬렉션 (ex: m.orders)

        // 경로 표현식 특징
        //  상태 필드 : 경로 탐색의 끝, 탐색 X
        //  단일 값 연관 경로 : 묵시적 내부 조인 (inner join) 발생, 탐색 O
        //  컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색 X,
        //   (FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능)

        // String query = "select m.team From Member m"
        // 실제 쿼리 나갈때 Team을 조인하게 됨
        // 즉 조심해야한다 성능적인 이슈

        // String query = "select t.members from Team t"
        // 컬렉션의 경우 t.members.어쩌고 이런 식으로 탐색이 안됨
        // size 정도 사용할 수 있음

        // 근데 username을 뽑고 싶다?
        // 이러면 명시적 조인을 이용해야함
        // String query = "select m.username from Team t join t.members m"

        // 경로 표현식 예제
        // select o.member.team from Order o : 성공
        // select t.members from Team : 성공
        // select t.members.username from Team : 실패
        // select m.username from Team t join t.members m : 성공

        // 경로 탐색을 사용한 묵시적 조인 시 주의 사항
        // 항상 내부 조인
        // 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
        // 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해
        // SQL 의 FROM (JOIN) 절에 영향을 줌

        // 실무 : 가급적 묵시적 조인 대신에 명시적 조인 사용
        // 조인은 SQL 튜닝에 중요 포인트 이므로
        // 묵시적 조인은 조인이 일어나는 상황을 한 눈에 파악하기 어려움

        em.flush();
        em.clear();

    }
}
