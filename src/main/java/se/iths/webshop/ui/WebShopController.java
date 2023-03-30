package se.iths.webshop.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.webshop.business.Customer;
import se.iths.webshop.business.Employee;
import se.iths.webshop.business.Person;
import se.iths.webshop.business.WebShopService;

import java.util.Optional;

@Controller
public class WebShopController {

    @Autowired
    WebShopService webShopService;

    @GetMapping("/register")
    public String register(Model model) {
        return "reg";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String check =  webShopService.registerUser(name, email, password);
        model.addAttribute("regcheck", check);
        return "reg";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Model model, @RequestParam String email, @RequestParam String password) {
        String login = webShopService.loginUser(email, password);
        model.addAttribute("login", login);
        model.addAttribute("products", webShopService.getProducts());
        if (login == "Wrong password or email!") {
            return "login";
        } else if (login == "Admin") {
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin";
        } else {
            return "shop";
        }
    }

    @GetMapping("/shop")
    public String shopView(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.getProducts());
            return "shop";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/cart-view")
    public String cartView(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getCart());
            return "cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/logout")
    public String logoutUser(Model model) {
        String logout = webShopService.logoutUser();
        model.addAttribute("login", logout);
        return "login";
    }

    @GetMapping("admin-add")
    public String adminViewAdd(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @RequestParam String name, @RequestParam String category, @RequestParam double price) {
        if (webShopService.getUser() instanceof Employee) {
            String add = webShopService.addProduct(name, category, price);
            model.addAttribute("addResult", add);
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/remove")
    public String removeProduct(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            String remove = webShopService.removeProduct(id);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.getProducts());
            model.addAttribute("remove", remove);
            return "admin-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-view")
    public String adminViewAll(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("products", webShopService.getProducts());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }
}
