package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the FermataOn decorator.
 * Tests the behavior of FermataOn, including frequency preservation,
 * duration preservation, and constructor validation for null notes.
 *
 * The FermataOn decorator is expected to extend the duration of a note
 * in practice, but in this implementation, the duration remains unchanged
 * for testing purposes.
 *
 * Author: Jabir
 */

public class TestFermataOn {

    /**
     * Tests that a FermataOn object preserves the frequency of the original note.
     */

    @Test
    void testFrequencyUnchanged() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };
        Note fermata = new FermataOn(note);
        assertEquals(440, fermata.getFrequency(), 0.001);
    }
    /**
     * Tests that a FermataOn object preserves the duration of the original note.
     */


    @Test
    void testDurationUnchanged() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }

        };
        Note fermata = new FermataOn(note);
        assertEquals(1000, fermata.getDuration(120));
    }

    /**
     * Tests that constructing a FermataOn object with a null note
     * throws a NullPointerException.
     */

    @Test
    void testNullNoteThrowsException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> new FermataOn(null)
        );
        assertEquals("the note for FermataOn cannot be null", exception.getMessage());
    }


}
