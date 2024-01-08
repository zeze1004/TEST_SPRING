package demo.cafe.spring.domain.order;

import static demo.cafe.spring.domain.product.ProductSellingStatus.*;
import static demo.cafe.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import demo.cafe.spring.api.controller.order.request.OrderCreateRequest;
import demo.cafe.spring.api.service.order.OrderService;
import demo.cafe.spring.api.service.order.reponse.OrderResponse;
import demo.cafe.spring.domain.orderproduct.OrderProduct;
import demo.cafe.spring.domain.orderproduct.OrderProductRepository;
import demo.cafe.spring.domain.product.Product;
import demo.cafe.spring.domain.product.ProductRepository;
import demo.cafe.spring.domain.product.ProductType;

@ActiveProfiles("test")
@SpringBootTest
// @DataJpaTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRespository orderRespository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    // 각 테스트가 끝나고 데이터 클렌징을 해야 테스트간 영향이 없음
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRespository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다")
    @Test
    void createOrder() {
        // given
        LocalDateTime registerDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 3500);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "002"))
            .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request, registerDateTime); // 주문 생성 응답을 클라이언트에 넘겨주자

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
            .extracting("registerDateTime", "totalPrice") // 실제 있는 값
            .contains(registerDateTime, 4000);
        assertThat(orderResponse.getProducts()).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactly(
                tuple("001", 1000),
                tuple("002", 3000)
            );
    }

    @DisplayName("동일한 상품번호도 별개의 상품으로 계산한다.")
    @Test
    void createOrderWithDuplicateProductNumber() {
        // given
        LocalDateTime registerDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 3500);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "001"))  // 동일한 상품 주문
            .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request, registerDateTime); // 주문 생성 응답을 클라이언트에 넘겨주자

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
            .extracting("registerDateTime", "totalPrice") // 실제 있는 값
            .contains(registerDateTime, 2000);
        assertThat(orderResponse.getProducts()).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactly(
                tuple("001", 1000),
                tuple("001", 1000)
            );
    }

    // 빌더패턴이 너무 길어서 가독성을 위한 도우미 함수
    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .type(type)
            .productNumber(productNumber)
            .price(price)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .build();
    }
}
