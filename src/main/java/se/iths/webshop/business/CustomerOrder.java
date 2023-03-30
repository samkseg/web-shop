package se.iths.webshop.business;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderLine> items;

    public CustomerOrder() {}

    public CustomerOrder(List<OrderLine> itemsList) {
        this.items = itemsList;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
