package se.iths.webshop.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.webshop.business.*;

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

    @PostMapping("/add-to-cart")
    public String addToCart(Model model, @RequestParam long id, @RequestParam int count) {
        if (webShopService.getUser() instanceof Customer) {
            webShopService.addProductToCart(id ,count);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.getProducts());
            model.addAttribute("changes", "Product added!");
            return "shop";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/update-cart")
    public String updateCartItem(Model model, @RequestParam String name, @RequestParam String category, @RequestParam double price, @RequestParam int count) {
        if (webShopService.getUser() instanceof Customer) {
            webShopService.updateCartItem(name, category, price, count);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal());
            return "cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/clear-cart")
    public String deleteCart(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("changes", webShopService.clearCart());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal());
            return "cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/confirmed")
    public String confirmed(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            CustomerOrder order = webShopService.checkout();
            ((Customer) webShopService.getUser()).addOrder(order);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal());
            model.addAttribute("message", "Order has been placed!");
            return "confirmed";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/checkout")
    public String confirm(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal());
            return "checkout";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/cart-view")
    public String cartView(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal());
            model.addAttribute("emptycart", webShopService.getCart().getItems().size() < 1);
            return "cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("orders", webShopService.getOrders());
            return "orders-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/order")
    public String orderView(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("order", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            return "order-view";
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
            return "admin-products";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-view")
    public String adminViewAll(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("products", webShopService.getProducts());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin-products";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-pending")
    public String adminViewPending(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin-pending";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }
    @PostMapping("/admin-pending-confirm")
    public String adminViewPendingConfirm(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            webShopService.confirmOrder(id);
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("message", "Order confirmed!");
            return "admin-pending";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-pending-order")
    public String adminViewPendingOrder(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("order", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            model.addAttribute("id", id);
            return "admin-pending-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-pending-cancel")
    public String adminViewPendingCancel(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            webShopService.cancelOrder(id);
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("result", "Order canceled!");
            return "admin-pending";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-process")
    public String adminViewConfirmed(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getConfirmedOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin-process";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-process-confirm")
    public String adminViewProcessConfirm(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            webShopService.processOrder(id);
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("result", "Order processed!");
            return "admin-process";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-process-order")
    public String adminViewConfirmedOrder(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("order", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            model.addAttribute("id", id);
            return "admin-process-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-dispatch")
    public String adminViewDispatched(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getDispatchedOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin-dispatch";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-dispatch-order")
    public String adminViewDispatchedOrder(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("order", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            return "admin-dispatch-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }
}
