package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain27 {

    public static void main(String[] args) {
//        기본 Case 식
//                select
//        case when m.age <= 10 then '학생요금'
//        when m.age >= 60 then '경로요금'
//       else '일반요금'
//        end
//        from Member m
//
//        단순 Case 식
//                select
//        case t.name
//                when '팀A' then '인센티브110%'
//        when '팀B' then '인센티브120%'
//      else '인센티브105%'
//        end
//        from Team t
//
//        조건식 - CASE 식
//                - COALESCE (콜렉스) : 하나씩 조회해서 null이 아니면 반환
//                - NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
//
//        사용자 이름이 없으면 이름 없는 회원을 반환
//        select coalesce(m.username, '이름 없는 회원') from Member m
//
//        사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환
//        select NULLIF(m.username, '관리자') from Member m

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member19 member19 = new Member19();
        member19.setAge(67);
        member19.setUsername(null);
//        member19.setUsername("관리자");

        em.persist(member19);
        em.flush();
        em.clear();

        String query = "select case when m.age <= 10 then '학생요금' when m.age >= 60 then '노인요금' else '일반요금' end from Member19 m";
        List<String> result = em.createQuery(query, String.class).getResultList();
        for (String s : result) {
            System.out.println(s);
        }


        String query2 = "select coalesce(m.username, '이름 없는 유저') from Member19 m";
        List<String> result2 = em.createQuery(query2, String.class).getResultList();
        for (String s : result2) {
            System.out.println(s);
        }

        String query3 = "select nullif(m.username, '관리자') from Member19 m";
        List<String> result3 = em.createQuery(query3, String.class).getResultList();
        for (String s : result3) {
            System.out.println(s);
        }


    }
}
