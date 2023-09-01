package whoWantsTBAM;

import java.util.Objects;
import java.util.Random;

public class AskTheAudience implements Lifeline{
    public static boolean used = false;
    String properABCD = Questions.currentQuestion.getProperABCD();
    public String[] useLifeline(){
        AskTheAudience.used = true;

        String[] optionsPercentage = new String[4];
        int[] wrong = new int[3];

        Random random = new Random();
        int goodAnsPercentage = random.nextInt(30) + 50;
        wrong[0] = random.nextInt(100-goodAnsPercentage);
        wrong[1] = random.nextInt(100-goodAnsPercentage-wrong[0]);
        wrong[2] = 100-goodAnsPercentage-wrong[0]-wrong[1];

        int j = 0;

        for(int i = 0; i < 4; i++) {
            if(Objects.equals(Question.abcd[i], properABCD)){
                optionsPercentage[i] = goodAnsPercentage + "%";
            }
            else {
                optionsPercentage[i] = wrong[j] + "%";
                j++;
            }
        }
        return optionsPercentage;
    }
}
