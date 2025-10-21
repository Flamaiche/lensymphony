package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the FermataOn class.
 * Tests the behavior of FermataOn, including frequency preservation,
 * duration doubling, and constructor validation for null notes.
 *
 * @author Jabir
 */
public class TestFermataOn {

    /**
     * Tests that the frequency of a FermataOn note
     * remains the same as the original note.
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
     * Tests that the duration of a FermataOn note
     * is correctly doubled compared to the original note.
     */
    @Test
    void testDurationDoubled() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };
        Note fermata = new FermataOn(note);
        assertEquals(2000, fermata.getDuration(120));
    }

    /**
     * Tests that creating a FermataOn note with a null note
     * throws a NullPointerException.
     */
    @Test
    void testNullNoteThrowsException() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new FermataOn(null)
        );
        assertEquals("the note for FermataOn cannot be null", exception.getMessage());
    }
}
