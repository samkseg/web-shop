package se.iths.webshop.business;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderLine> items;

    public CustomerOrder() {}

    public CustomerOrder(List<OrderLine> items, long userId) {
        this.items = items;
        this.userId = userId;
    }

    public String getName() {
        return "Order no. " + getId();
    }
    public double getTotalPrice() {
        double sum = 0;
        for (OrderLine orderLine : items) {
            sum = sum + orderLine.getPrice();
        }
        return sum;
    }

    public List<OrderLine> getItems() {
        return items;
    }

    public void setItems(List<OrderLine> items) {
        this.items = items;
    }

    public void addItem(OrderLine orderLine) {
        items.add(orderLine);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
