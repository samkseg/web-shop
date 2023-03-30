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

    public String registerUser(String username, String password) {
        Optional<Person> optionalPerson = personRepository.findByName(username);
        if (optionalPerson.isEmpty()) {
                user = personRepository.save(new Customer(username, password));
                return "Account created!";
        } else return "Account already exists!";
    }

    public String loginUser(String username, String password) {
        Optional<Person> optionalPerson = personRepository.findByNameAndPassword(username, password);
        if (optionalPerson.isEmpty()) {
            return "Wrong password or email!";
        } else {
            if (optionalPerson.get().getPassword() == password) {
                user = optionalPerson.get();
            }
        }
        return "Logged in as " + optionalPerson.get().getName();
    }

    public String logoutUser() {
        Person user = null;
        return "Logged out!";
    }
}
