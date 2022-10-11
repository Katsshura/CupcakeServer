package com.katsshura.cupcake.core.services.order;

import com.katsshura.cupcake.core.dto.order.CreateItemOrderDTO;
import com.katsshura.cupcake.core.dto.order.CreateOrderDTO;
import com.katsshura.cupcake.core.dto.order.OrderResponseDTO;
import com.katsshura.cupcake.core.entities.order.OrderEntity;
import com.katsshura.cupcake.core.entities.order.OrderProductEntity;
import com.katsshura.cupcake.core.entities.order.OrderProductId;
import com.katsshura.cupcake.core.entities.order.OrderStatusEntity;
import com.katsshura.cupcake.core.enums.order.OrderStatus;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.mapper.order.OrderMapper;
import com.katsshura.cupcake.core.repositories.order.OrderProductRepository;
import com.katsshura.cupcake.core.repositories.order.OrderRepository;
import com.katsshura.cupcake.core.repositories.order.OrderStatusRepository;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import com.katsshura.cupcake.core.repositories.user.payment.UserPaymentCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final  OrderStatusRepository orderStatusRepository;
    private final  OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final UserPaymentCardRepository userPaymentCardRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(final OrderRepository orderRepository,
                        final OrderStatusRepository orderStatusRepository,
                        final OrderProductRepository orderProductRepository,
                        final UserRepository userRepository,
                        final UserPaymentCardRepository userPaymentCardRepository,
                        final ProductRepository productRepository,
                        final OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
        this.userPaymentCardRepository = userPaymentCardRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponseDTO createOrder(final CreateOrderDTO createOrder) {
        log.debug("Creating order: [{}]", createOrder);

        final var order = buildOrder(createOrder);

        final var orderResult = this.orderRepository.save(order);

        final List<OrderProductEntity> orderProducts = createOrder
                .getProductIdQuantity()
                .stream()
                .map(item -> this.extractOrderProducts(item, orderResult))
                .collect(Collectors.toList());

        this.orderProductRepository.saveAll(orderProducts);

        final var orderStatusResult = this.saveOrderStatus(orderResult);

        if (orderResult.getStatus() == null) {
            orderResult.setStatus(new ArrayList<>());
        }

        orderResult.getStatus().add(orderStatusResult);

        return this.orderMapper.toOrderResponseDto(orderResult, orderProducts);
    }


    private OrderStatusEntity saveOrderStatus(final OrderEntity orderResult) {
        final var orderStatusEntity = OrderStatusEntity.builder()
                .status(OrderStatus.CREATED)
                .active(true)
                .order(orderResult)
                .build();

        return this.orderStatusRepository.save(orderStatusEntity);
    }

    private OrderEntity buildOrder(final CreateOrderDTO orderDTO) {
        final var user = this.userRepository.findById(orderDTO.getUserId());
        final var userPayment = this.userPaymentCardRepository
                .findById(orderDTO.getPaymentId());

        if (user.isEmpty()) {
            log.error("User with id: [{}] not found when creating order!", orderDTO.getUserId());
            throw new NotFoundException(String.format("User with id: [%s]", orderDTO.getUserId()));
        }

        if (userPayment.isEmpty()) {
            log.error("Payment with id: [{}] not found when creating order!", orderDTO.getUserId());
            throw new NotFoundException(String.format("Payment with id: [%s]", orderDTO.getUserId()));
        }

        return OrderEntity
                .builder()
                .user(user.get())
                .userPaymentId(userPayment.get())
                .build();
    }

    private OrderProductEntity extractOrderProducts(final CreateItemOrderDTO productIdQuantity,
                                                    final OrderEntity order) {
        final var product = this.productRepository.findById(productIdQuantity.getProductId());

        if (product.isEmpty()) {
            log.error("Product with id: [{}] not found when creating order!", productIdQuantity.getProductId());
            throw new NotFoundException(String.format("Product with id: [%s]", productIdQuantity.getProductId()));
        }

        return OrderProductEntity.builder()
                .id(OrderProductId.builder().orderId(order.getId()).productId(product.get().getId()).build())
                .order(order)
                .product(product.get())
                .quantity(productIdQuantity.getQuantity())
                .totalPrice(product.get().getPrice().multiply(new BigDecimal(productIdQuantity.getQuantity())))
                .build();
    }
}
