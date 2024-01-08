package demo.cafe.spring.api.service.order.reponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import demo.cafe.spring.api.service.product.response.ProductResponse;
import demo.cafe.spring.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registerDateTime;
    private List<ProductResponse> products;

    @Builder
    public OrderResponse(Long id, int totalPrice, LocalDateTime registerDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registerDateTime = registerDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .totalPrice(order.getTotalPrice())
            .registerDateTime(order.getRegisterDateTime())
            .products(order.getOrderProducts().stream()
                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                .collect(Collectors.toList())
            )
            .build();
    }
}
