package jpabook.jpbshop;

public class JpaMain14 {

    public static void main(String[] args) {
        // 연관관계 정리
        // !!OneToOne, MannToOne 관계는
        // 기본 로딩이 즉시 로딩이다
        // 모든걸 기본 로딩으로 바꾸는게 좋다

        // 모든 OneToOne, MannToOne 를 Lazy 로 변경

        // 영속성 전이 설정
        // Order -> Delivery 영속성 전이 ALL 설정
        // Order -> OrderItem 영속성 전이 ALL 설정

        // 주문을 만들면서 배송 정보를 만든다, 라이프 사이클을 맞춘다
        // 이러면 오더가 만들어 지면서 딜리버리가 자동으로 INSERT 됨


    }

}
