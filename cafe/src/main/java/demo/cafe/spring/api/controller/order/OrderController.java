package demo.cafe.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.cafe.spring.api.controller.order.request.OrderCreateRequest;
import demo.cafe.spring.api.service.order.OrderService;
import demo.cafe.spring.api.service.order.reponse.OrderResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        // controller에서 시간을 넘김
        LocalDateTime registerDateTime = LocalDateTime.now();
        return orderService.createOrder(request, registerDateTime);
    }
}
