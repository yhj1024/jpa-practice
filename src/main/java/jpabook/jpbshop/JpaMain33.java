package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa19domain.Member19;
import jpabook.jpbshop.domain.jpa19domain.Team19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain33 {

    // JPQL 에서 엔티티를 직접 사용하면 SQL에서 해당 에닡티의 기본 키 값을 사용
    // select count(m.id) from Member m // 엔티티의 아이디를 사용
    // select count(m) from Member m // 엔티티를 직접 사용

    // JPQL 에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용

    // 위 JPQL 은 둘다 아래와 같은 SQL로 변환된다
    // select count(m.id) from Member m

    // WHERE 절에서도 엔티티를 직접 사용할 수 있고, 이때도 엔티티의 기본 키 값을 사용한다
    // select m from Member m where m = :member // 엔티티를 직접 사용
    // select m from Member m where m.id = :memberId // 엔티티의 아이디를 사용

    // 둘다 아래의 sql 로 변환된다
    // select m from Member m where m.id = :memberId

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member19 member1 = new Member19();
        member1.setUsername("테스트");
        em.persist(member1);

        Member19 member2 = new Member19();
        member2.setUsername("테스트");
        em.persist(member2);

        Member19 member3 = new Member19();
        member3.setUsername("테스트");
        em.persist(member3);

        em.flush();
        em.clear();

        String jpql = "select m from Member19 m where m = :member";
        Member19 findMember = em.createQuery(jpql, Member19.class)
                .setParameter("member", member1)
                .getSingleResult();

        System.out.println("findMember = " + findMember);
        System.out.println("member id = " + findMember.getId());
        System.out.println("member name = " + findMember.getUsername());

        String jpql2 = "select m from Member19 m where m.id = :memberId";
        Member19 findMemberById = em.createQuery(jpql2, Member19.class)
                .setParameter("memberId", member1.getId())
                .getSingleResult();

        System.out.println("findMemberById = " + findMemberById);
        System.out.println("member id = " + findMemberById.getId());
        System.out.println("member name = " + findMemberById.getUsername());


        // 엔티티 직접 사용 - 외래 키 값
        // Team team = em.find(Team.class, 1L);
        // String qlString = "select m from Member m where m.team = :team"; // 멤버가 팀의 키 (외래키) 값을 가짐
        // List res = em.createQuery(qlString).setParameter("team", team).getResultList();

        Team19 team = new Team19();
        team.setName("팀A");
        em.persist(team);

        findMember.changeTeam(team);
        em.persist(findMember);

        em.flush();
        em.clear();

        String jpql33 = "select m from Member19 m where m.team = :team";
        Member19 findMemberByTeam = em.createQuery(jpql33, Member19.class)
                .setParameter("team", team)
                .getResultList()
                .get(0);

        System.out.println("findMemberByTeam = " + findMemberByTeam);
        System.out.println("member id = " + findMemberByTeam.getId());
        System.out.println("member name = " + findMemberByTeam.getUsername());




    }

}
