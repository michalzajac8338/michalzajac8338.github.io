import java.util.*;

public class FiftyFifty extends Lifeline{
    public FiftyFifty(){
        super();
    }
    public static boolean used = false;

    @Override
    public void useLifeline(){
        used = true;

        String otherAnsKey;
        String otherAnsVal;

        Random random = new Random();
        int wrongAnsRandom = random.nextInt(3);

        otherAnsKey = abcd.get(wrongAnsRandom);
        otherAnsVal = opt.get(otherAnsKey);

        System.out.println("Removing two wrong answers...\n");
        if(properAnsKey.compareTo(otherAnsKey) < 0){
            System.out.println(properAnsKey + ": " + properAnsVal);
            System.out.println(otherAnsKey + ": " + otherAnsVal);
        } else {
            System.out.println(otherAnsKey + ": " + otherAnsVal);
            System.out.println(properAnsKey + ": " + properAnsVal);
        }
    }
}
