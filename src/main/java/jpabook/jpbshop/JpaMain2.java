package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member2;
import jpabook.jpbshop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

            Member2 member2 = new Member2();
            member2.setUsername("멤버6");
            member2.setTeam(team);
            em.persist(member2);


            em.flush();
             em.clear();

            Member2 findMember = em.find(Member2.class, member.getId());
            Team findTeam = findMember.getTeam();
            
            System.out.println("find Team : " + findTeam.getName());

            // 여기서 조회한 팀 정보는 영속성 컨텍스트에서 가져온 정보이다

            // 이 때 쿼리를 미리 날리고 싶으면?
            // 멤버 em.persist 다음에
//             em.flush();
//             em.clear();
            // 를 이용하여 영속성 컨텍스트의 상태를 데이터 베이스에 반영하고 초기화 후
            // 조회하면 DB에서 읽어옴

            // 수정할 때는 setTeam만 해주면 됨

            // 양방향 연관관계와 연관관계 주인
            // member => team은 조회가 가능
            // 근데 team에서 member는? 안됨
            // 사실 근데 둘은 왔다갔다 할 수 있음 ref만 있으면
            // 이거를 양방향 매핑이라고 함

            // 테이블 연관 관계에서는
            // TEAM ID를 멤버가 FK로 가지고 있으니
            // 이거를 조회하면 됨 사실상 테이블에서는 FK로 양방향 연관 관계가 됨
            // 그래서 이런 개념 자체가 없음

            // 문제는 객체인 데, 팀에는 멤버를 가지고 있지 않음
            // 그래서 팀에 List members 라는걸 가져야됨

            // 이게 객체 참조와 테이블 외래키의 가장 큰 차이이다
            // 실제 적용은 Team 객체 members 추가 부분
            List<Member2> members = findMember.getTeam().getMembers();
            System.out.println(members.size());
            for (Member2 m : members) {
                System.out.println("m = " + m.getUsername());
            }

            // 이러면 이제 멤버에서 팀으로, 팀에서 멤버가 조회가 가능한데
            // 이게 바로 양방향 매핑이다

            // 이게 반드시 좋냐? 이러면 신경 쓸게 많아서 단방향이 기본적으로는 좋다
            // mapped by Team 객체 참고

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
