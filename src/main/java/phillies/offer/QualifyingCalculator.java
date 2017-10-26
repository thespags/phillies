package phillies.offer;

import java.util.List;

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
     */
    long calculate(final List<Player> players) {
        // For simplicity this is assuming the list of players is modifiable.
        // Although in general I would avoid that assumption.
        players.sort(Player.BY_SALARY.reversed());
        // If the number of players is less than 150, than take all available players.
        final int size = Math.min(150, players.size());
        long total = 0;

        for (int index = 0; index < size; index++) {
            total += players.get(index).getSalary();
        }

        return total / size;
    }

}
