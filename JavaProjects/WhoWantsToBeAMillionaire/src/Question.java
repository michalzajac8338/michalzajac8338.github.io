import java.util.*;

public class Question {
    public String question;
    private final String properAnswer;
    private String properABCD;
    public static final String[] abcd = {"A","B","C","D"};
    public String[][] options = new String[4][2];
    private static final Set<Question> questionSet = new LinkedHashSet<>();

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

    public static void addQuestion(Question question) {
        questionSet.add(question);
    }

    public static Iterator <Question> getQuestionIterator() {
        return questionSet.iterator();
    }

    public String[][] getOptions() {
        return this.options;
    }

    public String getProperAnswer(){
        return this.properAnswer;
    }
    public String getProperABCD(){
        return this.properABCD;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.question + "\n");
        for (var i = 0; i<=3; i++) {
            s.append(this.options[i][0]).append(": ").append(this.options[i][1]).append("\n");
        }
        return s.toString();
    }

    private String[] randomizeAnswers(String[] a){
        List<String> b = Arrays.asList(a);
        Collections.shuffle(b);
        b.toArray(a);
        return a;
    }
}
