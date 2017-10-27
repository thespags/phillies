package phillies.offer;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Determines a qualifying offer.
 * See {@link #calculate(List)}.
 *
 * @author jspagnola
 */
class QualifyingCalculator {

    private static final QualifyingCalculator INSTANCE = new QualifyingCalculator();

    private QualifyingCalculator() {
    }

    static QualifyingCalculator get() {
        return INSTANCE;
    }

    /**
     * Given a list of players calculates the qualifying based on the average of the top 150 players salaries.
     * <p>
     * This assumes the list of players is already sorted in descending order of salary.
     */
    long calculate(final List<Player> players) {
        Objects.requireNonNull(players, "list of players must be non null");
        Preconditions.checkArgument(!players.isEmpty(), "list of players must be non empty");
        // If the number of players is less than 150, than take all available players.
        final int size = Math.min(150, players.size());
        long total = 0;

        for (int index = 0; index < size; index++) {
            total += players.get(index).getSalary();
        }

        return total / size;
    }

    int findMiddle(final long result, final List<Player> players) {
        Objects.requireNonNull(players, "list of players must be non null");
        Preconditions.checkArgument(!players.isEmpty(), "list of players must be non empty");

        // Because its an average the result must somewhere within the list of players.
        // That is we can start the first player and end at the last player and don't need special edge cases.
        final int size = players.size() - 1;
        // We could do a binary search but for simplicity the search is over at most 150 players so small enough to not care.
        for (int i = 0; i < size; i++) {
            if (players.get(i).getSalary() >= result && result >= players.get(i + 1).getSalary()) {
                return i;
            }
        }
        throw new IllegalArgumentException("result was not within the player range: " + result);
    }
}
