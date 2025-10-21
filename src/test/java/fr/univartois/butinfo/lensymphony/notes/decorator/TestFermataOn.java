package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFermataOn {

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

    @Test
    void testNullNoteThrowsException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> new FermataOn(null)
        );
        assertEquals("the note for FermataOn cannot be null", exception.getMessage());
    }


}
