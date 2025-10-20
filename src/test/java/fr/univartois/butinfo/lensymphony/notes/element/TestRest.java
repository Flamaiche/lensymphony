package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestRest {
    @Test
    void testRestNotNull() {
        Rest rest = new Rest(NoteValue.QUARTER);
        assertNotNull(rest, "The rest object must not be null");
    }

    @Test
    void testFrequencyIsZero() {
        Rest rest = new Rest(NoteValue.QUARTER);
        assertEquals(0.0, rest.getFrequency(), 0.0001,
                "Frequency of a rest should always be 0.0");
    }

    @Test
    void testDurationMatchesNoteValue() {
        Rest rest = new Rest(NoteValue.QUARTER);
        int tempo = 120;
        int expectedDuration = NoteValue.QUARTER.duration(tempo);
        assertEquals(expectedDuration, rest.getDuration(tempo),
                "Duration should match the NoteValue and tempo");
    }



}
