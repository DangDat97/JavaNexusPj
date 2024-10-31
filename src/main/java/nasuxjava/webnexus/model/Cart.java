package nasuxjava.webnexus.model;

import java.util.HashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.util.List;

import java.util.ArrayList;

public class Cart {
    private Map<Long, CartItem> items = new HashMap<>(); // productId, CartItem

    public void addItem(Long productId, int quantity, BigDecimal price) {
        CartItem item = items.get(productId);
        if (item == null) {
            item = new CartItem(productId, quantity, price);
            items.put(productId, item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    public void removeItem(Long productId) {
        items.remove(productId);
    }

    public Map<Long, CartItem> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return items.values().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getProducts() {
        return new ArrayList<>(items.values()); // Trả về danh sách các CartItem
    }
}