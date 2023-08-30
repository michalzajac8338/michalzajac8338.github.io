import java.util.Objects;
import java.util.Random;

public class AskTheAudience implements Lifeline{
    public static boolean used = false;
    String properABCD = Questions.currentQuestion.getProperABCD();
    public void useLifeline(){
        AskTheAudience.used = true;

        String[][] optionsPercentage = new String[4][2];
        int[] wrong = new int[3];

        Random random = new Random();
        int goodAnsPercentage = random.nextInt(30) + 50;
        wrong[0] = random.nextInt(100-goodAnsPercentage);
        wrong[1] = random.nextInt(100-goodAnsPercentage-wrong[0]);
        wrong[2] = 100-goodAnsPercentage-wrong[0]-wrong[1];

        int j = 0;
        Questions.think();
        System.out.println("The Audience has spoken!!!");
        for(int i = 0; i < 4; i++) {
            Questions.think();
            if(Objects.equals(Question.abcd[i], properABCD)){
                optionsPercentage[i] = new String[]{Question.abcd[i], goodAnsPercentage + "%"};
            }
            else {
                optionsPercentage[i] = new String[]{Question.abcd[i], wrong[j] + "%"};
                j++;
            }
            System.out.println(optionsPercentage[i][0] + ": " + optionsPercentage[i][1]);
        }
    }
}
