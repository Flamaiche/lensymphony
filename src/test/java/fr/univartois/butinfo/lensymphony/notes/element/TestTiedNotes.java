package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTiedNotes {

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

}
