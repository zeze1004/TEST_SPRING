package demo.cafe.unit.beverage;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

class AmericanoTest {

	@Test
	void getName() {
		Americano americano = new Americano();

		// assertEquals(americano.getName(), "아메리카노"); // JUnit API
		assertThat(americano.getName()).isEqualTo("아메리카노"); // AssertThat API
	}

	@Test
	void getPrice() {
		Americano americano = new Americano();

		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}