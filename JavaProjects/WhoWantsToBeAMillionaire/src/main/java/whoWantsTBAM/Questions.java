package whoWantsTBAM;

import java.util.Iterator;
import java.util.Objects;

public class Questions{
    static Iterator<Question> iterQ;
    public static Question currentQuestion;

    public static void createQuestionSet(){
        Question.addQuestion(new Question("What is the color of the Sun?", new String[]{"black", "yellow", "green"}, "white"));
        Question.addQuestion(new Question("Which football club has won the most UCL trophies?", new String[]{"Manchester Utd", "FC Barcelona", "Arsenal"}, "Real Madrid"));
        Question.addQuestion(new Question("What is the name of Disney's mouse?", new String[]{"Donald", "Daisy", "Pluto"}, "Mickey"));
        Question.addQuestion(new Question("What does ++ in some programming languages?", new String[]{"initialize", "declare", "decrement"}, "increment"));
        Question.addQuestion(new Question("Which of the following companies originated from South Korea?", new String[]{"Apple", "Google", "Facebook"}, "Samsung"));
        Question.addQuestion(new Question("What is the first name of a popular portuguese soccer player named Ronaldo?", new String[]{"Leo", "Ricardo", "Luka"}, "Cristiano"));
        Question.addQuestion(new Question("Which of the following basketball clubs play their home games in Miami?", new String[]{"Lakers", "Celtics", "Thunder"}, "Heat"));
        Question.addQuestion(new Question("How many permanent teeth can a person have?", new String[]{"4", "28", "71"}, "32"));
        Question.addQuestion(new Question("What is the name of the dad in \"Family Guy\"?", new String[]{"Steward", "Brian", "Pablo"}, "Peter"));
        Question.addQuestion(new Question("What is the name of Homer Simpson's daughter?", new String[]{"Lala", "Marge", "Louis"}, "Maggie"));
        Question.addQuestion(new Question("What animal is BoJack Horseman?", new String[]{"Dinosaur", "Whale", "Cat"}, "Horse"));
        Question.addQuestion(new Question("What is the color of Morty's shirt? From \"Rick And Morty\" ?", new String[]{"dark red", "blue", "pink"}, "yellow"));
        iterQ = Question.getQuestionIterator();
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
