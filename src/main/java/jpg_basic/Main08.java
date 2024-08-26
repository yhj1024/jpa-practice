package jpg_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main08 {

    public static void main(String[] args) {
        // Table 전략
        // 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
        // 장점 : 모든 데이터베이스 적용 가능
        // 단점 : 성능

        // Member04

        // 키본 키 제약 조건 : null 아님, 유일, 변하면 안된다.
        // 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자
        // (랜덤 id같은)

        // 예를 들어 주민등록번호도 기본 키로 적절하지 않다.
        // 예) 정부에서 주민번호를 삭제하라고함 근데 주민번호를 PK로 사용 중이였음
        // 주민등록번호 관련된 모든 FK 로 쓰는 곳을 마이그레이션 필요
        
        // 권장 : Long형 + 대체키 + 키 생성전략 사용

        // 비즈니스를 절대, 키로 끌고 오 는것을 권장 하지 않음

        // 근데 INSERT 하고 PK 값이 필요하다?
        // 근데 오토인크리먼트 사용하게 되면 DB에 INSERT가 되어봐야 알 수 있음
        // 그래서 제네레이터에 INDENTITY 전략을 사용하게 되면
        // persist 시점에 바로 insert sql 을 날린다
        // 그리고 1이란 값을 세팅 해준다

        // 쿼리를 모아서 날릴 수 없다는 게 단점
        // 근데 모아서 날리는 게 크게 메리트 있진 않음

        // SEQUENCE 전략을 사용하게 되면
        // persist 때 nextval로 시퀀스만 얻어오고 commit 때 insert 나감

        // 이러면 DB 네트워크 비용 때문에 성능 이슈가 있지 않냐 라고 할 수 있음
        // 이거를 최적화하기 위해 이럴 때 allocationSize 를 사용함
        // 50을 주면 한번 조회할 때 미리 50개를 땡겨 오고 메모리에서 하나씩 땡겨쓰는 방법
        // 이게 기가 막히게 동시성 이슈 없이 사용할 수 있게 해준다

        // 그리고 SEQ 를 두번 호출하는데
        // 처음엔
        // DB 1 | 1
        // DB 51 | 2
        // DB 51 | 3
        // 이러한 구조로 되어 1 호출하고 50을 더 가져와서 두번 호출함 세번째는 호출 안함

        // allocationSize 를 크게 잡아도 되는데 서버가 내려가는 시점에 값은 다 날아가서 구멍이 생김
        // 100 정도가 적당

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        tx.begin();

        try {
            Member4 member1 = new Member4();
            member1.setUsername("A"); //1, 51
            Member4 member2 = new Member4();
            member2.setUsername("B"); // MEM 메모리에서 시퀀스 호출
            Member4 member3 = new Member4();
            member3.setUsername("C"); // MEM 메모리에서 시퀀스 호출

            System.out.println("=========");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            System.out.println("member1.id : " + member1.getId());
            System.out.println("member2.id : " + member2.getId());
            System.out.println("member3.id : " + member3.getId());

            System.out.println("=========");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

    }

}
