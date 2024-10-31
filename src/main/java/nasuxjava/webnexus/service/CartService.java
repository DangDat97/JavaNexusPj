package nasuxjava.webnexus.service;

import nasuxjava.webnexus.model.Cart;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void saveCart(HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void addItemToCart(HttpSession session, Long productId, int quantity, BigDecimal price) {
        Cart cart = getCart(session);
        cart.addItem(productId, quantity, price);
    }

    public void removeItemFromCart(HttpSession session, Long productId) {
        Cart cart = getCart(session);
        cart.removeItem(productId);
    }

    public BigDecimal getTotalPrice(HttpSession session) {
        Cart cart = getCart(session);
        return cart.getTotalPrice();
    }
}
