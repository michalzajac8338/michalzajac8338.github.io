package whoWantsTBAM.GameLogic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MoneyWonTest {

    @ParameterizedTest
    @ValueSource(ints = {7,8,9,10,11})
    void endMoney_lostAt50000SafetyNet(int n) {

        //given
        MoneyWon.index = n;

        //when
        String s = MoneyWon.endMoney();

        //then
        assertEquals("$50,000!!! How are You going to spend it?",s);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,13})
    void resigned_failsWithIndexOutOfZeroToTwelve(int n) {

        //given
        MoneyWon.index = n;

        //then
        assertThrows(IndexOutOfBoundsException.class, MoneyWon::resigned);
    }

    @ParameterizedTest
    @CsvSource(value = {"0;Really nice. You have won $0","5;Really nice. You have won $10,000","11;Really nice. You have won $500,000"},delimiter = ';')
    void resigned_AtDifferentPoints(String input, String expected) {

        //given
        MoneyWon.index = Integer.parseInt(input);

        //when
        String actual = MoneyWon.resigned();

        //then
        assertEquals(expected,actual);

    }
}