package jpabook.jpbshop;

import jpabook.jpbshop.domain.Team;
import jpabook.jpbshop.domain.jpa19domain.Member19;
import jpabook.jpbshop.domain.jpa19domain.Team19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain31 {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team19 team = new Team19();
        team.setName("팀A");
        em.persist(team);

        Member19 member19 = new Member19();
        member19.setUsername("테스트");
        member19.changeTeam(team);
        em.persist(member19);

//        Member19 member20 = new Member19();
//        member20.setUsername("테스트");
//        member20.changeTeam(team);
//        em.persist(member20);


        // 페치 조인 대상에는 별칭을 줄 수 없다
        // 하이버네이트는 가능하지만 가급적 사용 X
        // 예를 들어 아래처럼 as 주고 where m.username 이런식으로 쓰면 안됨
        // 이게 관례임 일부만 조회하면 객체 그래프의 정합성이 깨질 수 있음
        // JPA 에서 설계한 fetch join 의 목적은 전체를 다 가져오기 위함임
        String query = "select t From Team19 t join fetch t.members as m";
//        String query = "select distinct t From Team19 t join fetch t.members as m";

        List<Team19> resultList = em.createQuery(query, Team19.class)
//                .setFirstResult(0)
//                .setMaxResults(1) // 페이징 테스트, 경고 로그를 남기면서 메모리에서 페이징을 시도함 (매우 위험)
                .getResultList();
        for (Team19 team19 : resultList) {
            System.out.println("Team name : " + team19.getName() + " Member Count : " + team19.getMembers().size());
            for (Member19 m19 : team19.getMembers()) {
                System.out.println("Member : " + m19.getId() + " " + m19.getUsername());
            }
        }

        // 근데 가끔 유용할 때가 있는데 다른 엔티티 join fetch 추가 진행하거나할때 사용되는 경우 종종 있음
        //  String query = "select t From Team19 t join fetch t.members as m join fetch m.다른거 join fetch .....";
        // 이럴때 말고는 정합성 문제로 안씀 JPA join fetch 와 사상이 안 맞음

        // 둘 이상의 컬렉션은 페치 조인 하면 안됨. (어떻게 가능은 한데 안 하는게 좋음 정합성이 깨짐 일대다도 데이터 뻥튀기가 생기는데 일대다에서 더 심함)
        // 컬렉션을 페치 조인하면 페이징 API (setFirstResult, setMaxResults)를 사용할 수 없다
        // 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능 (데이터 뻥튀기가 안됨)
        // 하이버네이트는 경고 로그를 남기면서 메모리에서 페이징을 시도함 (매우 위험)

        // 해결방법은 여러개가 있는데 지금 같은 경우에는
        // 다대일로 관계를 뒤집어서
        // String query = "select m From Member19 m join fetch m.team t";
        // 이런 식으로 조회하는 방법이 있음

        // 다른 방법은 일단 team만 조회를 한다 그리고 실행하면 member가 사용될때 재조회가 된다 단 이러면 성능이 안나옴
        // (만약 팀이 10개라면 10번의 쿼리가 나감)

        // 그래서 이럴때 사용할수 있는게 @BatchSize
        // Team 클래스의 members에 @BatchSize(size = 100) 이런식으로 걸어주면
        // 멤버를 lazy loading으로 조회할 때 내꺼 뿐만 아니라 다른 team객체의 id로도 member를 조회하는데 in 으로 100개씩 가져옴

        // 이거를 또 global 설정으로 가져갈 수 있음 (보통 실무에서)
        // 퍼시스턴스 xml에 hibernate.default_batch_fetch_size=1000

        // 실무에서는 기본적으로 모두 lazy loading 으로 설정하고
        // 필요한 곳만 fetch join으로 최적화 하는게 좋음
        
        // 페치 조인은 객체 그래프를 유지할때 사용하면 효과적이고
        // 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌
        // 다른 형태로 조회할때는 DTO로 반환하는것이 효과적

        // 페치 조인 상당히 중요함 100% 이해 반드시 필요
        // 선언적으로 튜닝 가능

    }
}
