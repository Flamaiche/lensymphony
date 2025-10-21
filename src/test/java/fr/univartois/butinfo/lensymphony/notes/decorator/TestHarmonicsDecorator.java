package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for note synthesizer decorators,
 * specifically {@link HarmonicsDecorator} and {@link DecoratorNoteSynthesizer}.
 * <p>
 * These tests verify the basic behavior of decorators:
 * - the synthesized output is not null,
 * - the output array has the correct length,
 * - exceptions are thrown for invalid input,
 * - synthesis is properly delegated to the base synthesizer.
 * </p>
 */
public class TestHarmonicsDecorator {

    /**
     * Ensures that synthesizing a note with {@link HarmonicsDecorator}
     * never returns null.
     */
    @Test
    void testNotNull() {
        NoteSynthesizer base = (n, t, v) -> new double[50];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };
        HarmonicsDecorator d = new HarmonicsDecorator(base, 5);
        assertNotNull(d.synthesize(note, 120, 1.0));
    }

    /**
     * Ensures that the output array length from {@link HarmonicsDecorator}
     * matches the length of the array produced by the base synthesizer.
     */
    @Test
    void testLengthSame() {
        NoteSynthesizer base = (n, t, v) -> new double[60];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };
        HarmonicsDecorator d = new HarmonicsDecorator(base, 3);
        assertEquals(60, d.synthesize(note, 100, 1.0).length);
    }

    /**
     * Verifies that the constructor of {@link HarmonicsDecorator}
     * throws an exception if the number of harmonics is less than or equal to 1.
     */
    @Test
    void testInvalidThrows() {
        NoteSynthesizer base = (n, t, v) -> new double[10];
        assertThrows(IllegalArgumentException.class, () -> new HarmonicsDecorator(base, 1));
    }

    /**
     * Ensures that {@link DecoratorNoteSynthesizer} correctly delegates
     * the synthesize call to the base synthesizer.
     */
    @Test
    void testBaseSynthesizeCalled() {
        NoteSynthesizer base = (n, t, v) -> new double[]{1.0, 2.0, 3.0};
        DecoratorNoteSynthesizer decorator = new DecoratorNoteSynthesizer(base) {};
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };

        double[] result = decorator.synthesize(note, 120, 0.5);

        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, result);
    }
}
