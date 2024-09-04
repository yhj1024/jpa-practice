package jpg_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main01 {
    public static void main(String[] args) {
        //
        // 2. persistence -> 생성 EntityManagerFactory

        // persistence -> 생성 EntityManagerFactory 이 때 유닛 name은 xml내 설정한 unit name
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 팩토리까지 생성 하면 db 연결 crud 등 작업 가능
        // 실제 쓰려면 매니저를 꺼내야한다
        EntityManager entityManager = emf.createEntityManager();

        // 3. H2 DB 내 테이블 생성.
        //        create table Member (
        //                id bigint not null,
        //                name varchar(255),
        //                primary key (id)
        //       (

        // 4. Member Entity 생성


        // 5. 멤버 저장하기
//        Member member = new Member();
//        entityManager.persist(member);
        // 멤버 id가 없어 오류 발생

        // 아이디 네임 설정
//        member.setId(2L);
//        member.setName("Hello B");

//        entityManager.persist(member);

//        // 근데도 저장이 뭔가 안되는거 같다? 트랜잭션을 얻어서 사용 필요함
        EntityTransaction entityTransaction = entityManager.getTransaction();
//        entityTransaction.begin();
//        entityManager.persist(member);
//
//        entityTransaction.commit();

        // 쿼리가 정상적으로 나간게 확인됨
        // 포맷이 나간 이유는 persistence.xml 설정때문임
        // showsql : 쿼리 보여줌
        // format : 예쁘게
        // comment : 주석

        // 사용 후 닫아줌
//        entityManager.close();
//        emf.close();

        // 좀 더 정석적으로 사용하면 아래처럼 예외처리 하면서 사용해야됨
        // 하지만 우리는 스프링이 다 해준다..
//        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("Hello B");
//            entityTransaction.begin();
//            entityManager.persist(member);
//            entityTransaction.commit();
//        } catch (Exception e) {
//            entityTransaction.rollback();
//        } finally {
//            entityManager.close();
//        }
//        entityManager.close();

//        EntityTransaction entityTransaction = entityManager.getTransaction();
//        entityTransaction.begin();
//        try {
//            // 조회하기
//            Member findMember = entityManager.find(Member.class, 1L);
////            System.out.println(findMember.getId());
////            System.out.println(findMember.getName());
//            // 삭제하기
////            entityManager.remove(findMember);
//            // 수정하기 ( 자동으로 Update 가 된다 persist 같은게 없는데 신기하다... )
//            // JPA find 로 찾으면 변화를 감지하다가 commit 하는 순간에 update가 되었으면 update 쿼리가 나간다.
//            findMember.setName("HelloJPA");
//            entityTransaction.commit();
//        } catch (Exception e) {
//            entityTransaction.rollback();
//        } finally {
//            entityManager.close();
//        }

        // 엔티티 매니저 팩토리는 딱 하나만 생성이 된다
        // 엔티티 매니저는 클라이언트 요청 마다 썼다가 닫았다가 해준다
        // 따라서 쓰레드 간에 공유를 하면 안된다 (쓰고 버려야 된다)
        // JPA 의 모든 데이터 변경은 트랜 잭션 안에서 실행 된다.

        // 가장 단순한 조회 방법
        // JPQL 소개
        // 가장 단순한 조회 방법
        // EntityManager.find()
        // 객체 그래프 탐색 (a.getB().getC())
        // 나이가 18살 이상이 회원을 모두 검색하고 싶다면?

        // JPQL로 회원 전체 검색
        entityTransaction.begin();
        // * 대신 m 을 씀 객체를 대상으로 조회하는 개념
        List<Member> findMembers = entityManager.createQuery("select m from Member5Domain as m", Member.class)
                .setFirstResult(0) // 페이징 시작 row
                .setMaxResults(5) // 페이징 개수
                .getResultList();

        // JPQL 은 JPA 검색 쿼리 한계로 인해 사용함
        // 엔터티 객체를 대상으로 쿼리를 짤 수 있도록 해줌
        // SQL 추상화한 객체 지향 쿼리 언어
        // 장점 방언을 바꾸거나 해도 SQL 을 바꿀 필요가 없음

        for (Member member : findMembers) {
            System.out.println(member.getId());
            System.out.println(member.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        emf.close();
    }
}