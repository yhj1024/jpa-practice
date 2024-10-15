package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;
import jpabook.jpbshop.domain.Team;
import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain24 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 조인

        // 내부 조인
        // SELECT m FROM Member m [INNER] JOIN m.team t

        // 외부 조인
        // SELECT m From Member m LEFT [OUTER] JOIN m.team t

        // 세타 조인 (cross join) 카티션 곱을 수행해서 전체 다 불러와서 필터링 하는 방식
        // 5개 row, 5개 row가 있으면 25개를 다 만들어 낸 후 필터링 함
        // select count(m) from Member m, Team t where m.username = t.name

        try{

            Team team = new Team();
            team.setName("팀A");
            em.persist(team);

            Member19 member = new Member19();
            member.setUsername("홍길동");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // inner join
            String innerQuery = "select m from Member19 m inner join m.team t where t.name LIKE concat('%',:teamName ,'%')";
            // 쿼리 나갈 때 조인 쿼리 바로 밑에 select 쿼리가 하나 더 돈다
            // fetch 를 Lazy 로 잡아야 지연 로딩
            List<Member19> resultList1 = em.createQuery(innerQuery, Member19.class)
                    .setParameter("teamName", "팀")
                    .getResultList();
            for (Member19 member19 : resultList1) {
                System.out.println(member19.getUsername());
            }

            // outer join
            String outQuery = "select m from Member19 m left join m.team";
            List<Member19> resultList2 = em.createQuery(outQuery, Member19.class)
                    .getResultList();
            for (Member19 member19 : resultList2) {
                System.out.println(member19.getUsername());
            }

            // 세타 조인
            // username = 팀A
            // t.name = 팀A 인경우 두개는 같은 거임
            String crossQuery = "select m from Member19 m, Team t where m.username = t.name";
            List<Member19> resultList3 = em.createQuery(crossQuery, Member19.class)
                    .getResultList();
            for (Member19 member19 : resultList3) {
                System.out.println(member19.getUsername());
            }

            // 조인 - ON절

            // ON 절을 활용한 조인 (JPA 2.1 부터 지원)
            // 1. 조인 대상 필터링
            // 예) 회원과 팀을 조인하면서, 팀 이름이 a인 팀만 조인
            // JPQL)select m,t From Member19 m lef join m.team t on t.name ='A'
            // SQL)select m.*, t.* from Member m left join team t on m.team_id = t.id and t.name = 'A'
            String onFilterQuery = "select m from Member19 m left join m.team t on t.name = 'TEST'";
            List<Member19> resultList4 = em.createQuery(onFilterQuery, Member19.class).getResultList();
            for (Member19 member19 : resultList4) {
                System.out.println(member19.getUsername());
            }

            // 2. 연관관계 없는 엔티티 외부 조인 (하이버네이트 5.1부터)
            // 예) 회원의 이름과 팀이 이름이 같은 대상 외부 조인
            // JPQL : SELECT m From Member m left join Team t on m.username = t.name
            String onNoRelEntityQuery = "select m from Member19 m left join Team t on m.username = t.name";
            List<Member19> resultList5 = em.createQuery(onNoRelEntityQuery, Member19.class).getResultList();
            for (Member19 member19 : resultList5) {
                System.out.println(member19.getUsername());
            }

//                    .getResultList();
//            for (Member19 member19 : resultList3) {
//                System.out.println(member19.getUsername());
//            }



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
