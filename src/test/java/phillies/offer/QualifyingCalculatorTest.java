package phillies.offer;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for {@link QualifyingCalculator}
 *
 * @author jspagnola
 */
public class QualifyingCalculatorTest {

    @Test
    public void checkAverage() {
        final Player playerOne = new Player("Joe", 100);
        final Player playerTwo = new Player("Bill", 200);

        final long result = QualifyingCalculator.get().calculate(Arrays.asList(playerOne, playerTwo));

        assertThat(result, is(150L));
    }

    @Test
    public void checkOnlyTop150() {
        final List<Player> players = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            players.add(new Player("Joe", 100));
        }
        // This player will be ignored so the average remains 100.
        players.add(new Player("Joe", 0));

        final long result = QualifyingCalculator.get().calculate(players);

        assertThat(result, is(100L));
    }
}