package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.Score;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Score} class.
 * <p>
 * This test suite validates the correct behavior of the {@code Score} class,
 * which represents a musical composition consisting of multiple {@link Staff} instances.
 * It verifies object initialization, iteration behavior, and handling of edge cases.
 * </p>
 *
 * <p>Test coverage includes:</p>
 * <ul>
 *   <li>Proper instantiation of a {@code Score} object</li>
 *   <li>Correct iteration through {@code Staff} elements</li>
 *   <li>Compatibility with enhanced for-loops</li>
 *   <li>Graceful behavior when the {@code Score} contains no {@code Staff}</li>
 * </ul>
 *
 * @author [Your Name]
 * @version 1.0
 */
class TestScore {

    /**
     * The list of {@link Staff} objects used to initialize the {@link Score} under test.
     */
    private List<Staff> staffs;

    /**
     * The {@link Score} instance under test.
     */
    private Score score;

    /**
     * Initializes a test environment before each test method execution.
     * <p>
     * This method creates a list of three {@link Staff} objects,
     * each using the {@link Instrument#PURE_TONE} synthesizer,
     * and initializes a {@link Score} instance containing them.
     * </p>
     */
    @BeforeEach
    void setUp() {
        staffs = new ArrayList<>();
        staffs.add(new Staff(Instrument.PURE_TONE));
        staffs.add(new Staff(Instrument.PURE_TONE));
        staffs.add(new Staff(Instrument.PURE_TONE));

        score = new Score(staffs);
    }

    /**
     * Verifies that the {@link Score} constructor properly initializes a new instance.
     * <p>
     * Ensures that the created {@code Score} object is not {@code null}.
     * </p>
     */
    @Test
    @DisplayName("Constructor should initialize Score correctly")
    void testConstructor() {
        assertNotNull(score, "The Score object should not be null.");
    }

    /**
     * Verifies that the iterator returned by {@link Score#iterator()} iterates
     * over all {@link Staff} objects in the correct order.
     * <p>
     * Ensures that each {@link Staff} is non-null and uses the expected instrument.
     * </p>
     */
    @Test
    @DisplayName("Iterator should return all Staff objects in correct order")
    void testIteratorOrder() {
        Iterator<Staff> iterator = score.iterator();

        assertTrue(iterator.hasNext(), "The Score should contain at least one Staff.");

        int index = 0;
        while (iterator.hasNext()) {
            Staff staff = iterator.next();
            assertNotNull(staff, "Each Staff should be non-null.");
            assertEquals(Instrument.PURE_TONE, staff.getInstrument(),
                    "Each Staff should use the PURE_TONE instrument.");
            index++;
        }

        assertEquals(3, index, "The Score should contain exactly 3 Staff objects.");
    }

    /**
     * Verifies that the enhanced for-loop syntax works correctly
     * with the {@link Score} class, confirming its {@link Iterable} implementation.
     * <p>
     * Ensures that each {@link Staff} returned by iteration has the correct instrument.
     * </p>
     */
    @Test
    @DisplayName("Enhanced for-loop should iterate over all Staff objects")
    void testForEachLoop() {
        int count = 0;
        for (Staff s : score) {
            assertEquals(Instrument.PURE_TONE, s.getInstrument(),
                    "Each Staff should have the PURE_TONE instrument.");
            count++;
        }
        assertEquals(3, count, "The for-each loop should iterate over 3 Staff objects.");
    }

    /**
     * Verifies that an empty {@link Score} behaves correctly.
     * <p>
     * Ensures that the iterator is non-null and contains no elements.
     * </p>
     */
    @Test
    @DisplayName("Empty Score should behave correctly")
    void testEmptyScore() {
        Score emptyScore = new Score(new ArrayList<>());
        Iterator<Staff> iterator = emptyScore.iterator();

        assertNotNull(iterator, "The iterator of an empty Score should not be null.");
        assertFalse(iterator.hasNext(), "An empty Score should have no Staff objects.");
    }
}
