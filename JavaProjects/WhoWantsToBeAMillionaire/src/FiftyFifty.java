import java.util.*;

public class FiftyFifty implements Lifeline{
    public static boolean used = false;
    public void useLifeline(){
        FiftyFifty.used = true;

        String properABCD = Questions.currentQuestion.getProperABCD();
        String properAnswer = Questions.currentQuestion.getProperAnswer();

        Random random = new Random();
        String[] wrongABCD = Questions.currentQuestion.options[random.nextInt(2)];
        String[] wrongABCD2 = Questions.currentQuestion.options[random.nextInt(2,4)];

        if(Objects.equals(wrongABCD[0], properABCD)) wrongABCD = wrongABCD2;

        System.out.println("Removing two wrong answers...\n");
        Questions.think();

        if(properABCD.compareTo(wrongABCD[0]) < 0)
        {
            Questions.think();
            System.out.println(properABCD + ": " + properAnswer);
            Questions.think();
            System.out.println(wrongABCD[0] + ": " + wrongABCD[1]);
        } else {
            Questions.think();
            System.out.println(wrongABCD[0] + ": " + wrongABCD[1]);
            Questions.think();
            System.out.println(properABCD + ": " + properAnswer);
        }
    }
}
