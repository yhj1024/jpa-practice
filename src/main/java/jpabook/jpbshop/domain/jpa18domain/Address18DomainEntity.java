package jpabook.jpbshop.domain.jpa18domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address18DomainEntity {

    @Id @GeneratedValue
    private Long id;

    private Address18Domain address18Domain;

    public Address18DomainEntity(String city, String street, String zipcode) {
        this.address18Domain = new Address18Domain(city, street, zipcode);
    }

    public Address18DomainEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address18Domain getAddress18Domain() {
        return address18Domain;
    }

    public void setAddress18Domain(Address18Domain address18Domain) {
        this.address18Domain = address18Domain;
    }
}
