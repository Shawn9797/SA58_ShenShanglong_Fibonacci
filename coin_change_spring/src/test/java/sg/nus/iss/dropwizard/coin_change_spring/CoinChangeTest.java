package sg.nus.iss.dropwizard.coin_change_spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CoinChangeTest {
    @Autowired
    private CoinChangeService service;

    @Test
    public void testCalculateChange() {
        List<Double> selectedCoins1 = List.of(0.1, 0.5, 1.0, 5.0, 10.0);
        CoinChangeRequest request1 = new CoinChangeRequest(7.3,selectedCoins1);
        String result1 = service.calculate(request1);
        assertEquals(result1, "[5.0, 1.0, 1.0, 0.1, 0.1, 0.1]");

        List<Double> selectedCoins2 = List.of(1.0, 2.0, 50.0);
        CoinChangeRequest request2 = new CoinChangeRequest(103.0,selectedCoins2);
        String result2 = service.calculate(request2);
        assertEquals(result2, "[50.0, 50.0, 2.0, 1.0]");
    }

    @Test
    public void testCalculateError() {
        List<Double> selectedCoins1 = List.of(0.1, 0.5, 1.0, 5.0, 10.0);
        CoinChangeRequest request1 = new CoinChangeRequest(-10.1,selectedCoins1);
        String result1 = service.calculate(request1);
        assertEquals(result1, "Target amount must be between 0 and 10000.");

        CoinChangeRequest request2 = new CoinChangeRequest(7.01,selectedCoins1);
        String result2 = service.calculate(request2);
        assertEquals(result2, "No possible solution!");
    }
}
