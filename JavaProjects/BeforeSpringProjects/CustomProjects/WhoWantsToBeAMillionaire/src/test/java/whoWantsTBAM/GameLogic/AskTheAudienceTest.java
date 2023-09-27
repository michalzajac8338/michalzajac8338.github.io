package whoWantsTBAM.GameLogic;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AskTheAudienceTest {


    @Mock
    Question currentQuestion;

    @RepeatedTest(10)
    void useLifeline_associatesProperAnswerWithAtLeastThirtyPercent_andAnswersAddUpTo100() {
        //given
        AskTheAudience askTheAudience = new AskTheAudience();
        String[] optionsPercentage;
        doReturn("A").when(currentQuestion).getProperABCD();
        whoWantsTBAM.GameLogic.QuestionHandler.currentQuestion = currentQuestion;

        //when
        optionsPercentage = askTheAudience.useLifeline();

        //then
        assertTrue(Integer.parseInt(optionsPercentage[0].substring(0,2)) >= 30);
        assertEquals(100, Integer.parseInt(optionsPercentage[0].substring(0, 2)) +
                Integer.parseInt(optionsPercentage[1].substring(0, optionsPercentage[1].length()-1)) +
                Integer.parseInt(optionsPercentage[2].substring(0, optionsPercentage[2].length()-1)) +
                Integer.parseInt(optionsPercentage[3].substring(0, optionsPercentage[3].length()-1)));

        assertTrue(AskTheAudience.used);
    }
}