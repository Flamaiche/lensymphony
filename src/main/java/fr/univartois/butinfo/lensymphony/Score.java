package fr.univartois.butinfo.lensymphony;

import java.util.Iterator;
import java.util.List;

/**
 * The {@code Score} class represents a complete musical score,
 * composed of a list of staves ({@link Staff}).
 * <p>
 * Each staff can correspond to a distinct voice or instrument.
 * This class allows iteration over all the staves that make up
 * the score.
 * </p>
 *
 * @author Babahamou Malik
 * @version 1.0
 */
public class Score implements Iterable<Staff> {

    /**
     * The list of staves that make up the score.
     */
    private final List<Staff> staffs;

    /**
     * Creates a new {@code Score} instance with the specified list of staves.
     *
     * @param staffs The list of staves that compose this score.
     *               It must not be {@code null}.
     */
    public Score(List<Staff> staffs) {
        this.staffs = staffs;
    }

    /**
     * Returns an iterator over the staves in the score.
     * <p>
     * This allows the use of a “for-each” loop to iterate through
     * the different staves.
     * </p>
     *
     * @return An iterator over the {@link Staff} objects in the score.
     */
    @Override
    public Iterator<Staff> iterator() {
        return staffs.iterator();
    }
}
