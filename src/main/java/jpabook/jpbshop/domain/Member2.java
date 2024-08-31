package jpabook.jpbshop.domain;

import javax.persistence.*;

@Entity
public class Member2 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="USER_NAME")
    private String username;

    // 멤버와 팀이 있을 때, 누가 N이고 누가 1일까? 멤버가 N이고 팀이 1이됨 (하나의 팀에 여러 멤버가 소속되므로)
    // 그래서 멤버 입장에서 Many 팀은 One이라 ManyToOne 사용함
    // 그리고 어떤 키로 연관 되는지 필요하기 때문에 JoinColum으로 명칭 지정해줘야함
    @ManyToOne//(fetch= FetchType.LAZY) (기본값은 eager 이거) Lazy로 하면 쿼리가 한번에 안 나감
    @JoinColumn(name="TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
