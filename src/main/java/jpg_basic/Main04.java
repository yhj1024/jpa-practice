package jpg_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main04 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            
            // 영속
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "A");
//
//            em.persist(member1);
//            em.persist(member2);

//            System.out.println("==================");
            // 쿼리는 위에서 나가지 않고 아래에서 나감
//            영속성 컨텍스트 : 엔티티를 영구 저장하는 환경이라는 뜻이다. 애플리케이션과 데이터베이스 사이에서 객체를 보관하는 가상의 데이터베이스 같은 역할을 한다. 엔티티 매니저를 통해 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.

            // 그리고 batch 옵션을 통해 N개씩 짤라서 쿼리를 날릴 수 있음
            // 마바같은거 쓰면 직접 구현하기 힘듬 어쨌든 이러한 이 점을 얻을 수 있다

            // 엔티티 수정 변경 감지

            // 이번엔 150 멤버를 찾아본다
            // 이거를 NAME을 TEST 라고 변경한다

            // 그리고 업데이트 할 때 em.persist(member) 이렇게 넣어야 된다고 생각할 수 있는데 그렇지 않다
            // 자바 컬렉션처럼 그냥 setName만 해주면 끝난다

            Member findMember = em.find(Member.class, 150L);
            findMember.setName("AAAAAA");

            // 이것의 원리는 변경 감지 (더티 체킹) 를 제공한다.
            // 이것의 원리는 영속성 컨텍스트 안에 있다

            // 커밋을 하면 내부적으로
            // 1. flush 라는게 호출됨
            // 2. 스냅샷 이라는게 있는데 1차 캐시에 저장된 엔티티와 스냅샷을 전체를 다 비교해봄
            // 3. UPDATE SQL 을 만들어서 쓰기 지연 저장소에 넣고
            // 4. flush
            // 5. commit 이 된다

            // 삭제 대상 커밋도 마찬가지
            // 엔티티 변경은 persist 를 호출하지 않는 것이 정답

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }

}
