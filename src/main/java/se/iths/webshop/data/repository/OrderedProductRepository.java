package se.iths.webshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.entity.OrderedProduct;
import se.iths.webshop.business.entity.Product;

import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<Product> findByName(String name);
    Optional<Product>findByCategory(String category);

    Optional<Product>findById(long id);
}
