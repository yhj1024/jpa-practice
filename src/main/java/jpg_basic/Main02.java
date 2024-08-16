package jpg_basic;

public class Main02 {

    public static void main(String[] args) {
        // JPA 가장 중요한 요소
        // 1. 객체 RDB 매핑 설계
        // 2. 영속성 컨텍스트
        // 엔티티를 영구 저장하는 환경이라는 뜻
        // persist 가 단순히 저장이라고 배웠는데 이거는 사실 영속성 컨텍스트를 통해 영속화 한다는 뜻
        // 영속성 컨텍스트는 논리적인 개념, 눈에 보이지 않음, 엔티티 매니저를 통해서 영속성 컨텍스트에 접근
        // J2SE 환경 엔티티 매니저와 영속성 컨텍스트가 1:1
        // J2EE, 스프링 프레임워크 같은 컨테이너 환경 엔티티 매니저와 영속성 컨텍스트가 N:1

        // 엔티티의 생명주기
        // 비영속 (new/transient) => new 해서 객체 생성
        // 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
        // Member member = new Member();
        // member.setId("member1"); member.setUserName("회원1");

        // 영속 (managed) => persist 한 상태
        // EntityManager em = emf.createEntityManager();
        // em.getTransaction().begin();
        // em.persist(member);
        // 영속 상태가 된다고 실제 DB 로 나가지는 않는다 (commit 시점에 나감)

        // 영속성 컨텍스트에 관리되는 상태
        // 준영속 (detached)
        // em.detach(member); 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태

        // 영속성 컨텍스트에 저장되었다가 분리된 상태
        // 삭제 (removed)
        // em.remove(member);
        // 삭제된 상태

        // 영속성 컨텍스트의 이점

        // 1. 1차 캐시
        // 2. 동일성 보장
        // 3. 트랜잭션을 지원하는 쓰기 지연
        // 4. 변경 감지
        // 5. 지연 로딩
    }
}
