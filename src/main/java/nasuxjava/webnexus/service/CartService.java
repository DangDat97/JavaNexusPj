package nasuxjava.webnexus.service;

import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.model.CartItem;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CartService implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {

        cart.addItem(product, quantity);
    }

    public void removeFromCart(Product product) {
        cart.removeItem(product);
    }

    public void updateCart(Cart updatedCart) {
        for (CartItem updatedItem : updatedCart.getItems()) {
            for (CartItem currentItem : cart.getItems()) {
                if (currentItem.getProduct().getId().equals(updatedItem.getProduct().getId())) {
                    currentItem.setQuantity(updatedItem.getQuantity());
                }
            }
        }
    }
}
