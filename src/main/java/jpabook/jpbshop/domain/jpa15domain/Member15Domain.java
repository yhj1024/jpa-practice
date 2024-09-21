package jpabook.jpbshop.domain.jpa15domain;

import javax.persistence.*;

@Entity
public class Member15Domain {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Embedded
    private Period15Domain workPeriod;

    @Embedded
    private Address15Domain homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city", column = @Column(name="WORK_CITY")),
            @AttributeOverride(name="street", column = @Column(name="WORK_STREET")),
            @AttributeOverride(name="zipCode", column = @Column(name="WORK_ZIPCODE"))
    })
    private Address15Domain workAddress;

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

    public Period15Domain getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period15Domain workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address15Domain getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address15Domain homeAddress) {
        this.homeAddress = homeAddress;
    }
}
