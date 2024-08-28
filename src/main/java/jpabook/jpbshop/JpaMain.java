package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;
import jpabook.jpbshop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            
            // ENTITY 에 index나, 제약 조건 같이 넣어 주면 개발자가 entity만 보고 파악할 수 있어 편리하다

            // 테이블 필드에 orderDate 이대로 생성되는데 자바는 카멜인데
            // DB는 보통 파스칼 order_date, ORDER_DATE 처럼 생성한다.
            // 스프링 부트에서는 스프링부트 JPA 를 걸어서 올리면 알아서 파스칼로 바꿔준다
            // (설정 기본 값)
            // Column으로 일일히 매핑 해주는 방법도 있음!

            // 어떤 주문을 가져온다고 하자
            Order order = em.find(Order.class, 1L);
            // 이제 주문을 한 멤버를 조회하고 싶다?
            Long memberId = order.getMemberId();
            // 주문에 멤버 아이디로 멤버를 조회해옴
            Member member = em.find(Member.class, memberId);

            // 이 과정이 객체지향 스럽지 않다.
            // order안에 member가 있어야 객체지향스러운 코드이다
            // order.getMember(); 이렇게 가져올 수 있어야 함

            // 그래서 이런 방식의 설계는 객체지향스럽지 않다.
            // 이거는 관계형 DB에 맞춘 설계이다

            // 데이터 중심의 설계 문제점

            // 현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
            // 테이블의 외래키를 객체에 그대로 가져옴
            // 객체 그래프 탐색이 불가능
            // 참조가 없으므로 UML 도 잘못됨

            // 목표

            // 객체와 테이블 연관관계의 차이를 이해
            // 객체의 참조화 테이블의 외래 키를 매핑

            // 용어 이해
            // 방향 (Direction) : 단방향, 양방향
            // 다중성 (Multiplicity) : 다대일 (N:1), 일대다 (1:N), 일대일 (1:1), 다대다(N:M) 이해
            // 연관 관계의 주인 (Owner) : 객체 양방향 연관 관계는 관리 필요 *c언어의 포인트 같은 개념)

            // 연관 관계가 필요한 이유
            // 객체지향 설계의 목표는 자율적인 객체들의 협력 공동체를 만드는 것이다
            // 조영호

            // 예제 시나리오
            // 회원과 팀이 있다
            // 회원은 하나의 팀에만 소속될 수 있다
            // 회원과 팀은 다대일 관계다 (하나의 팀에 회원은 여러명 속할 수 있다)

            // 테이블 연관관계 설계
            // 회원(다)     팀(1)
            // member_id   team_id
            // team_id     name
            // username

            // 객체 연관관계 설계
            // 회원         팀
            // id           id
            // teamid       name
            // username

            // 이러면 참조 대신 키를 사용하게 된다

            // 이러면 회원을 만들고 팀을 만든다고 해보자
            // team을 생성하고 회원을 insert하는데
            // 키 참조를 하기때문에 setTeamId를 해야함
            // 이게 객체지향스럽지 않다 setTeam을 해야 객체지향스러운것

            // (그리고 조회할 땐 select * from member m join team t on m.teamid = t.id
            // ansi 표준 문법)

            // 어쨌든 멤버가 속한 팀을 찾으려면
            // 멤버를 조회하고 팀 id를 이용해서 또 팀을 조회해야됨
            // 여기서 또 객체지향스럽지 않음

            // 따라서 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다
            // 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다
            // 객체는 참조를 사용해서 연관된 객체를 찾는다
            // 테이블과 객체 사이에는 이런 큰 간격이 있다

            // 객체 지향 모델링
            // 객체 연관관계 사용
            // Member       Team
            // id           id
            // Team team    name
            // username
            
            // Member2 Entity 참고

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
