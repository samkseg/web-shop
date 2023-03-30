package se.iths.webshop.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webshop.data.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class WebShopService {
    @Autowired
    PersonRepository personRepository;
    Person user;

    public WebShopService() {}

    public List<Person> getUsers() {
        return personRepository.findAll();
    }

    public String registerUser(String userLogin, String password) {
        Optional<Person> optionalPerson = personRepository.findByName(userLogin);
        if (optionalPerson.isEmpty()) {
                user = personRepository.save(new Customer(userLogin, password));
                return "Account created";
        } else return "Account already exists";
    }
}
