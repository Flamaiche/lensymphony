package fr.univartois.butinfo.lensymphony.notes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PitchClass} enumeration.
 *
 * These tests verify the mapping behavior of {@link PitchClass#fromName(String)}.
 */
class TestPitchClass {

    @Test
    @DisplayName("Valid pitch names are correctly mapped")
    void testFromName_ValidNotes() {
        assertEquals(PitchClass.C, PitchClass.fromName("C"));
        assertEquals(PitchClass.C_SHARP_D_FLAT, PitchClass.fromName("C#"));
        assertEquals(PitchClass.C_SHARP_D_FLAT, PitchClass.fromName("DB"));
        assertEquals(PitchClass.D, PitchClass.fromName("D"));
        assertEquals(PitchClass.D_SHARP_E_FLAT, PitchClass.fromName("D#"));
        assertEquals(PitchClass.D_SHARP_E_FLAT, PitchClass.fromName("EB"));
        assertEquals(PitchClass.E, PitchClass.fromName("E"));
        assertEquals(PitchClass.F, PitchClass.fromName("F"));
        assertEquals(PitchClass.F_SHARP_G_FLAT, PitchClass.fromName("F#"));
        assertEquals(PitchClass.F_SHARP_G_FLAT, PitchClass.fromName("GB"));
        assertEquals(PitchClass.G, PitchClass.fromName("G"));
        assertEquals(PitchClass.G_SHARP_A_FLAT, PitchClass.fromName("G#"));
        assertEquals(PitchClass.G_SHARP_A_FLAT, PitchClass.fromName("AB"));
        assertEquals(PitchClass.A, PitchClass.fromName("A"));
        assertEquals(PitchClass.A_SHARP_B_FLAT, PitchClass.fromName("A#"));
        assertEquals(PitchClass.A_SHARP_B_FLAT, PitchClass.fromName("BB"));
        assertEquals(PitchClass.B, PitchClass.fromName("B"));
    }

    @Test
    @DisplayName("Method is case-insensitive")
    void testFromName_CaseInsensitive() {
        assertEquals(PitchClass.C, PitchClass.fromName("c"));
        assertEquals(PitchClass.C_SHARP_D_FLAT, PitchClass.fromName("c#"));
        assertEquals(PitchClass.C_SHARP_D_FLAT, PitchClass.fromName("db".toUpperCase())); // equivalent à "DB"
        assertEquals(PitchClass.G_SHARP_A_FLAT, PitchClass.fromName("g#"));
    }

    @Test
    @DisplayName("Invalid pitch names throw IllegalArgumentException")
    void testFromName_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> PitchClass.fromName("H"));
        assertThrows(IllegalArgumentException.class, () -> PitchClass.fromName("Z#"));
    }

    @Test
    @DisplayName("Null input throws NullPointerException")
    void testFromName_NullInput() {
        assertThrows(NullPointerException.class, () -> PitchClass.fromName(null));
    }
}
