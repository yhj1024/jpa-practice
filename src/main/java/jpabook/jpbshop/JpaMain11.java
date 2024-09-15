package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member2;
import jpabook.jpbshop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain11 {

    // 프록시와 연관관계 정리

    // 프록시
    // 즉시 로딩과 지연 로딩
    // 지연 로딩 활용
    // 영속성 전이 : CASCADE
    // 고아 객체
    // 영속성 전이 + 고아 객체, 생명 주기
    // 실전 예제 - 5. 연관관계 정리

    // 프록시
    // 왜 써야 하나?
    // Member를 조회할 때 Team도 함께 조회해야 할까?
    // 아래 코드 확인
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 멤버와 팀을 같이 출력하는 함수가 있다고 해보자
            // 이러면 한 쿼리에 멤버 + 팀 가져오는게 좋다
//            Member2 member = em.find(Member2.class, 1L);
//            printMemberAndTeam(member);

            // 근데 요구사항이 바뀌어서 멤버만 출력하는 로직으로 변경되면
            // Team 을 조인해서 가져올 필요가 없다
//            printMember(member);

            // 그래서 이거를 해결하기 위해
            // JPA 는 이거를 지연로딩과 프록시로 기가막히게 해결한다

            // 프록시 기초
            // em.find() vs em.getReference()
            // em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
            // em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
            // client => em.getReference() => proxy

            // 실제 아래 예시
            Member2 member2 = new Member2();
            member2.setUsername("홍길동");
            em.persist(member2);

            em.flush();
            em.clear();

            //
//            Member2 findMember = em.find(Member2.class, member2.getId());
//            System.out.println("findMember = " + findMember.getClass());
//            System.out.println("find member id : " + findMember.getId());
//            System.out.println("find member name : " + findMember.getUsername());
//
            //
//            Member2 reference = em.getReference(Member2.class, member2.getId());
//            System.out.println("findMember = " + reference.getClass());
            // class jpabook.jpbshop.domain.Member2$HibernateProxy$WnAWMpHB
            // 하이버네이터가 만든 프록시 클래스가 찍힘
//            System.out.println("find member id : " + reference.getId()); // 이 때 쿼리가 나가지 않는다. id가 매개변수로 주어져서
//            System.out.println("find member name : " + reference.getUsername()); // 이 때 쿼리가 나감

            // em.find를 하면 진짜 객체를 준다
            // em.getReference 를 하면 가짜 (프록시) 엔티티 객체 조회

            // 프록시 특징
            // 실제 클래스를 상속 받아서 만들어짐
            // 실제 클래스와 겉 모양이 같음
            // 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면됨 (이론상)
            // Client - em.getReference() -> Proxy (Entity target = null, getId(), getName())

            // 프록시 객체는 실제 객체의 참조를 보관함 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
            // 프록시 객체의 초기화
            // 1. Client => getName()
            // 2. MemberProxy 내부에 Member target = null 이므로, 영속성 컨텍스트에 초기화 요청
            // 3. 영속성 컨텍스트는 DB 조회
            // 4. 실제 Member Entity 생성
            // 5. target.getName() 을 통해 Entity 조회

            // 프록시의 특징

            // 프록시 객체는 처음 사용할 때 한번만 초기화
            // 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님,초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
            // 프록시 객체는 원본 엔티티를 상속 받음, 따라서 체크 시   ( == 비교 실패, 대신 instance of 사용)
            // Member m1 em.find
            // Member m2 em.find
            // m1.getClass == m2.getClass <== true
            // Member m1 em.getReference
            // Member m2 em.getReference
            // m1.getClass == m2.getClass <== false
            // 영속성 컨텍스트에 찾는 엔티티가 있으면 em.getReference() 를 호출해도 실제 엔티티 반환
            // Member m1 = em.find 동일한 id
            // Member m2 = em.ref 동일한 id
            // m2.getClass() => 프록시 객체가 아닌 실제 엔티티 반환 (영속성 컨텍스트에 찾는 엔티티가 있는 경우)
            // 성능 면에서 1차 캐시에 있는데 굳이 프록시 객체를 사용할 필요가 없다
            // m1 == ref 는 항상 true가 나와야 한다
            // Member refMember = em.ref.getClass() => Proxy 객체
            // Member findMember = em.find.getClass() => Member 객체
            // refMember == findMember => false가 나와야 하지만 findMember가 Proxy객체를 반환하여 true가 나옴
            // (JPA 같은 트랜잭션에서는 동일하게 보장하기 위해 ref를 먼저 조회를하면 find를 해도 Proxy가 나올 수 있다)

            // 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화 하면 문제 발생
            // Member ref
            Member2 reference = em.getReference(Member2.class, member2.getId());
            System.out.println(reference.getClass());

//            em.detach(reference);
//             em.close();
//            em.clear();

            // 이 때 SQL 이 나가면서 프록시 객체가 초기화 될 것
            reference.getUsername();

            // 근데 저게 나가기 전에
            // em.close(); <-- 영속성 컨텍스트를 끈다면?
            // 또는 em.detach(reference) 영속성 컨텍스트에서 빼버린다면?
            // em.clear(); 로 영속성 컨텍스트를 비운다면?

            // org.hibernate.LazyInitializationException: could not initialize proxy [jpabook.jpbshop.domain.Member2#1] - no Session
            // 이런 오류가 발생한다

            // (em.close 는 5.4.0.Final 버전까지는 예외가 발생하는데, 5.4.1.Final 버전부터는 오류가 발생하지 않음)
            // 트랜잭션이 유지 되고 있으면 Lazy 로딩 사용 가능하도록 변경됨)
            // https://hibernate.atlassian.net/browse/HHH-13191 이슈를 수정하면서 고친듯

            // 프록시 인스턴스의 초기화 여부 확인
            // emf.getPersistenceUnitUtil().isLoaded(reference);
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(reference));

            // 프록시 클래스 확인 방법
            // entity.getClass().getName()
            System.out.println(reference.getClass().getName());

            // 프록시 강제 초기화
            // reference.getUsername(); 이렇게해도 초기화 되는데 이상하니까
//            org.hibernate.Hibernate.initialize(reference); 이런 식으로 하용
            // JPA 표준은 강제 초기화 없음
            // 강제 호출 member.getName()


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            emf.close();
        }
        em.close();

    }

    private static void printMember(Member2 member) {
        String username = member.getUsername();
        System.out.println("username =" + username);
    }

    private static void printMemberAndTeam(Member2 member) {
        String username = member.getUsername();
        System.out.println("username =" + username);
        Team team = member.getTeam();
        System.out.println("team =" + team.getName());
    }


}
