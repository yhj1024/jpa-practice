package jpabook.jpbshop.domain.jpa7domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category7Domain {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID") // 상위 카테고리 셀프 조인 가능
    private Category7Domain parent;

    @OneToMany(mappedBy = "parent") // 하위 카테고리 조인
    private List<Category7Domain> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
        inverseJoinColumns = @JoinColumn(name= "ITEM_ID")
    ) // 조인 테이블 name, 조인 컬럼은 CATEGORY_ID 반대쪽은 ITEM_ID 라고 알려주는것
    private List<Item7Domain> items = new ArrayList<>();


}
