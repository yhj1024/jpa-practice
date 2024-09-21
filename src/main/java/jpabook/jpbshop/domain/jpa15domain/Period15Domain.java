package jpabook.jpbshop.domain.jpa15domain;

import net.bytebuddy.asm.Advice;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period15Domain {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // isWork같은 메서드를 만들어 여기서 응집성 있게 사용

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Period15Domain() {
    }
}
