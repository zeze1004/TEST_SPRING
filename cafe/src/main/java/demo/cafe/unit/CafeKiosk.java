package demo.cafe.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import demo.cafe.unit.beverage.Beverage;
import demo.cafe.unit.order.Order;
import lombok.Getter;

@Getter
public class CafeKiosk {

	private final List<Beverage> beverages = new ArrayList<>();
	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;
		for (Beverage beverage : beverages) {
			System.out.println("beverage: " + beverage);
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public Order createOrder() {
		return new Order(LocalDateTime.now(), beverages);
	}
}