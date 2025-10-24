package fr.univartois.butinfo.lensymphony.notes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link NoteValue} enumeration.
 * <p>
 * These tests ensure correct behavior of {@link NoteValue#fromString(String)} and
 * {@link NoteValue#duration(int)} methods.
 */
class TestNoteValue {

    /**
     * Test from string valid types.
     */
    @Test
    @DisplayName("fromString should correctly map valid type names")
    void testFromString_ValidTypes() {
        assertEquals(NoteValue.WHOLE, NoteValue.fromString("whole"));
        assertEquals(NoteValue.HALF, NoteValue.fromString("half"));
        assertEquals(NoteValue.QUARTER, NoteValue.fromString("quarter"));
        assertEquals(NoteValue.EIGHTH, NoteValue.fromString("eighth"));
        assertEquals(NoteValue.SIXTEENTH, NoteValue.fromString("16th"));
        assertEquals(NoteValue.THIRTY_SECOND, NoteValue.fromString("32nd"));
        assertEquals(NoteValue.SIXTY_FOURTH, NoteValue.fromString("64th"));
        assertEquals(NoteValue.ONE_HUNDRED_TWENTY_EIGHTH, NoteValue.fromString("128th"));
        assertEquals(NoteValue.TWO_HUNDRED_FIFTY_SIXTH, NoteValue.fromString("256th"));
    }

    /**
     * Test from string case insensitive.
     */
    @Test
    @DisplayName("fromString should be case-insensitive")
    void testFromString_CaseInsensitive() {
        assertEquals(NoteValue.WHOLE, NoteValue.fromString("WHOLE"));
        assertEquals(NoteValue.HALF, NoteValue.fromString("HaLf"));
        assertEquals(NoteValue.EIGHTH, NoteValue.fromString("EIGHTH"));
    }

    /**
     * Test from string invalid type.
     */
    @Test
    @DisplayName("fromString should throw an exception for invalid types")
    void testFromString_InvalidType() {
        assertThrows(IllegalArgumentException.class, () -> NoteValue.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> NoteValue.fromString("1/4"));
    }

    /**
     * Test duration computation.
     */
    @Test
    @DisplayName("duration should correctly compute duration based on tempo")
    void testDuration_Computation() {
        // Tempo = 120 BPM (each beat = 500 ms)
        // Whole note = 4 beats -> 2000 ms
        assertEquals(2000, NoteValue.WHOLE.duration(120));
        assertEquals(1000, NoteValue.HALF.duration(120));
        assertEquals(500, NoteValue.QUARTER.duration(120));
        assertEquals(250, NoteValue.EIGHTH.duration(120));
        assertEquals(125, NoteValue.SIXTEENTH.duration(120));
    }

    /**
     * Test duration different tempos.
     */
    @Test
    @DisplayName("duration should adjust correctly with different tempos")
    void testDuration_DifferentTempos() {
        // At 60 BPM, 1 beat = 1000 ms, so whole note = 4000 ms
        assertEquals(4000, NoteValue.WHOLE.duration(60));
        assertEquals(2000, NoteValue.HALF.duration(60));

        // At 240 BPM, 1 beat = 250 ms, so whole note = 1000 ms
        assertEquals(1000, NoteValue.WHOLE.duration(240));
        assertEquals(500, NoteValue.HALF.duration(240));
    }

    /**
     * Test duration extreme values.
     */
    @Test
    @DisplayName("duration should return 0 for extreme tempos (sanity check)")
    void testDuration_ExtremeValues() {
        assertEquals(0, NoteValue.WHOLE.duration(Integer.MAX_VALUE));
        assertTrue(NoteValue.WHOLE.duration(1) > 0);
    }
}
