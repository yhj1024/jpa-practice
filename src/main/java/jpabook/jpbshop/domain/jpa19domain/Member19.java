package jpabook.jpbshop.domain.jpa19domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member19 {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Embedded // 생략해도 되지만 명시적으로
    private Address19 address;



}
