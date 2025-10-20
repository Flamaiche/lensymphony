package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMusicalNote {

    @Test
    void testCreateNote() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        NoteValue value = NoteValue.QUARTER;
        MusicalNote note = new MusicalNote(pitch, value);

        assertEquals(pitch.frequency(), note.getFrequency(), 0.001);
        assertEquals(value.duration(120), note.getDuration(120));
    }

    @Test
    void testDifferentNoteValues() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4);
        MusicalNote note1 = new MusicalNote(pitch, NoteValue.HALF);
        MusicalNote note2 = new MusicalNote(pitch, NoteValue.WHOLE);

        assertTrue(note2.getDuration(120) > note1.getDuration(120));
    }

    @Test
    void testGetFrequency() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        MusicalNote note = new MusicalNote(pitch, NoteValue.QUARTER);

        assertEquals(pitch.frequency(), note.getFrequency(), 0.001,
                "getFrequency() did not return the correct frequency from NotePitch");
    }

    @Test
    void testGetDuration() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4);
        MusicalNote note = new MusicalNote(pitch, NoteValue.HALF);
        int tempo = 120;

        assertEquals(NoteValue.HALF.duration(tempo), note.getDuration(tempo),
                "getDuration() did not return the correct duration from NoteValue");

    }

}
