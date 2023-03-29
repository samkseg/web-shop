package se.iths.webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.webshop.business.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person>findByName(String name);
}
