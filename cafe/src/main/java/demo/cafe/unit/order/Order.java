package demo.cafe.unit.order;

import java.time.LocalDateTime;
import java.util.List;

import demo.cafe.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {
	private final LocalDateTime orderDataTime;
	private final List<Beverage> beverages;

}
