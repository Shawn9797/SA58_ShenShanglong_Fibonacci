package sg.nus.iss.dropwizard.coin_change_spring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CoinChangeRequest {
    private double targetMoney;
    private List<Double> selectedCoins;

    public CoinChangeRequest() {

    }
    public CoinChangeRequest(double targetMoney,List<Double> selectedCoins) {
        this.targetMoney = targetMoney;
        this.selectedCoins = selectedCoins;
    }

    public double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public List<Double> getSelectedCoins() {
        return selectedCoins;
    }

    public void setSelectedCoins(List<Double> selectedCoins) {
        this.selectedCoins = selectedCoins;
    }
}
