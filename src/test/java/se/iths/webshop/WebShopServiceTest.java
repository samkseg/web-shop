package se.iths.webshop;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.iths.webshop.business.entity.Customer;
import se.iths.webshop.business.entity.Employee;
import se.iths.webshop.business.entity.Person;
import se.iths.webshop.business.service.WebShopService;
import se.iths.webshop.data.repository.OrderRepository;
import se.iths.webshop.data.repository.OrderedProductRepository;
import se.iths.webshop.data.repository.PersonRepository;
import se.iths.webshop.data.repository.ProductRepository;
import se.iths.webshop.data.repository.implementation.OrderRepositoryImplementation;
import se.iths.webshop.data.repository.implementation.OrderedProductRepositoryImplementation;
import se.iths.webshop.data.repository.implementation.PersonRepositoryImplementation;
import se.iths.webshop.data.repository.implementation.ProductRepositoryImplementation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebShopServiceTest {

    @Mock
    private PersonRepository personRepository = mock(PersonRepositoryImplementation.class);
    @Mock
    private ProductRepository productRepository = mock(ProductRepositoryImplementation.class);
    @Mock
    private OrderRepository orderRepository = mock(OrderRepositoryImplementation.class);
    @Mock
    private OrderedProductRepository orderedProductRepository = mock(OrderedProductRepositoryImplementation.class);
    private WebShopService webShopService = new WebShopService(personRepository, productRepository, orderRepository, orderedProductRepository);


    @Test
    public void shouldRegisterCustomer() {
        String registration = webShopService.registerUser("Customer", "test@test.com", "test1");

        assertEquals("Account created!", registration);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldRegisterEmployee() {
        String registration = webShopService.registerUser("Admin", "admin@admin.com", "admin");

        assertEquals("Account created!", registration);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldLoginCustomer() {
        Customer customer = new Customer("Customer", "test@test.com", "test1");

        when(personRepository.findByEmailAndPassword("test@test.com", "test1")).thenReturn(Optional.of(customer));
        webShopService.loginUser("test@test.com", "test1");

        assertEquals(customer, webShopService.getUser());
    }

    @Test
    public void shouldLoginEmployee() {
        Employee employee = new Employee("Admin", "admin@admin.com", "admin");

        when(personRepository.findByEmailAndPassword("admin@admin.com", "admin")).thenReturn(Optional.of(employee));
        webShopService.loginUser("admin@admin.com", "admin");

        assertEquals(employee, webShopService.getUser());
    }
}