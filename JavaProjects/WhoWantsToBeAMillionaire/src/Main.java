import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String ans;
        boolean didOk = true;
        Scanner input = new Scanner (System.in);
        // System.out.println("Enter Your name");
        // String name = input.next();
        // System.out.printf("Welcome %s! Let's start the game\n",name);

        Questions q = new Questions();
        q.createQuestionSet();

        while (didOk) {
            q.askQuestion();

            if(!FiftyFifty.used | !AskTheAudience.used) {
                System.out.println("Do You wish to use Lifeline? y-yes other-no");
                ans = input.next();

                if (Objects.equals(ans, "y")) {
                    System.out.println("Which one?");
                    if(!FiftyFifty.used) System.out.println("f - fifty-fifty");
                    if(!AskTheAudience.used) System.out.println("a - ask the audience");
                    System.out.println("other - OK i don't need it");
                    ans = input.next();
                    switch (ans.toUpperCase()) {
                        case "F":
                            if(FiftyFifty.used){
                                System.out.println("You have already used this one :C");
                                break;
                            }
                            FiftyFifty ff = new FiftyFifty();
                            ff.useLifeline();
                            break;
                        case "A":
                            if(AskTheAudience.used){
                                System.out.println("You have already used this one :C");
                                break;
                            }
                            AskTheAudience ata = new AskTheAudience();
                            ata.useLifeline();
                            break;
                            default:
                            System.out.println("Ok, another time");
                    }
                }
            }

            ans = input.next();
            didOk = q.checkAnswer(ans);

            if (didOk) {
                System.out.println("Do You wish to continue? y-yes other - no");
                ans = input.next();
                if(!Objects.equals(ans.toUpperCase(), "Y")) {
                    System.out.println("It was a wonderful game (or not)");
                    MoneyWon.resigned();
                    break;
                }
            }
        }
    }
}