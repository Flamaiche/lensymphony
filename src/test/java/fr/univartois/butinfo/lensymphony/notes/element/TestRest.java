package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rest class.
 * Tests the basic behavior of musical rests, including creation,
 * frequency, and duration.
 */
public class TestRest {

    /**
     * Tests that a Rest object can be created and is not null.
     */
    @Test
    void testRestNotNull() {
        Rest rest = new Rest(NoteValue.QUARTER);
        assertNotNull(rest, "The rest object must not be null");
    }

    /**
     * Tests that the frequency of a rest is always 0.0 Hz.
     */
    @Test
    void testFrequencyIsZero() {
        Rest rest = new Rest(NoteValue.QUARTER);
        assertEquals(0.0, rest.getFrequency(), 0.0001,
                "Frequency of a rest should always be 0.0");
    }

    /**
     * Tests that the duration of a rest matches the expected NoteValue duration
     * given a specific tempo.
     */
    @Test
    void testDurationMatchesNoteValue() {
        Rest rest = new Rest(NoteValue.QUARTER);
        int tempo = 120;
        int expectedDuration = NoteValue.QUARTER.duration(tempo);
        assertEquals(expectedDuration, rest.getDuration(tempo),
                "Duration should match the NoteValue and tempo");
    }

}
