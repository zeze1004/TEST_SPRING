package demo.cafe.unit;

import demo.cafe.unit.beverage.Americano;
import demo.cafe.unit.beverage.Latte;

public class CafeKioskRunner {
	public static void main(String[] args) {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		System.out.println(">>> 아메리카노 추가");

		cafeKiosk.add(new Latte());
		System.out.println(">>> 라떼 추가");

		int totalPrice = cafeKiosk.calculateTotalPrice();
		System.out.println("총 금액: " + totalPrice);

	}
}
