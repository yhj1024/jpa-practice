package jpabook.jpbshop.domain.jpa19domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Delivery19 {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address19 address19;


}
