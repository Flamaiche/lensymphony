package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DottedNotes} decorator.
 * <p>
 * This decorator increases the duration of a note by 50% while leaving
 * the frequency unchanged.
 * </p>
 */
class TestDottedNotes {

    /**
     * Verifies that the frequency of a dotted note remains unchanged
     * compared to the original note.
     */
    @Test
    void testFrequencyUnchanged() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };
        Note dotted = new DottedNotes(note);
        assertEquals(440, dotted.getFrequency(), 0.001);
    }

    /**
     * Verifies that the duration of a dotted note is increased by 50%
     * compared to the original note's duration.
     */
    @Test
    void testDurationIncreasedByHalf() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };
        Note dotted = new DottedNotes(note);
        assertEquals(1500, dotted.getDuration(120));
    }

    /**
     * Tests the duration increase for a different original note duration
     * to ensure the 50% increase is applied correctly in general.
     */
    @Test
    void testAnotherDuration() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 800; }
        };
        Note dotted = new DottedNotes(note);
        assertEquals(1200, dotted.getDuration(120));
    }
}
