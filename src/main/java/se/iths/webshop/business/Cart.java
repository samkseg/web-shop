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
    public Optional<OrderLine>findById(long id) {
        Optional<OrderLine> optionalOrderLine = Optional.empty();
        for (OrderLine orderLine : items) {
            if (orderLine.getId() == id) {
                optionalOrderLine = Optional.of(orderLine);
            }
        }
        return optionalOrderLine;
    }

    public List<OrderLine> getItems () {
        return items;
    }
}
