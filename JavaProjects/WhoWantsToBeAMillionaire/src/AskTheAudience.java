import java.util.Random;

public class AskTheAudience extends Lifeline{
    public static boolean used = false;
    @Override
    public void useLifeline(){
        used = true;

        Random random = new Random();
        int goodAnsPercentage = random.nextInt(30) + 50;
        int wrong1 = random.nextInt(100-goodAnsPercentage);
        int wrong2 = random.nextInt(100-goodAnsPercentage-wrong1);
        int wrong3 = 100-goodAnsPercentage-wrong1-wrong2;

        opt.put(properAnsKey, goodAnsPercentage + "%");
        opt.put(abcd.get(0), wrong1 + "%");
        opt.put(abcd.get(1), wrong2 + "%");
        opt.put(abcd.get(2), wrong3 + "%");

        System.out.println("The Audience has spoken!!!");
        System.out.println("A" + ": " + opt.get("A"));
        System.out.println("B" + ": " + opt.get("B"));
        System.out.println("C" + ": " + opt.get("C"));
        System.out.println("D" + ": " + opt.get("D"));

    }
}
