package jpabook.jpbshop.domain.jpa6domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id @GeneratedValue
    @Column(name="PRODUCT_ID")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "MEMBER_PRODUCT") // 하나는 mappedBy 처리
    private List<Member6Domain> members;

    // 다대일로 풀어냄
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProduct;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
