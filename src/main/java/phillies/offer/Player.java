package phillies.offer;

import com.google.common.base.MoreObjects;

import java.util.Comparator;

/**
 * A tuple of a player name and their salary. I'm using comparator instead of comparable
 * because there's no natural sorting of a player that follows for comparable.
 *
 * @author jspagnola
 */
class Player {

    static final Comparator<Player> BY_NAME = Comparator.comparing(Player::getName);
    static final Comparator<Player> BY_SALARY = Comparator.comparingLong(Player::getSalary);
    private final String name;
    /**
     * Use a long to avoid integer overflow, as the total salaries is well over the max integer.
     */
    private final long salary;

    Player(final String name, final long salary) {
        this.name = name;
        this.salary = salary;
    }

    String getName() {
        return name;
    }

    long getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("salary", salary)
            .toString();
    }
}
