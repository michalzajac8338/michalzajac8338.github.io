package whoWantsTBAM.GameLogic;

import java.util.*;

public class Question {

    public static final String[] abcd = {"A","B","C","D"};
    public String question;
    private final String properAnswer;
    public String[][] options = new String[4][2];
    private String properABCD;

    public Question(String question, String[] wrongAnswers, String properAnswer){

        this.question = question;
        this.properAnswer = properAnswer;

        int i = 0;
        String[] answers = new String[4];
        for (String wrongAnswer : wrongAnswers) {
            answers[i] = wrongAnswer;
            i++;
        }

        answers[3] = properAnswer;
        String[] randAnswers;
        randAnswers = this.randomizeAnswers(answers);

        for(int j=0; j< abcd.length; j++)
        {
            this.options[j] = new String[]{abcd[j], randAnswers[j]};
            if(Objects.equals(randAnswers[j], properAnswer)) this.properABCD = abcd[j];
        }
    }

    public String getProperAnswer(){
        return this.properAnswer;
    }
    public String getProperABCD(){
        return this.properABCD;
    }

    public String toStringOption(int a) {
        String[] s = new String[4];
        for (var i = 0; i<=3; i++) {
            s[i] = this.options[i][0] + ": " + this.options[i][1];
        }
        return s[a];
    }

    private String[] randomizeAnswers(String[] a){
        List<String> b = Arrays.asList(a);
        Collections.shuffle(b);
        b.toArray(a);
        return a;
    }
}
