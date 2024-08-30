package jpabook.jpbshop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    // 멤버에있는 team과 연관관계
    // team 으로 매핑이 되어있다는 의미
    // mappedBy JPA 의 멘탈붕괴 난이도, 처음에는 이해하기 어렵다
    // 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.
    // 객체 연관 관계는 2개
    // 회원 -> 팀 연관관계 1개 (단방향)
    // 팀 -> 회원 연관관계 (단방향)
    // 테이블의 연관 관계는 1개
    // 회원 <-> 팀
    // 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개이다
    // 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다
    // 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
    // MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계 가짐
    // 여기서 딜레마가 온다 둘중 하나로 외래 키를 관리해야 한다
    
    // Member (id, Team team, username)
    // Team (id, name List members)
    // 멤버의 팀을 수정한다고 하자
    // Member의 team을 바꿀수도 있고 Team의 members를 바꿀 수도 있는데,
    // DB 관점에서보면 Member의 team_id fk를 변경하는게 옳다

    // 극단적인 예로 멤버에는 팀에 값을 넣고
    // 팀 멤버스에는 값이 없다
    // 반대로 멤버에는 팀이 없고
    // 팀 멤버스에는 값이 있다
    // 이럴때 어떻게 할지 애매한 상황이 생김
    // 그래서 둘 중 하나로 외래 키를 관리해야 한다
    // 이게 연관관계의 주인인 것

    // 연관 관계 주인
    //  양방향 매핑 규칙
    //   객체의 두 관계 중 하나를 연관관계의 주인으로 지정
    //   연관관계의 주인만이 외래 키를 관리 (등록, 수정)
    //   주인이 아닌 쪽은 읽기만 가능
    //   주인은 mappedBy 속성 사용 x (내가 매핑이 되었다는 뜻임)
    //   주인이 아니면 mappedBy 속성 지정을 해주어야 함
    //   누구를 주인으로?
    //   외래 키가 있는 곳을 주인으로 설정한다
    //   여기서는 Member.team 이 주인이 된다
    //   또는 일대다에서 다쪽이 (FK를 가진) 연관관계 주인이 된다 
    @OneToMany(mappedBy = "team")
    private List<Member2> members = new ArrayList<>();
    // new Array List는 관례 (null pointer 방지)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Member2> getMembers() {
        return members;
    }

    public void setMembers(List<Member2> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }
}
