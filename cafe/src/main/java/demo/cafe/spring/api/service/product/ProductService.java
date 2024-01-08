package demo.cafe.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import demo.cafe.spring.api.service.product.response.ProductResponse;
import demo.cafe.spring.domain.product.Product;
import demo.cafe.spring.domain.product.ProductRepository;
import demo.cafe.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }
}
