package jpg_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main05 {

    public static void main(String[] args) {
        // Flush 발생
        // 변경 감지, 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
        // 쓰기 지연 SQL 저장소의 쿼리를 데이터 베이스에 전송 (등록, 수정, 삭제 쿼리)

        // 영속성 컨텍스트를 플러시 하는 방법
        // em.flush() - 직접 호출
        // 트랜잭션 커밋 - 플러시 자동 호출
        // JPQL 쿼리 실행 - 플러시 자동 호출
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member(300L, "member200");
            em.persist(member);
            em.flush(); // 이러면 커밋 전에 쿼리가 날아감 (위에 내용 참고)
            System.out.println("===================");
            // 플러시를 해도 1차 캐시가 지워지지는 않음

            // JPQL 쿼리 실행 시 자동으로 플러시가 호출되는 이유
            // 1. em.persist(member);
            // 2. em.persist(member);
            // 3. em.persist(member);
            // 중간에 JPQL 실행
            // query = em.createQuery("select m from Member m", Member.class);
            // List<Member> members = query.getResultsList();
            // 플러시가 호출이 안되면 member 조회가 안됨 잘못하면 문제가 될 수 있어
            // 기본 값으로 JPQL 사용 시 flush 시 호출하도록 되어있음

            // 플러시 모드 옵션
            // em.setFlushMode(FlushModeType.COMMIT)
            // FlushModeType.AUTO
            // 커밋이나 쿼리를 실행할 때 플러시 (기본값)
            // FlushModeType.COMMIT
            // 커밋할 때만 플러시

            // 플러시는 영속성 컨텍스트를 비우지 않는다
            // 영속성 컨텍스트의 변경 내용을 데이터 베이스에 동기화
            // 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨

            // 준영속 상태
            // 영속 -> 준영속
            // 영속 상태의 엔티티가 영속성 컨텍스트에서 분리 (detached)
            // 영속성 컨텍스트가 제공하는 기능을 사용 못함

            // 준영속 상태로 만드는 방법
            // em.detach(entity) : 특정 엔티티만 준영속 상태로 만듬
            // Member member = em.find(Member.class, 150L); 멤버조회 1차캐시저장
            // member.setName("ㄹㄻㄴㅇㄹ");
            // em.detach(member); <-- 이러면 commit 발생해도 sql 나가지 않음
            // 이거를 준영속 상태라고 함
            // em.clear(); 이러면 영속성 컨텍스트 전체 초기화 함
            // em.close(); // 영속성 컨텍스트를 종료



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }



}
