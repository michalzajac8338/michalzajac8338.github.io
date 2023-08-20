import java.util.*;

public class Question {
    public String question;
    private final String properAnswer;
    public static final String[] abcd = {"A","B","C","D"};
    private final Map<String,String> options = new HashMap<>();
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
        String[] randAnswers = new String[4];
        randAnswers = this.randomizeAnswers(answers);

        for(int j=0; j< abcd.length; j++)
        {
            this.options.put(abcd[j],randAnswers[j]);
        }
    }

    public static void addQuestion(Question question) {
        questionSet.add(question);
    }

    public static Iterator <Question> getQuestionIterator() {
        return questionSet.iterator();
    }

    public Map<String, String> getOptions() {
        return this.options;
    }

    public String getProperAnswer(){
        return this.properAnswer;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.question + "\n");
        for (String key : this.options.keySet()) {
            s.append(key).append(": ").append(this.options.get(key)).append("\n");
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
