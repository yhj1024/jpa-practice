package jpg_basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

//@Entity
@SequenceGenerator(
        name="MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue=1, allocationSize=1
) // 시퀀스를 직접 만들 수도 있다
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCE",
//        pkColumnValue = "MEMBER_SEQ",
//        allocationSize = 1
//) // 시퀀스 테이블 생성도 가능
public class Member4 {

     // 기본키 지정
    /*
        직접 할당 @Id 만 사용
        자동 생성 @GeneratedValue
            시퀀스 전략
            GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임 예) Mysql AutoIncrement
            GenerationType.AUTO : 방언에 맞춰 자동 생성 (String 으로 하면 안된다 Long 같은 타입 필요)
            GenerationType.SEQUENCE : 오라클 같은 데이터 베이스에서 사용, 테이블 만들 때 시퀀스를 만들고 처리하게 된다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "MEMBER_SEQ")
    private Long id;

    @Column(name="name") // name db필드명
    /*
        insertable : boolean (기본 true) : 등록 가능 여부 false 로 두면 엔티티 저장 시 insert 안됨
        updatable : boolean (기본 true) : 수정 가능 여부 false 두면 엔티티 수정 시 update 안됨
        nullable (DDL) : boolean (기본 true) : false 로 하면 not null 제약 조건이 걸리게 된다
        unique (DDL) : boolean (기본 true) : 잘 안씀 제약 조건 명칭이 이상하게 나옴, Table 어노테이션으로 이름 설정해서 많이씀
        columnDefinition (DDL) : string : varchar(100) default 'EMPTY' 테이터 베이스 컬럼 정보를 직접 줄 수 있음
        length (DDL) : 길이 제약 조건, String 타입에만 사용한다.
        precision, scale (DDL) : BigDecimal (BigInteger) 에서 사용 percision은 소수점을 포함한 전체 자릿수를, scale은 소수인 자릿수를 사용
     */
    private String username;

    @Column(precision = 10)
    private BigDecimal test1;

    @Column(scale = 5)
    private BigInteger test2;

    private Integer age;

    @Enumerated(EnumType.STRING) // ENUM 이 VARCHAR와 매핑됨
    /*
        ORDINAL 과 STRING 타입이 있는데
        ORDINAL은 순서를 데이터 베이스에 저장한다 (순서에 따라 0,1,2 가 값으로 들어감)
        STRING 은 NAME을 저장하게 된다
        ORDINAL은 사용을 안 하는게 좋다
        왜냐면 순서가 저장 되기 때문에 나중에 뭔가 추가되거나 할 때 순서가 잘못되면 다 꼬일 수 있다
     */
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //
    /*
        LocalDate (Date 타입으로 생성됨), LocalDateTime(Timestamp로 생성됨) 으로 요새는 다 지원함
        TemporalType 을 사용하면
        TemporalType.DATE : 날짜, date 타입과 매핑
        TemporalType.TIME : 시간, time 타입 매핑
        TemporalType.TIMESTAMP : 날짜와 시간,  timestamp와 매핑
     */
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob // String이면 CLOB 으로 생성 외에는 BLOB으로 생성
    private String description;

    @Transient // 물리적으로 DB 필드 생성 안됨 메모리 상에서만 사용
    private int temp;

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

    public BigDecimal getTest1() {
        return test1;
    }

    public void setTest1(BigDecimal test1) {
        this.test1 = test1;
    }

    public BigInteger getTest2() {
        return test2;
    }

    public void setTest2(BigInteger test2) {
        this.test2 = test2;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public Member4() {

    }



}
