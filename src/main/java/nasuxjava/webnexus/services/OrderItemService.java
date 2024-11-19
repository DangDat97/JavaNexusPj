package nasuxjava.webnexus.services;

import java.util.List;
import java.util.Optional;

import nasuxjava.webnexus.entity.OrderItem;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    Optional<OrderItem> getOrderItemById(Long id);

    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    List<OrderItem> getOrderItemsByProductId(Long productId);
}
