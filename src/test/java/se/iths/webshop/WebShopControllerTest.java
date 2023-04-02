package se.iths.webshop;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import se.iths.webshop.business.service.WebShopService;
import se.iths.webshop.ui.WebShopController;

import static org.mockito.Mockito.*;

@Controller
public class WebShopControllerTest {

    @Mock
    private WebShopService webShopService = mock(WebShopService.class);

    private WebShopController webShopController = new WebShopController();
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webShopController.setWebShopService(webShopService);
    }

    @Test
    public void shouldCallServiceToRegisterNewUser() {
        when(webShopService.registerUser(anyString(),anyString(),anyString())).thenReturn("");

        webShopController.registerUser(model,"Customer", "test@test.com", "test1");

        verify(webShopService, times(1)).registerUser(anyString(),anyString(),anyString());
    }

    @Test
    public void shouldCallServiceToRegisterNewAdmin() {
        when(webShopService.registerAdmin(anyString(),anyString(),anyString())).thenReturn("");

        webShopController.registerAdmin(model,"Admin", "admin@admin.com", "admin");

        verify(webShopService, times(1)).registerAdmin(anyString(),anyString(),anyString());
    }

    @Test
    public void shouldCallServiceToLogin() {
        when(webShopService.registerAdmin(anyString(),anyString(),anyString())).thenReturn("");

        webShopController.loginUser(model,"test@test.com", "test1");

        verify(webShopService, times(1)).loginUser(anyString(),anyString());
    }


}
