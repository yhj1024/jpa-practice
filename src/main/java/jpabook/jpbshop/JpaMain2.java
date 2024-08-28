package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;
import jpabook.jpbshop.domain.Member2;
import jpabook.jpbshop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Team team = new Team();
            team.setName("e팀");
            em.persist(team);


            Member2 member = new Member2();
            member.setUsername("멤버5");
            member.setTeam(team);
            em.persist(member);


            Member2 findMember = em.find(Member2.class, member.getId());
            Team findTeam = findMember.getTeam();
            
            System.out.println("find Team : " + findTeam.getName());

            // 여기서 조회한 팀 정보는 영속성 컨텍스트에서 가져온 정보이다

            // 이 때 쿼리를 미리 날리고 싶으면?
            // 멤버 em.persist 다음에
            // em.flush();
            // em.clear();
            // 를 이용하여 영속성 컨텍스트의 상태를 데이터 베이스에 반영하고 초기화 후
            // 조회하면 DB에서 읽어옴

            // 수정할 때는 setTeam만 해주면 됨
            //
            
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
