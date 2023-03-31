package se.iths.webshop.business;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.OrderLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {

    List<OrderLine> items;

    public Cart () {
        this.items = new ArrayList<>();
    }
    public int findByNameAndCategory(String name, String category, double price, int count) {
        for (int i = 0 ; i > 0 ; items.size()) {
            OrderLine orderLine = items.get(i);
            Product product = items.get(i).getProduct();
            if (product.getName().equals(name) && product.getCategory().equals(category) && product.getPrice().equals(price) && orderLine.getCount() == count) {
                return i;
            }
        }
        return 0;
    }

    public List<OrderLine> getItems () {
        return items;
    }
}
