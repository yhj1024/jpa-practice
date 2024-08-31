package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member2;
import jpabook.jpbshop.domain.Team;
import jpg_basic.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain3 {
    public static void main(String[] args) {

        // 양방향 매핑 시 가장 많이 하는 실수
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Member2 member2 = new Member2();
//            member2.setUsername("member1");
//            em.persist(member2);
//
//            Team team = new Team();
//            team.setName("teamA");
//            team.getMembers().add(member2);
//            em.persist(team);

//            em.flush();
//            em.clear();

            // 실행해보면 멤버에 팀이 저장이 되지 않음
            // 왜냐면 team 의 members는 주인이 아니기 때문
            // mappedBy는 읽기 전용일 뿐, 따라서 아래 처럼 연관관계 주인에 설정을 해주어야 한다

            Team team = new Team();
            team.setName("teamA");
//            team.getMembers().add(member2);
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();


            // 이것도 근데 진짜 객체지향 관점으로 생각을 해보면 둘다 넣어주어야 한다
            // 아래처럼 하면 일단 출력은 된다
            // 쿼리를 보면 팀을 한번 조회하고 members 를 한번 조회하는 형태로 두번 조회

            // 근데 team.getMembers().add(member2); 이걸 안 넣어주면 뭔가 이상하다 객체지향스럽지 않다
            // 그리고 중간에 flush clear 를 해버리면 문제가 없는데
            // 저게 없다고 하면 이제 findTeam 은 1차 캐시를 통해 조회를 하게 될 것 이다
            // 이러면 값이 당연히 아래 member 가 안 나오게 된다 (1차 캐시에는 member가 없으니까)
            
            // 이런 케이스도 그렇고 객체지향 관점으로 봐도 양 쪽에 다 넣어주는게 맞다
            // 테스트 코드를 생각해봐도 그렇다
            
            // 그래서 이걸 추가할 때 단순히 team.getMembers().add(member2); 넣어도 되지만
            // 연관 관계 편의를 위해 member.setTeam 할 때 team 객체에 member 를 넣어주어 처리하면 편하다
            
            // 그리고 setter에 뭔가 처리하는것보다 changeTeam 라는 메서드를 따로 만들어서 사용하는게 좋다
            // Member 객체 참고

            // 아니면 team 에 addMember 를 만들어서 사용해도 됨, 이거는 정하기 나름
            // 그리고 양방향 매핑 시에 다음을 주의해야 한다
            // 예) toString(), lombok, JSON 생성 라이브러리
            // toString() : 그냥 쓰지 않는다
            // entity를 controller 에서 쓰지 않는다

            // 정리
            // 단방향 매핑만으로도 이미 연관관계 매핑은 완료
            // 처음부터 양방향 생각하지말고 일단 단방향으로 설계부터 해둔다
            // 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
            // JPQL 에서 역방향으로 탐색할 일이 많음
            // 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨 (테이블에 영향을 주지 않음)
            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members = findTeam.getMembers();

            for (Member2 member : members) {
                System.out.println("m = " + member.getUsername());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
