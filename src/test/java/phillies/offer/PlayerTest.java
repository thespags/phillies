package phillies.offer;

import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Unit tests for {@link Player}.
 *
 * @author jspagnola
 */
public class PlayerTest {

    @Test
    public void sortBySalary() throws ParseException {
        final Player playerOne = new Player("Joe", 1000);
        final Player playerTwo = new Player("Bill", 2000);

        final List<Player> players = Arrays.asList(playerTwo, playerOne);
        players.sort(Player.BY_SALARY);

        assertThat(players, contains(playerOne, playerTwo));
    }

    @Test
    public void sortByName() {
        final Player playerOne = new Player("Joe", 1000);
        final Player playerTwo = new Player("Bill", 2000);

        final List<Player> players = Arrays.asList(playerOne, playerTwo);
        players.sort(Player.BY_NAME);

        assertThat(players, contains(playerTwo, playerOne));
    }
}