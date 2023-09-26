package whoWantsTBAM.GameLogic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class QuestionHandlerTest {

    @Mock
    Question question, question1;
    @Mock
    Question currentQuestion;
    @Mock
    QuestionHandler questionHandler;

    @Test
    void checkTheStateOfCurrentQuestion_duringGameplay() {

        //given
        Set<Question> questionSet = new LinkedHashSet<>();
        questionSet.add(question);
        questionSet.add(question1);
        QuestionHandler.iterQ = questionSet.iterator();

        //when
        doAnswer(I -> currentQuestion = QuestionHandler.iterQ.next()).when(questionHandler).askQuestion();
        questionHandler.askQuestion();

        //then
        assertEquals(question, currentQuestion);

        //when
        questionHandler.askQuestion();

        //then
        assertEquals(question1, currentQuestion);

        assertThrows(NoSuchElementException.class, () -> questionHandler.askQuestion());
    }


    QuestionHandler questionHandler1 = new QuestionHandler();

    @Test
    void checkAnswer_singleProperAnswer_shouldIncreaseMoneyUpToOne() {
        //given
        MoneyWon.index = 0;
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion = currentQuestion;

        //when
        doReturn("A").when(currentQuestion).getProperABCD();
        questionHandler1.checkAnswer("A");

        //then
        assertEquals(1, MoneyWon.index);
        assertEquals(currentQuestion.getProperABCD(),"A");
        assertNotEquals(currentQuestion.getProperABCD(), "B");
    }

    @Test
    void checkAnswer_fourProperAnswers_shouldIncreaseMoneyUpToFour() {
        //given
        MoneyWon.index = 0;
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion = currentQuestion;

        //when
        doReturn("A").when(currentQuestion).getProperABCD();
        questionHandler1.checkAnswer("A");
        questionHandler1.checkAnswer("A");
        questionHandler1.checkAnswer("A");
        questionHandler1.checkAnswer("A");

        //then
        assertEquals(4, MoneyWon.index);
    }

    @Test
    void checkAnswer_twoProperThanWrong_shouldIncreaseMoneyUpToTwo() {
        //given
        MoneyWon.index = 0;
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion = currentQuestion;

        //when
        doReturn("A").when(currentQuestion).getProperABCD();
        questionHandler1.checkAnswer("A");
        questionHandler1.checkAnswer("A");
        questionHandler1.checkAnswer("B");

        //then
        assertEquals(2, MoneyWon.index);
    }
}