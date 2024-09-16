package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member2;
import jpabook.jpbshop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain12 {

    public static void main(String[] args) {

        // 즉시로딩과 지연로딩
        // Member가 Team 을 가지고 있을때
        // 비즈니스적으로 Team을 자주 조회하지 않으면
        // 조회할 필요가 없음

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team team = new Team();
            team.setName("TEST TEAM");
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("테스트");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            Member2 findMember = em.find(Member2.class, member2.getId()); // 멤버만 딱 조회함
            System.out.println("findMember :" + findMember.getTeam().getClass()); // 지연 로딩이면 프록시 객체가 조회됨
            findMember.getTeam().getName(); // team에 있는 무언가를 실제 접근할 떄 초기화(db) 쿼리가 나가게 된다

            // 반대로 비즈니스 로직상 멤버와 팀을 거의 같이 쓴다면?
            // 성능상 EAGER (기본)을 사용하는게 좋다

            // EAGER 라고 할 때
            // 조인해서 한방퀴리 vs 두번조회 방법이 있는데
            // 가능하면 조인을 사용해서 SQL 한번에 함께 조회

            // 실무에서는 가급적 지연 로딩만 사용
            // 즉시 로딩을 적용하면 예상하지 못한 SQL 이 발생
            // 즉시 로딩은 JPQL 에서 N+1 문제를 일으킨다
            // -> 예 em.createQuery("select m from Member m", Member.class).gerResultList();
            // 이러면 일단 멤버를 다 가져온다
            // 그리고 team에 즉시로딩이 걸려있으면 또 따로따로 team을 다 가져오는 쿼리를 날린다
            // 이거를 해결하기 위에 실무에서는 보통 Lazy 로 다 깔아놓고
            // JPQL로 Fetch join 을 이용해 가져온다
            // -> 예 em.createQuery("select m from Member m join fetch m.team", Member.class).gerResultList();
            // 이러면 한방쿼리로 조인해서 다 가져옴

            // @ManyToOne, @OneToMany 은 기본이 즉시 로딩 -> LAZY 로 설정
            // @OneToMany, @ManyToMany는 기본이 지연 로딩

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
