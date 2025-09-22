package com.pgno209.smartwash.controller;

import com.pgno209.smartwash.model.Category;
import com.pgno209.smartwash.model.Clothes;
import com.pgno209.smartwash.model.OrderItem;
import com.pgno209.smartwash.service.ClothesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class ClothesController {
    private final ClothesService clothesService;

    public ClothesController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/browse")
    public String browse(@RequestParam(required = false) Integer categoryId, Model model, HttpSession session) {
        List<Category> categories = clothesService.getAllCategories();
        model.addAttribute("categories", categories);

        if (categoryId != null) {
            List<Clothes> clothes = clothesService.getClothesByCategory(categoryId);
            model.addAttribute("clothes", clothes);
            model.addAttribute("selectedCategoryId", categoryId);

            @SuppressWarnings("unchecked")
            List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }
            model.addAttribute("cart", cart);

            Map<Integer, Integer> cartQuantities = new HashMap<>();
            for (OrderItem item : cart) {
                cartQuantities.put(item.getClothId(), item.getQuantity());
            }
            model.addAttribute("cartQuantities", cartQuantities);
        }

        return "browse";
    }

    @PostMapping("/browse/add")
    public String addToCart(@RequestParam Integer clothId, @RequestParam Integer quantity, @RequestParam Integer categoryId, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        OrderItem existingItem = cart.stream()
                .filter(item -> item.getClothId().equals(clothId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            if (quantity > 0) {
                existingItem.setQuantity(quantity);
            } else {
                cart.remove(existingItem);
            }
        } else if (quantity > 0) {
            OrderItem item = new OrderItem();
            item.setClothId(clothId);
            item.setQuantity(quantity);
            cart.add(item);
        }

        return "redirect:/browse?categoryId=" + categoryId;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        List<Clothes> cartClothes = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : cart) {
            Clothes cloth = clothesService.getClothById(item.getClothId());
            if (cloth != null) {
                cartClothes.add(cloth);
                total = total.add(cloth.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartClothes", cartClothes);
        model.addAttribute("total", total);

        return "cart"; // looks for cart.html in templates/
    }

    @GetMapping("/cart/remove/{clothId}")
    public String removeFromCart(@PathVariable Integer clothId, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getClothId().equals(clothId));
        }
        // redirect back to the cart view
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart"); // or set to new ArrayList<>() if you prefer
        return "redirect:/cart";
    }

}