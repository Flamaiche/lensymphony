package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the TiedNotes class.
 * Tests the behavior of tied notes, including creation, frequency,
 * total duration, and constructor validation for null or empty lists.
 *
 * @author Jabir
 */

public class TestTiedNotes {

    /**
     * Tests that a TiedNotes object correctly calculates the frequency
     * and total duration from a list of notes.
     */

    @Test
    void testTiedNotesFrequencyAndDuration() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);

        MusicalNote note1 = new MusicalNote(pitch, NoteValue.QUARTER);
        MusicalNote note2 = new MusicalNote(pitch, NoteValue.HALF);

        TiedNotes tied = new TiedNotes(List.of(note1, note2));

        assertEquals(note1.getFrequency(), tied.getFrequency(), 0.001);

        int tempo = 120;
        int expectedDuration = note1.getDuration(tempo) + note2.getDuration(tempo);
        assertEquals(expectedDuration, tied.getDuration(tempo));
    }
    /**
     * Tests that constructing a TiedNotes object with an empty list
     * throws an IllegalArgumentException.
     */
    @Test
    void testTiedNotesEmptyListThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new TiedNotes(List.of()),
                "Expected constructor to throw for empty list"
        );
        assertEquals("Tied notes list cannot be null or empty", thrown.getMessage());
    }

    /**
     * Tests that constructing a TiedNotes object with a null list
     * throws an IllegalArgumentException.
     */

    @Test
    void testTiedNotesNullListThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new TiedNotes(null),
                "Expected constructor to throw for null list"
        );
        assertEquals("Tied notes list cannot be null or empty", thrown.getMessage());
    }

}
