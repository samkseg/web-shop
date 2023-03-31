package se.iths.webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.webshop.business.OrderedProduct;
import se.iths.webshop.business.Product;

import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<Product> findByName(String name);
    Optional<Product>findByCategory(String category);

    Optional<Product>findById(long id);
}
