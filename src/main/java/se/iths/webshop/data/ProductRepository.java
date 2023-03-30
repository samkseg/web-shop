package se.iths.webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product>findByCategory(String category);

    Optional<Product>findById(long id);

}
