package se.iths.webshop.business;

import jakarta.persistence.*;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
    int count;

    public OrderLine() {}

    public OrderLine(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return count + " " + product.getName() + " " + getPrice();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return product.getPrice() * count;
    }
}
