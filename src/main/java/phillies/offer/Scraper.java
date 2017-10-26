package phillies.offer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Scrape data of all players and their salary. Sourced from the Jsoup <a href="https://jsoup.org/">documentation</a>
 *
 * @author jspagnola
 */
class Scraper {

    private static final Logger LOGGER = Logger.getLogger(Scraper.class.getSimpleName());
    private static final String SALARY_URL = "https://questionnaire-148920.appspot.com/swe/";
    private static final String NO_SALARY = "no salary data";

    Scraper() {
    }

    /**
     * Scrapes from the questionnaire page returning a list of players and their salaries.
     */
    List<Player> scrape() {
        final List<Player> players = new ArrayList<>();
        final Document doc = getSalaryPage();

        // Get the table of players skipping the header.
        final Element table = doc.getElementById("salaries-table");
        final Elements tableBody = table.getElementsByTag("tbody");
        final Elements playerElements = tableBody.first().getElementsByTag("tr");

        for (final Element playerElement : playerElements) {
            final Optional<String> name = getCell(playerElement, "player-name");

            // If the data is malformed for a row skip it.
            if (name.isPresent()) {
                final Optional<Long> salary = getSalary(playerElement);
                salary.ifPresent(value -> players.add(new Player(name.get(), value)));
            } else {
                LOGGER.log(Level.INFO, "Empty player row: " + String.valueOf(playerElement));
            }
        }
        return players;
    }

    private Optional<Long> getSalary(final Element playerElement) {
        final Optional<String> salary = getCell(playerElement, "player-salary");
        if (salary.isPresent() && !Objects.equals(NO_SALARY, salary.get())) {
            // By page views it appears salary are either prefixed with zero, one or two $.
            // As well as an appropriate number of , or zero.
            // No salary appears to have cents.
            // Let's simply strip out $ and , instead of trying to parse correctly without stripping.
            final String sanitizedSalary = salary.get().replaceAll("[$,]", "");
            try {
                return Optional.of(Long.parseLong(sanitizedSalary));
            } catch (final NumberFormatException x) {
                LOGGER.log(Level.INFO, "Error parsing salary", x);
                return Optional.empty();
            }
        }
        //This is expected for some players, LOGGER.log(Level.INFO, "No salary found: " + String.valueOf(playerElement));
        return Optional.empty();
    }

    private Optional<String> getCell(final Element element, final String className) {
        return Optional.ofNullable(element.getElementsByClass(className))
            .map(Elements::text);
    }

    private Document getSalaryPage() {
        try {
            return Jsoup.connect(SALARY_URL).get();
        } catch (final IOException x) {
            // convert checked to unchecked exception.
            throw new RuntimeException(x);
        }
    }
}
