package se.iths.webshop.data;

import se.iths.webshop.business.entity.OrderLine;
import se.iths.webshop.business.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {

    List<OrderLine> items;

    public Cart () {
        this.items = new ArrayList<>();
    }

    public Cart (List<OrderLine> list) {
        this.items = new ArrayList<>();
        items.addAll(list);
    }
    public Optional<OrderLine> findByName(String name) {
        for (OrderLine orderLine : items) {
            Product product = orderLine.getProduct();
            if (product.getName().toLowerCase().equals(name.toLowerCase())) {
                return Optional.of(orderLine);
            }
        }
        return Optional.empty();
    }

    public Optional<OrderLine> findByProduct(Product product) {
        for (OrderLine orderLine : items) {
            Product foundProduct = orderLine.getProduct();
            if (product.equals(foundProduct)) {
                return Optional.of(orderLine);
            }
        }
        return Optional.empty();
    }

    public Optional<Product> addProduct(Product product, int count) {
        Optional<OrderLine> orderLine = findByProduct(product);
        if (orderLine.isEmpty()) {
            getItems().add(new OrderLine(product, count));
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public void updateCartItem(String name, int count) {
        Optional<OrderLine> orderLine = findByName(name);
        if (orderLine.isPresent()) {
            if (count == 0) {
                getItems().remove(orderLine.get());
            } else {
                orderLine.get().setCount(count);
            }
        }
    }

    public double getTotal() {
        double sum = 0;
        for (OrderLine orderLine : getItems()) {
            sum = sum + orderLine.getPrice();
        }
        return sum;
    }

    public List<OrderLine> getItems () {
        return items;
    }
}
