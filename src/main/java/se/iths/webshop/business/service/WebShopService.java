package se.iths.webshop.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webshop.business.entity.*;
import se.iths.webshop.data.*;
import se.iths.webshop.data.repository.OrderRepository;
import se.iths.webshop.data.repository.OrderedProductRepository;
import se.iths.webshop.data.repository.PersonRepository;
import se.iths.webshop.data.repository.ProductRepository;

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

    @Autowired
    OrderedProductRepository orderedProductRepository;

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

    public CustomerOrder getOrder(long id) {
        Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (!categories.contains(product.getCategory())) {
                categories.add(product.getCategory());
            }
        }
        return categories;
    }

    public List<Product> searchProduct(String text) {
        return productRepository.findAll().stream().filter(product -> product.getName().toLowerCase().contains(text.toLowerCase())).toList();
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findAll().stream().filter(product -> product.getCategory().contains(category)).toList();
    }

    public Person getUser() {
        return user;
    }

    public Cart getCart() {
        return cart;
    }

    public String registerAdmin(String name, String email, String password) {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalPerson.isEmpty()) {
            user = personRepository.save(new Employee(name, email, password));
            return "Account created!";

        } else return "Account already exists!";
    }

    public String registerUser(String name, String email, String password) {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
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

    public String addProductToCart(long id, int count) {
        Product product = getProduct(id);
        getCart().getItems().add(new OrderLine(product, count));
        return product.getName() + " added to cart!";
    }

    public void updateCartItem(String name, String category, double price, int count) {
        int index = cart.findByNameAndCategory(name, category, price, count );
        if (count == 0) {
            getCart().getItems().remove(index);
        } else {
            getCart().getItems().get(index).setCount(count);
        }
    }

    public double getCartTotal() {
        double sum = 0;
        for (OrderLine orderLine : getCart().getItems()) {
            sum = sum + orderLine.getPrice();
        }
        return sum;
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
        return name + " added!";
    }

    public String removeProduct(long id) {
        productRepository.delete(productRepository.findById(id).get());
        return "Product " + id + " was deleted!";
    }

    public CustomerOrder checkout() {;
        ArrayList list = new ArrayList<>();
        for (OrderLine orderLine : cart.getItems()) {
            Product product = orderLine.getProduct();
            OrderedProduct orderedProduct = new OrderedProduct(product.getName(), product.getCategory(), product.getPrice());
            OrderItem orderItem = new OrderItem(orderedProduct, orderLine.getCount());
            list.add(orderItem);
            orderedProductRepository.save(orderedProduct);
        }
        CustomerOrder order = new CustomerOrder();
        order.setItems(list);
        order.setUserId(user.getId());
        orderRepository.save(order);
        return order;
    }

    public List<CustomerOrder> getPendingOrders() {
        return orderRepository.findAll().stream().filter(order -> !order.isConfirmed() && !order.isProcessed() && !order.isCanceled()).toList();
    }

    public List<CustomerOrder> getConfirmedOrders() {
        return orderRepository.findAll().stream().filter(order -> order.isConfirmed() && !order.isProcessed() && !order.isCanceled()).toList();
    }

    public List<CustomerOrder> getDispatchedOrders() {
        return orderRepository.findAll().stream().filter(order -> !order.isConfirmed() && order.isProcessed() && !order.isCanceled()).toList();
    }

    public List<CustomerOrder> getCanceledOrders() {
        return orderRepository.findAll().stream().filter(order -> !order.isConfirmed() && !order.isProcessed() && order.isCanceled()).toList();
    }

    public void confirmOrder(long id) {
        CustomerOrder order = getOrder(id);
        order.setProcessed(false);
        order.setConfirmed(true);
        orderRepository.save(order);

    }

    public void processOrder(long id) {
        CustomerOrder order = getOrder(id);
        order.setConfirmed(false);
        order.setProcessed(true);
        orderRepository.save(order);

    }

    public void cancelOrder(long id) {
        CustomerOrder order = getOrder(id);
        order.setConfirmed(false);
        order.setProcessed(false);
        order.setCanceled(true);
        orderRepository.save(order);

    }
}
