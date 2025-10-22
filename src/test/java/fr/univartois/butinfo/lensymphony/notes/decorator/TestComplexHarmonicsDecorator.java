package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ComplexHarmonicsDecorator}.
 */
class TestComplexHarmonicsDecorator {

    /**
     * Ensures that synthesizing a note with ComplexHarmonicsDecorator
     * never returns null.
     */
    @Test
    void testNotNull() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[50];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };

        IntUnaryOperator multiplier = i -> i; // frequency multiplied by i
        BiFunction<Integer, Double, Double> amplitudeFunc = (i, time) -> 1.0; // constant amplitude

        ComplexHarmonicsDecorator decorator = new ComplexHarmonicsDecorator(base, 3, multiplier, amplitudeFunc);

        assertNotNull(decorator.synthesize(note, 120, 1.0));
    }

    /**
     * Ensures that the output array length from ComplexHarmonicsDecorator
     * matches the length of the array produced by the base synthesizer.
     */
    @Test
    void testLengthSame() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[60];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };

        IntUnaryOperator multiplier = i -> i;
        BiFunction<Integer, Double, Double> amplitudeFunc = (i, time) -> 1.0;

        ComplexHarmonicsDecorator decorator = new ComplexHarmonicsDecorator(base, 4, multiplier, amplitudeFunc);

        assertEquals(60, decorator.synthesize(note, 100, 1.0).length);
    }

    /**
     * Verifies that the constructor throws an exception if
     * the number of harmonics is less than 1.
     */
    @Test
    void testInvalidThrows() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[10];
        IntUnaryOperator multiplier = i -> i;
        BiFunction<Integer, Double, Double> amplitudeFunc = (i, time) -> 1.0;

        assertThrows(IllegalArgumentException.class,
                () -> new ComplexHarmonicsDecorator(base, 0, multiplier, amplitudeFunc));
    }

    /**
     * Verifies that the harmonics effect actually modifies the samples.
     */
    @Test
    void testHarmonicsEffectApplied() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[note.getDuration(tempo)];
        Note note = new Note() {
            public double getFrequency() { return 100; }
            public int getDuration(int t) { return 1000; }
        };

        IntUnaryOperator multiplier = i -> i;
        BiFunction<Integer, Double, Double> amplitudeFunc = (i, time) -> 1.0;

        ComplexHarmonicsDecorator decorator = new ComplexHarmonicsDecorator(base, 2, multiplier, amplitudeFunc);

        double[] original = base.synthesize(note, 120, 1.0);
        double[] decorated = decorator.synthesize(note, 120, 1.0);

        // Verify that the samples have been modified by adding harmonics
        boolean modified = false;
        for (int i = 0; i < original.length; i++) {
            if (decorated[i] != original[i]) {
                modified = true;
                break;
            }
        }
        assertTrue(modified);
    }
}
