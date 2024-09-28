package jpabook.jpbshop.domain.jpa18domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member18Domain {

    @GeneratedValue
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded // 임베디드 값 타입
    private Address18Domain homeAddress;

    @ElementCollection // 컬렉션 타입 사용 선언
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID") // 이러면 얘를 외래키로 잡게 됨
    ) // 테이블 이름 지정
    // 아래 임베디드 타입은 컬럼이 잡힐텐데 얘는 STRING 하나이다 컬럼이 어떻게 잡히나? 하나니까 그냥 잡아줄 수 있음
    @Column(name="FOOD_NAME") // 이거만 예외 적으로 사용 가능
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection // 컬렉션 타입 사용 선언
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name="MEMBER_ID") // 이러면 얘를 외래키로 잡게 됨
//    ) // 테이블 이름 지정
//    private List<Address18Domain> addressHistory = new ArrayList<>();

    //대안
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<Address18DomainEntity> addressHistory = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address18Domain getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address18Domain homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

//    public List<Address18Domain> getAddressHistory() {
//        return addressHistory;
//    }
//
//    public void setAddressHistory(List<Address18Domain> addressHistory) {
//        this.addressHistory = addressHistory;
//    }


    public List<Address18DomainEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistoryEntity(List<Address18DomainEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
