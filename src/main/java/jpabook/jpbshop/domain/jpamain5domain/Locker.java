package jpabook.jpbshop.domain.jpamain5domain;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name="LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker") // 일대일은 다대일과 같은 원리로 처리할 수 있다!
    private Member5Domain member5Domain;


}
