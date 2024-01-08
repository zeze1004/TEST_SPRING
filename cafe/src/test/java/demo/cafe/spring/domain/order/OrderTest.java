package demo.cafe.spring.domain.order;

import static demo.cafe.spring.domain.order.OrderStatus.*;
import static demo.cafe.spring.domain.product.ProductSellingStatus.*;
import static demo.cafe.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import demo.cafe.spring.domain.product.Product;
import demo.cafe.spring.domain.product.ProductType;

class OrderTest {

    @DisplayName("상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 상태 시 초기 상태는 INIT이다.")
    @Test
    void init() {
        // given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualTo(INIT);
    }

    @DisplayName("주문 상태 시 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        LocalDateTime registerDateTime = LocalDateTime.now(); // 명시적으로 테스트를 확인하기 위해 시간을 변수로 뺌

        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, registerDateTime);

        // then
        assertThat(order.getRegisterDateTime()).isEqualTo(registerDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
            .type(HANDMADE)
            .productNumber(productNumber)
            .price(price)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .build();
    }

}
