package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.element.Rest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link NoteFactory} singleton.
 * <p>
 * These tests verify that the factory correctly creates basic musical notes
 * ({@link MusicalNote}) and rests ({@link Rest}) using the default methods
 * defined in {@link AbstractNoteFactory}.
 * </p>
 * <p>
 * Methods that are not implemented in Sprint 1, such as dotted notes and fermata,
 * are also tested to ensure they return {@code null} as expected.
 * </p>
 *
 * <p>Author: [Your Name]</p>
 * <p>Version: 0.1.0</p>
 */
public class TestNoteFactory {

    /** Singleton instance of the note factory used in the tests. */
    private final AbstractNoteFactory factory = NoteFactory.getINSTANCE();

    /**
     * Tests that {@link AbstractNoteFactory#createNote(NotePitch, NoteValue)}
     * creates a {@link MusicalNote} with the correct frequency and duration.
     */
    @Test
    public void testCreateNoteMusical() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4); // A4 = 440 Hz
        NoteValue value = NoteValue.QUARTER;

        Note note = factory.createNote(pitch, value);

        assertNotNull(note, "createNote should not return null");
        assertInstanceOf(MusicalNote.class, note, "Should return a MusicalNote instance");
        assertEquals(pitch.frequency(), note.getFrequency(), 1e-6, "Frequency should match pitch");
        assertEquals(value.duration(120), note.getDuration(120),
                "Duration should match NoteValue with given tempo");
    }

    /**
     * Tests that {@link AbstractNoteFactory#createRest(NoteValue)}
     * creates a {@link Rest} with the correct duration and zero frequency.
     */
    @Test
    public void testCreateRest() {
        NoteValue value = NoteValue.HALF;

        Note rest = factory.createRest(value);

        assertNotNull(rest, "createRest should not return null");
        assertInstanceOf(Rest.class, rest, "Should return a Rest instance");
        assertEquals(0.0, rest.getFrequency(), "Rest should have frequency 0");
        assertEquals(value.duration(100), rest.getDuration(100),
                "Duration should match NoteValue with given tempo");
    }

    /**
     * Tests that {@link AbstractNoteFactory#createDottedNote(Note)}
     * returns {@code null} as it is not implemented in Sprint 1.
     */
    @Test
    public void testCreateDottedNoteReturnsNull() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        Note note = factory.createNote(pitch, NoteValue.QUARTER);

        assertNull(factory.createDottedNote(note), "Dotted notes not implemented yet, should return null");
    }

    /**
     * Tests that {@link AbstractNoteFactory#createFermataOn(Note)}
     * returns {@code null} as it is not implemented in Sprint 1.
     */
    @Test
    public void testCreateFermataOnReturnsNull() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        Note note = factory.createNote(pitch, NoteValue.QUARTER);

        assertNull(factory.createFermataOn(note), "Fermata notes not implemented yet, should return null");
    }
}
