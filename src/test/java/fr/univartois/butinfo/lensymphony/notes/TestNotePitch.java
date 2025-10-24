package fr.univartois.butinfo.lensymphony.notes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link NotePitch} class.
 *
 * <p>
 * These tests ensure NotePitch computes frequencies correctly using equal temperament,
 * handles semitone alterations (sharp/flat) including octave transitions, reuses cached
 * instances for identical parameters, and throws on invalid octave ranges.
 * </p>
 *
 * @author
 * @version 1.0
 */
class TestNotePitch {

    /**
     * Test reference pitch.
     */
    @Test
    @DisplayName("A4 should have a frequency of 440 Hz")
    void testReferencePitch() {
        NotePitch a4 = NotePitch.of(PitchClass.A, 4);
        assertEquals(440.0, a4.frequency(), 1e-9, "A4 must be 440 Hz");
    }

    /**
     * Test equal temperament ratio.
     */
    @Test
    @DisplayName("Frequencies should follow equal temperament ratios (semitone ratio)")
    void testEqualTemperamentRatio() {
        NotePitch a4 = NotePitch.of(PitchClass.A, 4);
        NotePitch aSharp4 = NotePitch.of(PitchClass.A, 4, 1); // A#4
        double expectedRatio = Math.pow(2.0, 1.0 / 12.0);
        double actualRatio = aSharp4.frequency() / a4.frequency();
        assertEquals(expectedRatio, actualRatio, 1e-6, "Consecutive semitone ratio must be 2^(1/12)");
    }

    /**
     * Test sharp and flat change frequency.
     */
    @Test
    @DisplayName("Sharp and flat should change frequency in expected direction")
    void testSharpAndFlatChangeFrequency() {
        NotePitch c4 = NotePitch.of(PitchClass.C, 4);
        NotePitch cSharp4 = c4.sharp();
        NotePitch b3 = c4.flat();

        assertTrue(cSharp4.frequency() > c4.frequency(), "C#4 should be higher than C4");
        assertTrue(b3.frequency() < c4.frequency(), "B3 should be lower than C4");
    }

    /**
     * Test octave transition on sharp.
     */
    @Test
    @DisplayName("Altering across octave boundaries should produce expected pitch")
    void testOctaveTransitionOnSharp() {
        NotePitch b4 = NotePitch.of(PitchClass.B, 4);
        NotePitch b4Sharp = b4.sharp(); // B4 + 1 semitone -> C5
        NotePitch c5 = NotePitch.of(PitchClass.C, 5);

        assertEquals(c5.frequency(), b4Sharp.frequency(), 1e-9,
                "B4 sharp must equal C5 frequency (octave transition)");
    }

    /**
     * Test octave transition on flat.
     */
    @Test
    @DisplayName("Flat across octave underflow should produce expected pitch")
    void testOctaveTransitionOnFlat() {
        NotePitch c4 = NotePitch.of(PitchClass.C, 4);
        NotePitch c4Flat = c4.flat(); // C4 - 1 semitone -> B3
        NotePitch b3 = NotePitch.of(PitchClass.B, 3);

        assertEquals(b3.frequency(), c4Flat.frequency(), 1e-9,
                "C4 flat must equal B3 frequency (octave underflow)");
    }

    /**
     * Test caching behavior.
     */
    @Test
    @DisplayName("of() should return same instance for identical pitch class + octave")
    void testCachingBehavior() {
        NotePitch first = NotePitch.of(PitchClass.C, 4);
        NotePitch second = NotePitch.of(PitchClass.C, 4);
        assertSame(first, second, "of() should reuse cached instances for identical inputs");
    }

    /**
     * Test invalid octave throws.
     */
    @Test
    @DisplayName("Requesting a pitch outside octave bounds should throw")
    void testInvalidOctaveThrows() {
        // NB_OCTAVES is 9 in implementation (0..8). Use an out-of-range octave to provoke the exception.
        assertThrows(IllegalArgumentException.class, () -> NotePitch.of(PitchClass.C, -1),
                "Negative octave must throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> NotePitch.of(PitchClass.C, 9),
                "Too high octave must throw IllegalArgumentException");
    }
}
