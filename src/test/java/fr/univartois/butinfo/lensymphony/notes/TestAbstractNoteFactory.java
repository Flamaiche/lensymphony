package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.element.Rest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAbstractNoteFactory {

    // Creating an instance to test default methods
    private final AbstractNoteFactory factory = new AbstractNoteFactory() {

        // Override methode with no-default temp. To have no error

        @Override
        public Note createDottedNote(Note note) {
            return null;
        }

        @Override
        public Note createFermataOn(Note note) {
            return null;
        }

        @Override
        public Note createTiedNotes(List<Note> notes) {
            return null;
        }
    };

    @Test
    public void testCreateNoteMusical() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4); // A4
        NoteValue value = NoteValue.QUARTER;

        Note note = factory.createNote(pitch, value);

        assertNotNull(note, "createNote should not return null");
        assertTrue(note instanceof MusicalNote, "Should return a MusicalNote instance");
        assertEquals(pitch.frequency(), note.getFrequency(), 1e-6, "Frequency should match pitch");
        assertEquals(value.duration(120), note.getDuration(120), "Duration should match NoteValue with tempo");
    }

    @Test
    public void testCreateRest() {
        NoteValue value = NoteValue.HALF;

        Note rest = factory.createRest(value);

        assertNotNull(rest, "createRest should not return null");
        assertTrue(rest instanceof Rest, "Should return a Rest instance");
        assertEquals(0.0, rest.getFrequency(), "Rest should have frequency 0");
        assertEquals(value.duration(100), rest.getDuration(100), "Duration should match NoteValue with tempo");
    }
}
