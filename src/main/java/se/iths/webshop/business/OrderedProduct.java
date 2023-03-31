package se.iths.webshop.business;

import jakarta.persistence.*;

@Entity
public class OrderedProduct {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = false)
private Long id;
    private String name;
    private String category;
    private Double price;

    public OrderedProduct() {}

    public OrderedProduct(String name, String category, Double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }



}
