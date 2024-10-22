package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain28 {

    public static void main(String[] args) {

        // JPQL 기본 함수
        // CONCAT, SUBSTRING, TRIM, LOWER, UPPER, LENGTH, LOCATE, ABS, SQRT, MOD, SIZE, INDEX (JPA 용도)

        // 사용자 정의 함수 호출
        // 하이버네이트는 사용자 방언에 추가해야 한다
        // 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다
        // select function('group_concat', i.name) from Item i

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member19 member19 = new Member19();
        member19.setAge(67);
        member19.setUsername("홍길동입니다");
        em.persist(member19);

        Member19 member_ = new Member19();
        member_.setAge(67);
        member_.setUsername("박길동입니다");
        em.persist(member_);

        em.flush();
        em.clear();

        // concat = || : || <-- 하이버네이트에서 지원
        String query = "select concat('a','b') from Member19 m";
        String query2 = "select 'a' || 'b' from Member19 m";
        
        // substring : 문자열 자르기
        String query3 = "select substring(m.username, 2, 4) from Member19 m";

        // 좌우 공백 제거
        // LOWER, UPPER : LOSWER 소문자로 UPPER 대문자로
        // LENGTH : 길이
        // LOCATE : 'de' 를 포함하는 위치 index 반환 (Integer 리턴함) 여기서는 4를 반환
        // String query4 = "select locate('de', 'abcdefg') from Member19 m";

        // ABS : 절대값
        // SQRT : 제곱근 계산 SQRT(9) => 3
        // MOD : m을 n으로 나눈 나머지 계산한다  MOD(m,n)  MOD(10,4) → 2
        // SIZE : select size(t.member) from Team t 컬렉션에 대한 사이즈 반환 해줌
        // INDEX : 일반적으로 컬렉션 리스트의 위치를 찾을때 사용하지만 안 쓰는게 좋다

        List<String> result = em.createQuery(query3, String.class).getResultList();
        for (String s : result) {
            System.out.println(s);
        }
        
        // 사용자 정의 함수
        // group_concat 이미 h2 에 있지만 이런 식으로 쓰면 된다는 예제임
        // Dialect 를 상속받는 MyH2Dialect 클래스를 만들고
        // registerFunction 으로 함수 등록 및
        // persistence.xml 파일 Dialect 를 새로 만든걸 참조하도록 수정
        // + 하이버네이트를 쓰면 select group_concat() 이렇게 써도됨 function 은 표준
        String query5 = "select function('group_concat', m.username) from Member19 m";
        List<String> result2 = em.createQuery(query5, String.class).getResultList();
        for (String s : result2) {
            // 두명이 그룹화되어 한 줄로 쭉 나온다
            System.out.println("s = " + s);
        }

    }
}
