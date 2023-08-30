import java.util.Iterator;
import java.util.Objects;

public class Questions{
    static Iterator<Question> iterQ;
    public static Question currentQuestion;

    public static void createQuestionSet(){
        Question.addQuestion(new Question("What is the color of the Sun?", new String[]{"black", "yellow", "green"}, "white"));
        Question.addQuestion(new Question("Which football club have won the most UCL trophies?", new String[]{"Manchester Utd", "FC Barcelona", "Arsenal"}, "Real Madrid"));
        Question.addQuestion(new Question("What is the name of mouse Disney character?", new String[]{"Donald", "Daisy", "Pluto"}, "Mickey"));
        Question.addQuestion(new Question("What does ++ in some programming languages?", new String[]{"initialize", "declare", "decrement"}, "increment"));
        Question.addQuestion(new Question("which of the following companies originated from South Korea?", new String[]{"Apple", "Google", "Facebook"}, "Samsung"));
        Question.addQuestion(new Question("What is the first name of the most popular portuguese soccer player named ronaldo", new String[]{"Leo", "Ricardo", "Luka"}, "Cristiano"));
        Question.addQuestion(new Question("Which of the following basketball clubs play their home games in Miami?", new String[]{"Lakers", "Celtics", "Thunder"}, "Heat"));
        Question.addQuestion(new Question("How many permanent teeth can a person have?", new String[]{"4", "28", "71"}, "32"));
        Question.addQuestion(new Question("What the name of dad in \"Family Guy\"?", new String[]{"Steward", "Brian", "Pablo"}, "Peter"));
        Question.addQuestion(new Question("What the name of daughter of Homer Simpson?", new String[]{"Lala", "Marge", "Louis"}, "Maggie"));
        Question.addQuestion(new Question("What animal is BoJack Horseman?", new String[]{"Dinosaur", "Whale", "Cat"}, "Horse"));
        Question.addQuestion(new Question("What is the color of Morty shirt? From \"Rick And Morty\" ?", new String[]{"dark red", "blue", "pink"}, "yellow"));
        iterQ = Question.getQuestionIterator();
    }

    public void askQuestion(){
        currentQuestion = iterQ.next();
        Questions.think();
        System.out.println(currentQuestion.toString());
    }
    
    public boolean checkAnswer(String userAnswer) {

        if (Objects.equals(userAnswer.toUpperCase(), currentQuestion.getProperABCD())) {
            Questions.think();
            System.out.println("Well done!");
            MoneyWon.index++;
            MoneyWon.currentMoney();
        } else {
            Questions.think();
            System.out.println("Oh no! :c");
            Questions.think();
            System.out.println("The proper answer was:" + currentQuestion.getProperAnswer());
            MoneyWon.gameLostMoneyWon();
        }
        return Objects.equals(userAnswer.toUpperCase(), currentQuestion.getProperABCD());
    }

    public static void think(){
        try {
            Thread.sleep(1000);
    }
            catch (InterruptedException e) {System.err.println(e);}
    }
}
