package nasuxjava.webnexus.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import nasuxjava.webnexus.entity.OrderItem;
import nasuxjava.webnexus.repository.OrderItemRepository;
import nasuxjava.webnexus.services.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    };

    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    };

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    };

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);

    };

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAll();
    };

    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return orderItemRepository.findAll();
    };

}
