package se.iths.webshop.business;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends Person{
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomerOrder> orders;

    public Customer (){}

    public Customer (String name, String email, String password){
        super(name, email, password);
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void addOrder(CustomerOrder order) {
        orders.add(order);
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }
}
