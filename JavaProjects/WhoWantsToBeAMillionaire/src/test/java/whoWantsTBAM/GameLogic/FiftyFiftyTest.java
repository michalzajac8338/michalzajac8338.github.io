package whoWantsTBAM.GameLogic;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FiftyFiftyTest {


    @Mock
    Question currentQuestion;

    @RepeatedTest(10)
    void useLifeline() {
        //given
        FiftyFifty fiftyFifty = new FiftyFifty();
        String[] remainingAnswers;
        doReturn("A").when(currentQuestion).getProperABCD();
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion = currentQuestion;
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion.options = new String[][]{{"A",""}, {"B",""}, {"C",""}, {"D",""}};

        //when
        remainingAnswers = fiftyFifty.useLifeline();

        //then
        assertEquals(remainingAnswers[0],"A");
        assertTrue(Objects.equals(remainingAnswers[1], "B") ||
                Objects.equals(remainingAnswers[1], "C") ||
                Objects.equals(remainingAnswers[1], "D"));
    }
}