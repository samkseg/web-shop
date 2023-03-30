package se.iths.webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.OrderLine;

import java.util.Optional;

public interface Cart extends JpaRepository<OrderLine, Long> {
    Optional<OrderLine>findById(long id);
}
