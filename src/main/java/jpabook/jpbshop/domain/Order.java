package jpabook.jpbshop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS") // ORDER 가 SQL 예약어로 걸린 경우가 있어 ORDERS 로 사용
public class Order {
    // ENTITY 에 index나, 제약 조건 같이 넣어 주면 개발자가 entity만 보고 파악할 수 있어 편리하다

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    private LocalDateTime orderDate;
    // 테이블 필드에 orderDate 이대로 생성되는데 자바는 카멜인데
    // DB는 보통 파스칼 order_date, ORDER_DATE 처럼 생성한다.
    // 스프링 부트에서는 스프링부트 JPA 를 걸어서 올리면 알아서 파스칼로 바꿔준다
    // (설정 기본 값)
    // Column으로 일일히 매핑 해주는 방법도 있음!

    @Enumerated(EnumType.STRING) // ORDINAL 오디널로 하면 안됨 순서로 들어가서
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
