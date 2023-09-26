package whoWantsTBAM.GameLogic;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class QuestionHandler {
    private static final Set<Question> questionSet = new LinkedHashSet<>();
    static Iterator<Question> iterQ;
    public static Question currentQuestion;

    public static void createQuestionSet(){
        questionSet.add(new Question("What is the color of the Sun?", new String[]{"black", "yellow", "green"}, "white"));
        questionSet.add(new Question("Which football club has won the most UCL trophies?", new String[]{"Manchester Utd", "FC Barcelona", "Arsenal"}, "Real Madrid"));
        questionSet.add(new Question("What is the name of Disney's mouse?", new String[]{"Donald", "Daisy", "Pluto"}, "Mickey"));
        questionSet.add(new Question("What does ++ in some programming languages?", new String[]{"initialize", "declare", "decrement"}, "increment"));
        questionSet.add(new Question("Which of the following companies originated from South Korea?", new String[]{"Apple", "Google", "Facebook"}, "Samsung"));
        questionSet.add(new Question("What is the first name of a popular portuguese soccer player named Ronaldo?", new String[]{"Leo", "Ricardo", "Luka"}, "Cristiano"));
        questionSet.add(new Question("Which of the following basketball clubs play their home games in Miami?", new String[]{"Lakers", "Celtics", "Thunder"}, "Heat"));
        questionSet.add(new Question("How many permanent teeth can a person have?", new String[]{"4", "28", "71"}, "32"));
        questionSet.add(new Question("What is the name of the dad in \"Family Guy\"?", new String[]{"Steward", "Brian", "Pablo"}, "Peter"));
        questionSet.add(new Question("What is the name of Homer Simpson's daughter?", new String[]{"Lala", "Marge", "Louis"}, "Maggie"));
        questionSet.add(new Question("What animal is BoJack Horseman?", new String[]{"Dinosaur", "Whale", "Cat"}, "Horse"));
        questionSet.add(new Question("What is the color of Morty's shirt? From \"Rick And Morty\" ?", new String[]{"dark red", "blue", "pink"}, "yellow"));
        iterQ = questionSet.iterator();
    }

    public void askQuestion(){
        currentQuestion = iterQ.next();
    }
    
    public boolean checkAnswer(String userAnswer) {

        if (Objects.equals(userAnswer.toUpperCase(), currentQuestion.getProperABCD())) {
            MoneyWon.index++;
        }
        return Objects.equals(userAnswer.toUpperCase(), currentQuestion.getProperABCD());
    }
}
