package jpabook.jpbshop.domain.jpa7domain;


import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Item7Domain {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category7Domain> categorys = new ArrayList<>();

}
