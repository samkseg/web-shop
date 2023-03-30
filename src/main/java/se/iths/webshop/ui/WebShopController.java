package se.iths.webshop.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.webshop.business.WebShopService;

@Controller
public class WebShopController {

    @Autowired
    WebShopService webShopService;

    @GetMapping("/register")
    public String register(Model model) {
        return "reg";
    }

    @PostMapping("/register")
    public String registerSubmit(Model model, @RequestParam String username, @RequestParam String password) {
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
        model.addAttribute("loginResult", login);
        if (login == "Wrong password or email!") {
            return "login";
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
}
