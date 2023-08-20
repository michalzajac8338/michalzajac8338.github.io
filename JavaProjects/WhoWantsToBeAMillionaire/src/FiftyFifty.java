import java.util.*;

public class FiftyFifty implements Lifeline{
    public static boolean used = false;

    @Override
    public void useLifeline(){
        used = true;
        Map<String, String> opt = Questions.currentQuestion.getOptions();

        String properAnsKey = "";
        String properAnsVal = Questions.currentQuestion.getProperAnswer();
        String otherAnsKey;
        String otherAnsVal;

        List<String> abcd = new ArrayList<>(Arrays.asList(Question.abcd));

        Random random = new Random();
        int wrongAnsRandom = random.nextInt(3);

        // getting properAns abcd
        for (String option: opt.keySet()) {
            if(Objects.equals(opt.get(option), properAnsVal)) {
                properAnsKey = option;
                opt.get(properAnsKey);
                abcd.remove(properAnsKey);
                break;
            }
        }

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
