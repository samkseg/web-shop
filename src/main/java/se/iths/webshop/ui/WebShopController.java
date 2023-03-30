package se.iths.webshop.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String registerUser(Model model, @RequestParam String username, @RequestParam String password) {
        String check =  webShopService.registerUser(username, password);
        model.addAttribute("regcheck", check);
        return "reg";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Model model, @RequestParam String username, @RequestParam String password) {
        String login = webShopService.loginUser(username, password);
        Person checkUserType = webShopService.getUser();
        model.addAttribute("loginResult", login);
        model.addAttribute("products", webShopService.getProducts());
        if (login == "Wrong password or email!") {
            return "login";
        } else if (login == "admin") {
            return "admin";
        } else {
            return "shop";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(Model model) {
        String logout = webShopService.logoutUser();
        model.addAttribute("loginResult", logout);
        return "login";
    }

    @GetMapping("admin-add")
    public String adminViewAdd(Model model) {
        return "admin";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @RequestParam String name, @RequestParam String category, @RequestParam double price) {
        String add = webShopService.addProduct(name, category, price);
        model.addAttribute("addResult", add);
        return "admin";
    }

    @PostMapping("/remove")
    public String removeProduct(Model model, @RequestParam long id) {
        String remove = webShopService.removeProduct(id);
        model.addAttribute("products", webShopService.getProducts());
        model.addAttribute("removeResult", remove);
        return "admin-view";
    }

    @GetMapping("/admin-view")
    public String adminViewAll(Model model) {
        model.addAttribute("products", webShopService.getProducts());
        return "admin-view";
    }
}
