package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.Staff;
import fr.univartois.butinfo.lensymphony.notes.Score;
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
 * Ensures correct initialization, iteration, and behavior
 * of a musical score containing multiple {@link Staff} instances.
 * </p>
 */
public class TestScore {

    private List<Staff> staffs;
    private Score score;

    /**
     * Sets up a test score with several staffs using the same instrument.
     */
    @BeforeEach
    void setUp() {
        staffs = new ArrayList<>();
        staffs.add(new Staff(Instrument.PURE_TONE));
        staffs.add(new Staff(Instrument.PURE_TONE));
        staffs.add(new Staff(Instrument.PURE_TONE));

        score = new Score(staffs);
    }

    @Test
    @DisplayName("Constructor should initialize Score correctly")
    void testConstructor() {
        assertNotNull(score, "The Score object should not be null.");
    }

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

    @Test
    @DisplayName("Empty Score should behave correctly")
    void testEmptyScore() {
        Score emptyScore = new Score(new ArrayList<>());
        Iterator<Staff> iterator = emptyScore.iterator();

        assertNotNull(iterator, "The iterator of an empty Score should not be null.");
        assertFalse(iterator.hasNext(), "An empty Score should have no Staff objects.");
    }
}
