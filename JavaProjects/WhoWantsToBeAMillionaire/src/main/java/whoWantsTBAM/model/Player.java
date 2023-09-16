package whoWantsTBAM.model;

import whoWantsTBAM.GameLogic.MoneyWon;

public class Player {

    public int place;
    public final String name;
    public int result;
    public String resultMoney;
    public Player(String name){
        this.name = name;
    }

    public Player(int place, String name, int result){
        this.name = name;
        this.result = result;
        this.place = place;
        this.resultMoney = MoneyWon.moneyToWin[result];
    }
    public void setResult(){
        this.result = MoneyWon.index;
    }

    public String getName() {
        return name;
    }
    public int getResult() {
        return result;
    }
    public String getResultMoney() {
        return resultMoney;
    }
    public int getPlace() {
        return place;
    }
    @Override
    public String toString(){
        return this.place + ". " + this.name + " " + this.result;
    }
}
