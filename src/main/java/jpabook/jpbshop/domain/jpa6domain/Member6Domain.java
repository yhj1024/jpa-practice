package jpabook.jpbshop.domain.jpa6domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member6Domain {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
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
