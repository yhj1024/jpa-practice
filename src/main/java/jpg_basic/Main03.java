package jpg_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main03 {
    public static void main(String[] args) {
        // 엔티티 조회, 1차 캐시

        // 엔티티를 생성한 상태 (비영속)
        // member.setId("member1");
        // member.setUserName("회원1");

        // 1차 캐시에 저장
        // em.persis(member);

        // 이러면 영속 컨텍스트 안에 1차 캐시에 @Id member1, Entity member 형태로 저장이 됨

        // 이 때 find를 하게되면 DB를 조회하지 않고 1차 캐시에서 조회를 한다.
        // Member findMember = em.find(Member.class, "member1");

        // 만약 멤버2를 조회하면 1차 캐시에 없기 때문에 db에서 조회 후 1차 캐시에 저장을 한다
        // 그리고 1차 캐시에서 조회를 하여 반환해준다. 이 후 member2를 조회하면 또 캐시를 가져오게됨
        // Member findMember = em.find(Member.class, "member2");

        // 여러 명이 공유하는 캐시가 아니고 한 트랜잭션 안에서만 발생하는 캐시임
        // 큰 성능 이점의 장점은 없으나 비즈니스 로직이 굉장히 큰 경우 이득이 생길 수 있다.

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member();
            // 1차 캐시 사용 하지 않도록 수정함
            member.setId(101L);
            member.setName("HelloJPA");

            //영속
            System.out.println("==== BEFORE ====");
            em.persist(member);
            System.out.println("==== AFTER ====");

            // 이 때 select 쿼리가 안 나감 (1차 캐시에서 조회함)
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
            
            // 이번엔 40, 41, 45 주석을 치고 실행하면
            // 1차 캐시에 없기 때문에 DB에서 조회함
            // find 101L 두개가 연속해서 있으면 한번은 DB조회 한번은 1차 캐시 조회함

            // 영속 엔티티의 동일성 보장
            Member findMemberA = em.find(Member.class, 101L);
            Member findMemberB = em.find(Member.class, 101L);

            System.out.println(findMemberA == findMemberB); // true
            // 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공

            // 엔티티 등록
            // 트랜잭션을 지원하는 쓰기 지연

            // em.persist(memberA);
            // em.persist(memberB);

            // 여기까지 INSERT SQL 을 데이터 베이스에 보내지 않는다
            // 영속 컨텍스트 안에는 쓰기 지연 SQL 저장소라는게 있다
            // memberA 를 persist 하면 1차 캐시에 안에 들어가면서 동시에
            // insert 쿼리를 생성하여 쓰기 지연 SQL 저장소에 쌓는다
            // memberB 도 마찬가지로 1차 캐시에 안에 들어가면서 동시에
            // insert 쿼리를 생성하여 쓰기 지연 SQL 저장소에 쌓는다

            // 그리고 commit 이 되는 시점에
            // 쿼리가 한번에 나가고 이거를 flush 라고 한다

            tx.commit();

        }catch (Exception e) {

        }


    }
}
