package nasuxjava.webnexus.model;

import java.math.BigDecimal;

public class CartItem {
    private Long productId;
    private int quantity;
    private BigDecimal price;

    public CartItem(Long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
