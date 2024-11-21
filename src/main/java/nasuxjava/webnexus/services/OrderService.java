package nasuxjava.webnexus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nasuxjava.webnexus.entity.Order;
import nasuxjava.webnexus.entity.OrderItem;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.entity.Order.OrderStatus;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.model.CartItem;
import nasuxjava.webnexus.repository.OrderRepository;
import nasuxjava.webnexus.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(items);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            item.setPrice(product.getPrice());
            item.setOrder(order);
            total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Order addCarttoOrder(Cart cart, User user) {
        Order order = new Order();
        List<OrderItem> listOrderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(item.getProduct().getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            listOrderItems.add(orderItem);
        }

        order.setOrderDate(Date.from(Instant.now()));
        order.setTotalAmount(cart.getToTalCart());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setOrderItems(listOrderItems);
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return order;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserId(user.getId());
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
