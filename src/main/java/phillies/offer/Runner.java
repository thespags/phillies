package phillies.offer;

import java.text.NumberFormat;
import java.util.List;

/**
 * Entry point for running the scraper and calculating the qualifying offer.
 *
 * @author jspagnola
 */
public class Runner {

    public static void main(final String... args) {
        final List<Player> players = new Scraper().scrape();
        players.sort(Player.BY_SALARY.reversed());
        final long offer = QualifyingCalculator.get().calculate(players);
        final String value = NumberFormat.getCurrencyInstance().format(offer);
        final int middle = QualifyingCalculator.get().findMiddle(offer, players);

        // Give extra information for comparable players' salaries.
        // Here I decided at most 5 above and 5 below the qualifying offer.
        final int top = Math.max(middle - 5, 0);
        System.out.println((middle - top) + " closet players above the qualifying offer:");
        for (int i = top; i <= middle; i++) {
            final Player player = players.get(i);
            System.out.println(player.getName() + " " + NumberFormat.getCurrencyInstance().format(player.getSalary()));
        }
        // We don't want to count the middle in the low range so we modify 1 as appropriate.
        final int bottom = Math.min(middle + 5 + 1, players.size());
        System.out.println("\n" + (bottom - middle - 1) + " closet players below the qualifying offer:");
        for (int i = middle + 1; i < bottom; i++) {
            final Player player = players.get(i);
            System.out.println(player.getName() + " " + NumberFormat.getCurrencyInstance().format(player.getSalary()));
        }
        System.out.println("\nQualifying Offer Salary: " + value);
    }
}
