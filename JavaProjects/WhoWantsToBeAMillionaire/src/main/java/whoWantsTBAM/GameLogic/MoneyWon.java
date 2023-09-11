package whoWantsTBAM.GameLogic;

public class MoneyWon {
    public static int index = 0;
    public static final String[] moneyToWin = {"$0","$500","$1,000","$2,000","$5,000","$10,000","$20,000","$50,000","$75,000","$150,000",
            "$250,000","$500,000","$1,000,000"};

    public static String endMoney(){
        String s = "";
        if (index<2) s = "$0 Better luck next time";
        else if (index<7) s = "for $1000 You may buy a very nice fridge (full of beer?)";
        else if (index<12) s = "$50,000!!! How are You going to spend it?";
        else if (index == 12) s = "YOU ARE OUR NEW MILLIONAIRE!!!";
        return s;
    }

    public static String resigned(){
        return "Really nice. You have won " + moneyToWin[index];
    }
}
