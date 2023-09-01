package whoWantsTBAM;

import java.util.*;

public class FiftyFifty implements Lifeline{
    public static boolean used = false;
    public String[] useLifeline(){
        FiftyFifty.used = true;

        String properABCD = Questions.currentQuestion.getProperABCD();

        Random random = new Random();
        String[] wrongABCD = Questions.currentQuestion.options[random.nextInt(2)];
        String[] wrongABCD2 = Questions.currentQuestion.options[random.nextInt(2,4)];

        if(Objects.equals(wrongABCD[0], properABCD)) wrongABCD = wrongABCD2;

        return new String[]{properABCD, wrongABCD[0]};

    }
}
