package whoWantsTBAM.GameLogic;

import java.util.*;

public class FiftyFifty extends Lifeline {
    public static boolean used = false;
    @Override
    public String[] useLifeline(){
        FiftyFifty.used = true;

        String properABCD = QuestionHandler.currentQuestion.getProperABCD();

        Random random = new Random();
        String[] wrongABCD = QuestionHandler.currentQuestion.options[random.nextInt(2)];
        String[] wrongABCD2 = QuestionHandler.currentQuestion.options[random.nextInt(2,4)];

        if(Objects.equals(wrongABCD[0], properABCD)) wrongABCD = wrongABCD2;

        return new String[]{properABCD, wrongABCD[0]};
    }
}
