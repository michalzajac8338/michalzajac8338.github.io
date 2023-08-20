import java.util.Iterator;
import java.util.Objects;

public class Questions{
    Iterator<Question> iterQ;
    public static Question currentQuestion;
    Question question1 = new Question("What is the color of the Sun?", new String[]{"black", "yellow", "green"}, "white");
    Question question2 = new Question("Which football club have won the most UCL trophies?", new String[]{"Manchester Utd", "FC Barcelona", "Arsenal"}, "Real Madrid");
    Question question3 = new Question("What is the name of mouse Disney character?", new String[]{"Donald", "Daisy", "Pluto"}, "Mickey");
    Question question4 = new Question("What does ++ in some programming languages?", new String[]{"initialize", "declare", "decrement"}, "increment");
    Question question5 = new Question("which of the following companies originated from South Korea?", new String[]{"Apple", "Google", "Facebook"}, "Samsung");
    Question question6 = new Question("What is the first name of the most popular portuguese soccer player named ronaldo", new String[]{"Leo", "Ricardo", "Luka"}, "Cristiano");
    Question question7 = new Question("Which of the following basketball clubs play their home games in Miami?", new String[]{"Lakers", "Celtics", "Thunder"}, "Heat");
    Question question8 = new Question("How many permanent teeth can a person have?", new String[]{"4", "28", "71"}, "32");
    Question question9 = new Question("What the name of dad in \"Family Guy\"?", new String[]{"Steward", "Brian", "Pablo"}, "Peter");
    Question question10 = new Question("What the name of daughter of Homer Simpson?", new String[]{"Lala", "Marge", "Louis"}, "Maggie");
    Question question11 = new Question("What animal is BoJack Horseman?", new String[]{"Dinosaur", "Whale", "Cat"}, "Horse");
    Question question12 = new Question("What is the color of Morty shirt? From \"Rick And Morty\" ?", new String[]{"dark red", "blue", "pink"}, "yellow");

    public void createQuestionSet(){
        Question.addQuestion(question1);
        Question.addQuestion(question2);
        Question.addQuestion(question3);
        Question.addQuestion(question4);
        Question.addQuestion(question5);
        Question.addQuestion(question6);
        Question.addQuestion(question7);
        Question.addQuestion(question8);
        Question.addQuestion(question9);
        Question.addQuestion(question10);
        Question.addQuestion(question11);
        Question.addQuestion(question12);
        iterQ = Question.getQuestionIterator();
    }

    public void askQuestion(){
        currentQuestion = iterQ.next();
        System.out.println(currentQuestion.toString());
    }
    
    public boolean checkAnswer(String userAnswer) {

        if (Objects.equals(currentQuestion.getOptions().get(userAnswer.toUpperCase()), currentQuestion.getProperAnswer())) {
            System.out.println("Well done!");
            MoneyWon.index++;
            MoneyWon.currentMoney();
        } else {
            System.out.println("Oh no! :c");
            System.out.println("The proper answer was:" + currentQuestion.getProperAnswer());
            MoneyWon.gameLostMoneyWon();
        }
        return Objects.equals(currentQuestion.getOptions().get(userAnswer.toUpperCase()), currentQuestion.getProperAnswer());
    }
}
