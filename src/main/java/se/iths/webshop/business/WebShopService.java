package se.iths.webshop.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webshop.data.OrderRepository;
import se.iths.webshop.data.PersonRepository;
import se.iths.webshop.data.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class WebShopService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    public Cart cart;

    Person user;

    public WebShopService() {
        cart = new Cart();
    }

    public List<Person> getUsers() {
        return personRepository.findAll();
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<CustomerOrder> getOrders() {
        return orderRepository.findByUserId(user.getId());
    }

    public List<OrderLine> getOrderLines() {
        return cart.getItems();
    }

    public Product getProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public OrderLine getOrderLine(long id) {
        Optional<OrderLine> optionalOrderLine = cart.findById(id);
        return optionalOrderLine.orElse(null);
    }

    public CustomerOrder getOrder(long id) {
        Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public Person getUser() {
        return user;
    }

    public Cart getCart() {
        return cart;
    }

    public String registerUser(String name, String email, String password) {
        Optional<Person> optionalPerson = personRepository.findByName(name);
        if (optionalPerson.isEmpty()) {
                user = personRepository.save(new Customer(name, email, password));
                return "Account created!";

        } else return "Account already exists!";
    }

    public String loginUser(String email, String password) {
        Optional<Person> optionalPerson = personRepository.findByEmailAndPassword(email, password);
        if (optionalPerson.isEmpty()) {
            return "Wrong password or email!";
        } else {
            if (optionalPerson.get().getPassword().equals(password)) {
                user = optionalPerson.get();
            }
        }
        if (optionalPerson.get() instanceof Employee) {
            return "Admin";
        }
        return optionalPerson.get().getName();
    }

    public void addProductToCart(long id, int count) {
        Product product = getProduct(id);
        getCart().getItems().add(new OrderLine(product, count));
    }

    public void updateCartItem(long id, int count) {
        Optional<OrderLine> orderLine = cart.findById(id);
        if (orderLine.isPresent()) {
            if (count == 0) {
                getCart().getItems().remove(orderLine.get());
            } else {
                orderLine.get().setCount(count);
            }
        }
    }

    public String clearCart(){
        cart.getItems().clear();
        return "Cart emptied";
    }
    public String logoutUser() {
        user = null;
        return "Logged out!";
    }

    public String addProduct(String name, String category, double price) {
        productRepository.save(new Product(name, category, price));
        return "Product added!";
    }

    public String removeProduct(long id) {
        productRepository.delete(productRepository.findById(id).get());
        return "Product " + id + " was deleted!";
    }

    public CustomerOrder checkout() {;

        ArrayList list = new ArrayList<>();
        for (OrderLine orderLine : cart.getItems()) {
            OrderItem orderItem = new OrderItem(orderLine.getProduct(), orderLine.getCount());
            list.add(orderItem);
        }
        CustomerOrder order = new CustomerOrder();
        order.setItems(list);
        order.setUserId(user.getId());
        orderRepository.save(order);
        return order;
    }
}
