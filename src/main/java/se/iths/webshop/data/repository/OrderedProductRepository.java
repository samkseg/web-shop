package se.iths.webshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.entity.OrderedProduct;

import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<OrderedProduct> findByName(String name);
    Optional<OrderedProduct>findByCategory(String category);

    Optional<OrderedProduct>findById(long id);

    Optional<OrderedProduct> findByNameAndCategoryAndPrice(String name, String category, Double price);
}
