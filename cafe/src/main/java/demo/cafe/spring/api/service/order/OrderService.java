package demo.cafe.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import demo.cafe.spring.api.controller.order.request.OrderCreateRequest;
import demo.cafe.spring.api.service.order.reponse.OrderResponse;
import demo.cafe.spring.domain.order.Order;
import demo.cafe.spring.domain.order.OrderRespository;
import demo.cafe.spring.domain.product.Product;
import demo.cafe.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 서비스로직
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRespository orderRespository; // 주문 내역 저장
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registerDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        // product 상품 번호로 조회
        List<Product> products = findProductsBy(productNumbers);

        // 주문 생성
        Order order = Order.create(products, registerDateTime);

        // 주문 저장
        Order savedOrder = orderRespository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
            .map(productMap::get)
            .toList();
    }
}
