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
        final long offer = QualifyingCalculator.get().calculate(players);
        final String value = NumberFormat.getCurrencyInstance().format(offer);

        System.out.println("salary: " + value);
    }
}
