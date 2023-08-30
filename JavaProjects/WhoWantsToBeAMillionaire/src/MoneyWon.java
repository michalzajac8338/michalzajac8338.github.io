public class MoneyWon {
    public static int index = 0;
    public static final String[] moneyToWin = {"$0","$500","$1,000","$2,000","$5,000","$10,000","$20,000","$50,000","$75,000","$150,000",
            "$250,000","$500,000","$1,000,000"};
    public static void currentMoney(){
        System.out.println("You are at: " + moneyToWin[index]);
    }

    public static void gameLostMoneyWon(){
        if (index<2) System.out.println("Better luck next time");
        else if (index<7) System.out.println("for $1000 You may buy a very nice fridge (full of beer?)");
        else if (index<12) System.out.println("$50,000!!! How are You going to spend it?");
    }

    public static void resigned(){
        Questions.think();
        System.out.println("Really nice. You have won "+moneyToWin[index]);
    }
}
