package jpabook.jpbshop.domain.jpa7domain;

import jpabook.jpbshop.domain.OrderItem;
import jpabook.jpbshop.domain.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Delivery7Domain {


    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order7Domain order;

    private String name;
    private String city;
    private String street;
    private String zipCode;



}
