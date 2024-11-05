package jpabook.jpbshop;

import jpabook.jpbshop.domain.Team;
import jpabook.jpbshop.domain.jpa19domain.Member19;
import jpabook.jpbshop.domain.jpa19domain.Team19;

import javax.persistence.*;
import java.util.List;

public class JpaMain30 {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 페치 조인 (fetch join) 실무에서 엄청 중요함
        // sql 조인 종류 x
        // jpql 에서 성능 최적화를 위해 제공하는 기능
        // 연관된 엔티티나 컬렉션을 sql 한번에 함께 조회하는 기능
        // join fetch 명령어 사용
        // 페치 조인 ::=[ LEFT [OUTER] | INNER ] JOIN FETCH 조인 경로

        // 엔티티 페치 조인
        // 회원을 조회하면서 연관된 팀도 함께 조회 (SQL 한번에)
        // SQL 을 보면 회원 뿐만 아니라 (팀T.*)도 함께 SELECT
        // [JPQL] select m from Member m join fetch m.team
        // [SQL] select.*, t.* from Member m inner join team t on m.team_id = t.id

        // 구조 아래 처럼 영속성 컨텍스트에 관리가 된다
        // MEMBER - TEAM - MEMBER JOIN TEAM

        // MEMBER
        // ID NAME TEAM
        // 1  회원1 1
        // 2  회원2 1
        // 3  회원3 2

        // TEAM
        // 1 팀A
        // 2 팀B
        // 3 팀C

        // ID NAME TEAM_ID ID NAME
        // 1  회원1 1       1  팀A
        // 2  회원2 1       1  팀A
        // 3  회원3 2       2  팀B

        // LIST
        // 회원1 -> [팀A]
        // 회원2 -----^
        // 회원3 -> [팀B]
        // fetch join

        Team19 teamA = new Team19();
        teamA.setName("팀A");
        em.persist(teamA);

        Team19 teamB = new Team19();
        teamB.setName("팀B");
        em.persist(teamB);

        Member19 member1 = new Member19();
        member1.setUsername("회원1");
        member1.setTeam(teamA);
        em.persist(member1);

        Member19 member2 = new Member19();
        member2.setUsername("회원2");
        member2.setTeam(teamA);
        em.persist(member2);

        Member19 member3 = new Member19();
        member3.setUsername("회원3");
        member3.setTeam(teamB);
        em.persist(member3);

        em.flush();
        em.clear();

//        List<Member19> selectMFromMember19M = em.createQuery("select m from Member19 m", Member19.class).getResultList();
//
//        for (Member19 member19 : selectMFromMember19M) {
//            // 지연로딩이 걸려있어서 팀A 를 사용하는 순간 쿼리 조회함
//            // 팀A 쿼리가 한번만 나가는데 영속성 컨텍스트에 (1차 캐시에) 저장하고 이 값을 재참고 하기 때문!
//            // 회원1, 팀A (영속성 컨텍스트에 없음, SQL 조회)
//            // 회원1, 팀A (영속성 컨텍스트에 있음, 1차 캐시에 저장된 값 사용)
//            // 회원1, 팀B (영속성 컨텍스트에 없음, SQL 조회)
//
//            // 근데 회원 100명이 있고 최악의 경우에는 쿼리가 100번이 나감
//            // 이거는 지연로딩이건 아니건 무조건 이렇게 됨
//            // 그래서 이걸 풀기 위해 페치조인을 사용
//
//            System.out.println(member19.getUsername() + " " + member19.getTeam().getName());
//        }

//        List<Member19> selectMFromMember19M2 = em.createQuery("select m from Member19 m join fetch m.team", Member19.class).getResultList();
//
//        for (Member19 member19 : selectMFromMember19M2) {
//            // fetch 조인을 하게 되면 sql 나갈때 team 을 inner join 으로 같이 가지고온다
//            // 지연 로딩을 걸어도 fetch join이 우선된다
//            System.out.println(member19.getUsername() + " " + member19.getTeam().getName());
//        }

        // 컬렉션 페치 조인
        // 일대다 관계 컬렉션 페치 조인
        // 반대로해도 들고온다는 얘기
        // 팀A 를 가져오는 쿼리

        String query = "select t from Team19 t join fetch t.members";
        List<Team19> resultList = em.createQuery(query, Team19.class).getResultList();
        for (Team19 team19 : resultList) {
            System.out.println("Team name : " + team19.getName() + " Member Count : " + team19.getMembers().size());
            for (Member19 member19 : team19.getMembers()) {
                System.out.println("Member : " + member19.getId() + " " + member19.getUsername());
            }
        }

        // 팀은 2개뿐인데 Row가 3개가 찍힌다
        // 일대다 관계 조인이라서 그렇다

        // [TEAM JOIN MEMBER]
        // ID NAME ID TEAM_ID NAME
        // 1  팀A   1 1       회원1
        // 1  팀A   2 1       회원2
        // 이런 형태로 나오게 됨
        // JPA가 어떻게 할 수 없음 (미리 Member가 몇명인지 알 수가 없다)

        // teams 결과 리스트
        // 0x100
        //       ---> 팀A(0x100) --> member -> 회원1, 회원2
        // 0x100                   fetch join

        // 그래서 이거를 2개로 만들고 싶다
        // 일단 distinct 를 사용해본다

        String query2 = "select distinct t from Team19 t join fetch t.members";
        List<Team19> resultList2 = em.createQuery(query2, Team19.class).getResultList();
        for (Team19 team19 : resultList2) {
            System.out.println("Team name : " + team19.getName() + " Member Count : " + team19.getMembers().size());
            for (Member19 member19 : team19.getMembers()) {
                System.out.println("Member : " + member19.getId() + " " + member19.getUsername());
            }
        }

        // 근데 SQL 은 아래 처럼 나올거다
        // 팀A ID 1 회원1
        // 팀A ID 2 회원2
        // 문제는 SQL에서 DISTINCT 는 완전히 동일해야 중복이 제거가 된다
        // 그래서 DISTINCT를 사용하면 JPA 가 추가로 애플리케이션에 중복 제거를 시도한다
        // 같은 식별자를 가진 [ Team 엔티티 제거 ] 해준다

        // 다대일 관계는 늘어나지 않는다

        // 페치 조인과 일반 조인의 차이
        // 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않음

        // 일반 조인
        String query3 = "select t from Team t join t.members m";
        List<Team19> resultList3 = em.createQuery(query3, Team19.class).getResultList();
        // 실행 sql을 보면
        // select id, name from Team19 t inner join members m on t.id = m.id
        // 이런 식으로 select 절에 팀만 가지고 오고 members는 가지고 오지 않음
        // 그래서 members 탐색을 하게 되면 쿼리가 추가로 또 나가게 됨

        // 페치 조인은 select 에 다 퍼올려서 가져 온다 (즉시 로딩)
        // 페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념

        // 실무에서 많이 쓰인다

    }
}
