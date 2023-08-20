import java.util.*;
import java.util.stream.Collectors;

public abstract class Lifeline {
    public abstract void useLifeline();

    //protected static Map<String, String> opt = Questions.currentQuestion.getOptions();

    // protected static Map<String, String> opt = SerializationUtils.clone(new HashMap<>(Questions.currentQuestion.getOptions()));
    protected Map<String, String> opt = Questions.currentQuestion.getOptions().entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    protected String properAnsKey = "";
    protected String properAnsVal = Questions.currentQuestion.getProperAnswer();
    protected List<String> abcd = new ArrayList<>(Arrays.asList(Question.abcd));

    public Lifeline(){

        // getting properAns abcd
        for (String option: opt.keySet()) {
            if(Objects.equals(opt.get(option), properAnsVal)) {
                properAnsKey = option;
                abcd.remove(properAnsKey);
                break;
            }
        }
        System.out.println(opt);

    }
}
