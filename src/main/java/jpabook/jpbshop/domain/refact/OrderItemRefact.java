package jpabook.jpbshop.domain.refact;

import javax.persistence.*;

@Entity
public class OrderItemRefact {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    //    @Column(name = "ORDER_ID")
//    private Long orderId;
    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private OrderRefact order;

    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private ItemRefact item;

    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderRefact getOrder() {
        return order;
    }

    public void setOrder(OrderRefact order) {
        this.order = order;
    }

    public ItemRefact getItem() {
        return item;
    }

    public void setItem(ItemRefact item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
