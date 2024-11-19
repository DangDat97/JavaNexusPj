package nasuxjava.webnexus.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nasuxjava.webnexus.entity.Product;

@Setter
@Getter
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
