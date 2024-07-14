package sg.nus.iss.dropwizard.coin_change_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/coinChange")
public class CoinChangeController {
    @Autowired
    private CoinChangeService service;

    private static final List<Double> denominationList = List.of(
            0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0,
            5.0, 10.0, 50.0, 100.0, 1000.0
    );

    @GetMapping()
    public List<Double> getDenominationList() {
        return denominationList;
    }

    @PostMapping("/calculate")
    public String calculateCoinChange(@RequestBody CoinChangeRequest request) {
        return service.calculate(request);
    }
}
