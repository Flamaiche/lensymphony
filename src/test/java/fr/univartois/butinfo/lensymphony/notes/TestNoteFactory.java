package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.notes.decorator.DottedNotes;
import fr.univartois.butinfo.lensymphony.notes.decorator.FermataOn;
import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.element.Rest;
import fr.univartois.butinfo.lensymphony.notes.element.TiedNotes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link NoteFactory} singleton.
 *
 * <p>These tests verify the creation of various types of notes using the factory,
 * including basic notes, rests, dotted notes, fermata notes, tied notes,
 * and error handling for invalid inputs.</p>
 *
 * <p>The tests also check the behavior of stacked decorators (e.g., dotted + fermata).</p>
 *
 * <p>Author: [Your Name]</p>
 * <p>Version: 0.1.1</p>
 */
public class TestNoteFactory {

    /** Singleton instance of the note factory used for tests. */
    private final AbstractNoteFactory factory = NoteFactory.getINSTANCE();

    /**
     * Tests that creating a basic musical note produces a {@link MusicalNote}
     * with the correct frequency and duration.
     */
    @Test
    public void testCreateNoteMusical() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4); // A4 = 440 Hz
        NoteValue value = NoteValue.QUARTER;

        Note note = factory.createNote(pitch, value);

        assertNotNull(note);
        assertInstanceOf(MusicalNote.class, note);
        assertEquals(pitch.frequency(), note.getFrequency(), 1e-6);
        assertEquals(value.duration(120), note.getDuration(120));
    }

    /**
     * Tests that creating a rest produces a {@link Rest} note
     * with frequency 0 and correct duration.
     */
    @Test
    public void testCreateRest() {
        NoteValue value = NoteValue.HALF;

        Note rest = factory.createRest(value);

        assertNotNull(rest);
        assertInstanceOf(Rest.class, rest);
        assertEquals(0.0, rest.getFrequency());
        assertEquals(value.duration(100), rest.getDuration(100));
    }

    /**
     * Tests that creating a dotted note produces a {@link DottedNotes}
     * decorator with correct frequency (unchanged) and duration multiplied by 1.5.
     */
    @Test
    public void testCreateDottedNote() {
        Note note = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note dotted = factory.createDottedNote(note);

        assertNotNull(dotted);
        assertInstanceOf(DottedNotes.class, dotted);
        assertEquals(note.getFrequency(), dotted.getFrequency(), 1e-6);
        assertEquals((int)(note.getDuration(120) * 1.5), dotted.getDuration(120));
    }

    /**
     * Tests that creating a fermata note produces a {@link FermataOn} decorator
     * with the correct frequency (unchanged) and duration multiplied by 2.
     */
    @Test
    public void testCreateFermataOn() {
        Note note = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note fermata = factory.createFermataOn(note);

        assertNotNull(fermata);
        assertInstanceOf(FermataOn.class, fermata);
        assertEquals(note.getFrequency(), fermata.getFrequency(), 1e-6);
        assertEquals(note.getDuration(120) * 2, fermata.getDuration(120));
    }

    /**
     * Tests creating tied notes using varargs.
     * Verifies that the total duration is the sum of the durations of individual notes
     * and the frequency is that of the first note.
     */
    @Test
    public void testCreateTiedNotesVarargs() {
        Note n1 = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note n2 = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.HALF);

        Note tied = factory.createTiedNotes(n1, n2);

        assertNotNull(tied);
        assertInstanceOf(TiedNotes.class, tied);

        int expectedDuration = n1.getDuration(120) + n2.getDuration(120);
        assertEquals(expectedDuration, tied.getDuration(120));
        assertEquals(n1.getFrequency(), tied.getFrequency(), 1e-6);
    }

    /**
     * Tests creating tied notes from a list.
     * Verifies that the total duration is the sum of individual notes and frequency matches the first note.
     */
    @Test
    public void testCreateTiedNotesList() {
        Note n1 = factory.createNote(NotePitch.of(PitchClass.E, 4), NoteValue.HALF);
        Note n2 = factory.createNote(NotePitch.of(PitchClass.E, 4), NoteValue.HALF);

        Note tied = factory.createTiedNotes(List.of(n1, n2));

        assertNotNull(tied);
        assertInstanceOf(TiedNotes.class, tied);

        int expectedDuration = n1.getDuration(100) + n2.getDuration(100);
        assertEquals(expectedDuration, tied.getDuration(100));
        assertEquals(n1.getFrequency(), tied.getFrequency(), 1e-6);
    }

    /**
     * Tests the behavior of stacked decorators: a dotted note decorated with a fermata.
     * Verifies that the frequency is unchanged and the duration is correctly multiplied.
     */
    @Test
    public void testStackedDecorators() {
        Note baseNote = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note dotted = factory.createDottedNote(baseNote);
        Note fermata = factory.createFermataOn(dotted);

        assertInstanceOf(FermataOn.class, fermata);
        assertEquals(baseNote.getFrequency(), fermata.getFrequency(), 1e-6);

        int expectedDuration = (int)(baseNote.getDuration(120) * 1.5) * 2;
        assertEquals(expectedDuration, fermata.getDuration(120));
    }

    /**
     * Tests that creating tied notes using varargs with null or empty input
     * throws an {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateTiedNotesVarargsNullOrEmpty() {
        // Null input
        assertThrows(IllegalArgumentException.class, () -> factory.createTiedNotes((Note[]) null));
        // Empty input
        assertThrows(IllegalArgumentException.class, () -> factory.createTiedNotes());
    }

    /**
     * Tests that creating tied notes using a list with null or empty input
     * throws an {@link IllegalArgumentException}.
     */
    @Test
    public void testCreateTiedNotesListNullOrEmpty() {
        // Null list
        assertThrows(IllegalArgumentException.class, () -> factory.createTiedNotes((List<Note>) null));
        // Empty list
        assertThrows(IllegalArgumentException.class, () -> factory.createTiedNotes(List.of()));
    }
}
