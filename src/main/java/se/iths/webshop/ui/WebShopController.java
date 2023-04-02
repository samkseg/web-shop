package se.iths.webshop.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.webshop.business.entity.Customer;
import se.iths.webshop.business.entity.CustomerOrder;
import se.iths.webshop.business.entity.Employee;
import se.iths.webshop.business.service.WebShopService;

@Controller
public class WebShopController {

    @Autowired
    WebShopService webShopService;

    public void setWebShopService(WebShopService webShopService) {
        this.webShopService = webShopService;
    }

    @GetMapping("/register-user")
    public String register(Model model) {
        return "reg";
    }

    @PostMapping("/register-user-submit")
    public String registerUser(Model model, @RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String check =  webShopService.registerUser(name, email, password);
        model.addAttribute("regcheck", check);
        return "reg";
    }

    @GetMapping("/register-admin")
    public String registerAdmin(Model model) {
        return "admin/admin-reg";
    }

    @PostMapping("/register-admin-submit")
    public String registerAdmin(Model model, @RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String check =  webShopService.registerAdmin(name, email, password);
        model.addAttribute("regcheck", check);
        return "admin/admin-reg";
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
            return "admin/admin-add";
        } else {
            model.addAttribute("categories", webShopService.getCategories());
            return "shop/category";
        }
    }

    @GetMapping("/search")
    public String searchView(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.getProducts());
            return "shop/search";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam String text) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.searchProduct(text));
            model.addAttribute("text", text);
            return "shop/search";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/product")
    public String product(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("product", webShopService.getProduct(id));
            return "shop/product-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/category")
    public String category(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("categories", webShopService.getCategories());
            model.addAttribute("products", webShopService.getProducts());
            return "shop/category";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @RequestMapping("/category/{category}")
    public String category(Model model, @PathVariable("category") String category) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("categories", webShopService.getCategories());
            model.addAttribute("products", webShopService.findByCategory(category));
            return "shop/category";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/add-item")
    public String addToCart(Model model, @RequestParam long id, @RequestParam int count, @RequestParam String searched) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("products", webShopService.searchProduct(searched));
            model.addAttribute("message", webShopService.addProductToCart(id ,count));
            return "shop/search";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/add-item-category")
    public String addToCartFromCategory(Model model, @RequestParam long id, @RequestParam int count, @RequestParam String selected) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("categories", webShopService.getCategories());
            model.addAttribute("products", webShopService.findByCategory(selected));
            model.addAttribute("message", webShopService.addProductToCart(id ,count));
            return "shop/category";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }
    @PostMapping("/update-cart")
    public String updateCartItem(Model model, @RequestParam String name, @RequestParam int count) {
        if (webShopService.getUser() instanceof Customer) {
            webShopService.updateCartItem(name, count);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal() + " SEK");
            model.addAttribute("emptycart", webShopService.getCart().getItems().size() < 1);
            return "shop/cart-view";
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
            model.addAttribute("total", "Total: " + webShopService.getCartTotal() + " SEK");
            model.addAttribute("emptycart", webShopService.getCart().getItems().size() < 1);
            return "shop/cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/confirmed")
    public String confirmed(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            CustomerOrder order = webShopService.checkout();
            ((Customer) webShopService.getUser()).addOrder(order);
            webShopService.clearCart();
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", order.getItems());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal() + " SEK");
            model.addAttribute("message", "Order has been placed!");
            return "shop/confirmed";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/checkout")
    public String confirm(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal() + " SEK");
            return "shop/checkout";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/cart")
    public String cartView(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("items", webShopService.getOrderLines());
            model.addAttribute("total", "Total: " + webShopService.getCartTotal() + " SEK");
            model.addAttribute("emptycart", webShopService.getCart().getItems().size() < 1);
            return "shop/cart-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("orders", webShopService.getOrders());
            return "shop/orders-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/order")
    public String orderView(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Customer) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("id", id);
            model.addAttribute("name", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            model.addAttribute("cancel", webShopService.getOrder(id).isCanceled());
            return "shop/order-view";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/order-cancel")
    public String orderCancel(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Customer) {
            webShopService.cancelOrder(id);
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("id", id);
            model.addAttribute("name", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            model.addAttribute("cancel", webShopService.getOrder(id).isCanceled());
            return "shop/order-view";
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
            return "admin/admin-add";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @RequestParam String name, @RequestParam String category, @RequestParam double price, @RequestParam String description) {
        if (webShopService.getUser() instanceof Employee) {
            String add = webShopService.addProduct(name, category, price, description);
            model.addAttribute("addResult", add);
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-add";
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
            return "admin/admin-products";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-view")
    public String adminViewAll(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("products", webShopService.getProducts());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-products";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-pending")
    public String adminViewPending(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-pending";
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
            return "admin/admin-pending";
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
            return "admin/admin-pending-order";
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
            model.addAttribute("message", "Order canceled!");
            return "admin/admin-pending";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-process")
    public String adminViewConfirmed(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getConfirmedOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-process";
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
            model.addAttribute("message", "Order processed!");
            return "admin/admin-process";
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
            return "admin/admin-process-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-process-cancel")
    public String adminViewConfirmedCancel(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            webShopService.cancelOrder(id);
            model.addAttribute("orders", webShopService.getPendingOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("message", "Order has been canceled!");
            return "admin/admin-process";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-dispatch")
    public String adminViewDispatched(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getDispatchedOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-dispatch";
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
            return "admin/admin-dispatch-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @GetMapping("/admin-cancel")
    public String adminViewCanceled(Model model) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("orders", webShopService.getCanceledOrders());
            model.addAttribute("login", webShopService.getUser().getName());
            return "admin/admin-cancel";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }

    @PostMapping("/admin-cancel-order")
    public String adminViewCanceledOrder(Model model, @RequestParam long id) {
        if (webShopService.getUser() instanceof Employee) {
            model.addAttribute("login", webShopService.getUser().getName());
            model.addAttribute("order", webShopService.getOrder(id).getName());
            model.addAttribute("items", webShopService.getOrder(id).getItems());
            model.addAttribute("total", webShopService.getOrder(id).getTotalPrice());
            model.addAttribute("confirm", webShopService.getOrder(id).isConfirmed());
            model.addAttribute("process", webShopService.getOrder(id).isProcessed());
            return "admin/admin-cancel-order";
        }
        model.addAttribute("login", "Please log in first");
        return "login";
    }
}
