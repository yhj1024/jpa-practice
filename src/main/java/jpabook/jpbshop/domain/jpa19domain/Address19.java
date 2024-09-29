package jpabook.jpbshop.domain.jpa19domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address19 {

    @Column(length = 30) // 길이제한
    private String city;

    @Column(length = 30) // 길이제한
    private String address;

    @Column(length = 30) // 길이제한
    private String zipcode;

    public String getFullAddress() { // 이런 식으로 값 타입에 필요한 비즈니스 코드를 추가하여 응집도를 높일 수 있다
        return this.getCity() + " " + this.getAddress() + " " + this.getZipcode();
    }

    public String getCity() {
        return city;
    }

//    public void setCity(String city) { 값 타입 에서 setter 위험 (불변성)
//        this.city = city;
//    }

    public String getAddress() {
        return address;
    }

//    public void setAddress(String address) { 값 타입 에서 setter 위험 (불변성)
//        this.address = address;
//    }

    public String getZipcode() {
        return zipcode;
    }

//    public void setZipcode(String zipcode) { 값 타입 에서 setter 위험 (불변성)
//        this.zipcode = zipcode;
//    }


    @Override
    public boolean equals(Object o) { // 생성할 때 use getter 체크 해주고 하는게 좋음 getter 를 이용하여 값 접근
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address19 address19 = (Address19) o;
        return Objects.equals(getCity(), address19.getCity()) && Objects.equals(getAddress(), address19.getAddress()) && Objects.equals(getZipcode(), address19.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getAddress(), getZipcode());
    }
}
