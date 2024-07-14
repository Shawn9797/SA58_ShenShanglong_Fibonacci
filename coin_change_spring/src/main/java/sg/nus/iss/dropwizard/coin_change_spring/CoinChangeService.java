package sg.nus.iss.dropwizard.coin_change_spring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinChangeService {

    public String calculate(CoinChangeRequest request) {
        double targetAmount = request.getTargetMoney();
        List<Double> denominations = request.getSelectedCoins();

        String responseStr;
        if (targetAmount < 0 || targetAmount > 10000.0) {
            responseStr = "Target amount must be between 0 and 10000.";
            return responseStr;
        }

        List<Double> coins = getMinCoins(targetAmount,denominations);

        if (!coins.isEmpty()) {
            coins.sort(Collections.reverseOrder());
            responseStr = coins.toString();
        } else {
            responseStr = "No possible solution!";
        }
        return responseStr;
    }

    private List<Double> getMinCoins(double targetMoney, List<Double> denominations) {
        List<Double> minCoins = new ArrayList<>();

        //Dynamic programming
        int target = (int)Math.round(targetMoney * 100);
        int[] dp = new int[target + 1]; //store the min number of coins
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;

        int[] lastCoin = new int[target + 1]; //store the last used coin
        List<Integer> coinValues = denominations.stream()
                .map( coinValue -> (int)Math.round(coinValue * 100))
                .collect(Collectors.toList());

        for (int i = 0; i <= target; i++) {
            for (Integer coinValue : coinValues) {
                if (i >= coinValue && dp[i - coinValue] != Integer.MAX_VALUE) {
                    if (dp[i] > dp[i - coinValue] + 1) {
                        dp[i] = dp[i - coinValue] + 1;
                        lastCoin[i] = coinValue;
                    }
                }
            }
        }

        if (dp[target] == Integer.MAX_VALUE) {
            //No possible solutions
            return minCoins;
        } else {
            //iteratively do (target - lastCoinValue) to get all tht coins
            int curValue = target;
            while (curValue > 0) {
                int coin = lastCoin[curValue];
                minCoins.add(coin/100.0);
                curValue -= coin;
            }
            return minCoins;
        }
    }
}
