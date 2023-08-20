import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Questions{
    Iterator<Question> iterQ;
    public static Question currentQuestion;
    Question question1 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question2 = new Question("What is my bro name", new String[]{"Michał", "Martyna", "Mario"}, "Miłosz");
    Question question3 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question4 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question5 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question6 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question7 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question8 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question9 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question10 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question11 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");
    Question question12 = new Question("What is my name", new String[]{"Mario", "Miłosz", "Martyna"}, "Michał");

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
            return Objects.equals(currentQuestion.getOptions().get(userAnswer.toUpperCase()), currentQuestion.getProperAnswer());
        } else {
            System.out.println("Oh no! :c");
            System.out.println("The proper answer was:" + currentQuestion.getProperAnswer());
            MoneyWon.gameLostMoneyWon();
            return Objects.equals(currentQuestion.getOptions().get(userAnswer.toUpperCase()), currentQuestion.getProperAnswer());
        }
    }
}
