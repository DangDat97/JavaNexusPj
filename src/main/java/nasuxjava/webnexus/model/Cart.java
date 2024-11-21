package nasuxjava.webnexus.model;

import nasuxjava.webnexus.entity.Product;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CartItem> items = new ArrayList<>();

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Product product) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(product.getId())) {
                iterator.remove();
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public BigDecimal getToTalCart() {
        BigDecimal totalCart = new BigDecimal("0");
        for (CartItem item : items) {
            totalCart = totalCart.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalCart;
    }
}