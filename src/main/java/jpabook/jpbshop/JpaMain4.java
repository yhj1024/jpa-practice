package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpamain4domain.MemberTest4;
import jpabook.jpbshop.domain.jpamain4domain.TeamTest4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain4 {

    public static void main(String[] args) {

        // 연관 관계 매핑 시 고려사항 4가지
        // 다대일, 일대다, 일대일, 다대다

        // 연관관계 매핑 시 고려사항 3가지
        // 다중성, 단방향, 양방향, 연관관계의 주인

        // 다대일 @ManyToOne
        // 일대다 @OneToMany
        // 일대일 @OneToOne
        // 다대다 @ManyToMany

        // 헷갈릴 때
        // 예) 회원 팀 , 반대로 팀 회원을 생각해보면 된다

        // 실무에서 다대다는 사용하면 안 된다

        // 테이블 : 외래 키 하나로 양쪽 조인 가능, 사실 방향 개념이 없음
        // 객체 : 참조용 필드가 있는 쪽으로만 참조 가능
        // 한 쪽만 참조하면 단방향, 양 쪽이 서로 참조하면 양방향

        // 연관 관계의 주인
        // 테이블은 외래 키 하나로 두 테이블이 연관 관계를 맺음
        // 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
        // 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
        // 연관관계의 주인 : 외래 키를 관리하는 참조
        // 주인의 반대편: 외래 키에 영향을 주지 않음, 단순 조회만!

        // 다대일 [N:1]
        // 멤버와 팀이 있으면 당연히 팀이 1 멤버가 다이다
        // 이러면 외래 키가 다(멤버)에 있어야 한다

        // 단방향 매핑 이전 Member와 Team에 @ManyToOne 같은거 지우고 연습 해보기

        // 일대다 [1:N]
        // 일대다 단방향
        // 실무에서는 잘 하지 않는 방식
        // 이번엔 팀과 멤버에서 팀을 기준으로 연관관계 주인으로 (FK 관리 등 여기서) 하도록 해본다
        // Team 객체에 일단 List members가 있고 Member는 Team을 알고 싶지 않은 상황

        // DB 입장으로 보면 멤버에 무조건 Team Id가 들어가야됨
        // Team에 Member Id를 가지는건 말이 안됨

        // 어쨌든 Team의 List members가 변경되면 Member 테이블의 Team Id가 변경되도록 해야함
        // jpamain4domain 예시 참고

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            MemberTest4 memberTest4 = new MemberTest4();
            memberTest4.setName("멤버테스트4");
            em.persist(memberTest4);

            TeamTest4 team = new TeamTest4();
            team.setName("팀A");
            // 여기가 좀 애매해진다
            // 이거는 팀 테이블에 insert 될 내용이 아님
            // 이러면 외래키가 바뀌어야 하는데 member 테이블이
            // update 되어야함
            team.getMembers().add(memberTest4);
            em.persist(team);
            /* create one-to-many row jpabook.jpbshop.domain.jpamain4domain.TeamTest4.members update
                    MemberTest4
            set
            TEAM_ID=?
            where
            MEMBER_ID=?
            */
            // 이러면 단점이 팀만 손을 댔는데
            // 왜 멤버 테이블이 업데이트 되는지 의문이 생길 수 있다
            // 실무 수십개 테이블이 엮여서 돌아가는데 이렇게 되면 운영이 힘듬

            // 이러면 양방향 연관관계를 추가한다는 전략으로 감
            // 이러면 객체 지향 손해 (멤버에서 -> 팀으로 가는 경우) 
            // 를 포기하더라도 (트레이드 오프) 다대일 양방향 가는게 낫다

            // 일대다 단방향 정리
            // 일대다 단방향은 일대다 에서 일이 연관관계의 주인
            // 테이블 일대다 돤계는 항상 다 쪽에 외래 키가 있음
            // 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
            // @JoinColumn을 꼭 사용해야함 그렇지 않으면 조인 테이블 방식을 사용함 (중간에 테이블을 하나 추가함)
            // 안 쓰면 중간 테이블을 자동으로 만들어서 방식을 바꾸게됨
            // 단점 : 성능, 운영 어려움

            // 일대다 양방향
            // member 객체 쪽에 team 객체를 추가하고
            // @JoinColumn(name="team") 이렇게 하면 엄청 나게 꼬이게 된다 어디서 먼저 업데이트되고 변할지 모르니
            // insertable= false, updatable= false
            // 그래서 읽기 전용으로 만들어 서 써버리는 방법이 있다 (약간의 야매 방식)

            // 결론 : 다대일 양방향을 사용하자

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();



    }

}
