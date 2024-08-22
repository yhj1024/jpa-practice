package jpg_basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
public class Member3 {

    @Id // 기본키 지정
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

    public Member3() {

    }



}
