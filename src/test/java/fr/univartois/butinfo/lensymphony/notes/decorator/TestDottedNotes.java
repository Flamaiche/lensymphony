package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DottedNotes decorator.
 *
 * Tests that the frequency remains unchanged and
 * the duration is correctly increased by 50%.
 */

class TestDottedNotes {

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
