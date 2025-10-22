package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the VibratoDecorator class.
 * Tests the behavior of VibratoDecorator, including vibrato application,
 * constructor parameter handling, and edge cases.
 * Author: Jabir
 */
public class TestVibrato {

    /**
     * Tests that the vibrato effect modifies the samples produced
     * by the base synthesizer. Ensures that the resulting samples
     * are not all identical to the original base samples.
     */

    @Test
    void testVibratoEffectApplied() {
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };

        NoteSynthesizer baseSynth = (n, tempo, volume) -> new double[]{0.5, 0.5, 0.5, 0.5, 0.5};

        VibratoDecorator vibrato = new VibratoDecorator(baseSynth, 0.01, 5);
        double[] samplesWithVibrato = vibrato.synthesize(note, 120, 1.0);

        boolean allSame = true;
        for (double s : samplesWithVibrato) {
            if (s != 0.5) {
                allSame = false;
                break;
            }
        }
        assertFalse(allSame, "Vibrato should modify the samples");
    }

    /**
     * Tests that the default constructor of VibratoDecorator
     * sets the depth and speed to 5.
     */

    @Test
    void testDefaultConstructorValues() {
        NoteSynthesizer baseSynth = (n, tempo, volume) -> new double[]{1.0};
        VibratoDecorator vibrato = new VibratoDecorator(baseSynth);
        assertEquals(5, vibrato.getDepth(), 0.001);
        assertEquals(5, vibrato.getSpeed(), 0.001);
    }

    /**
     * Tests the getter methods of VibratoDecorator for depth and speed.
     */

    @Test
    void testGetters() {
        NoteSynthesizer baseSynth = (n, tempo, volume) -> new double[]{1.0};
        VibratoDecorator vibrato = new VibratoDecorator(baseSynth, 0.02, 10);
        assertEquals(0.02, vibrato.getDepth(), 0.001);
        assertEquals(10, vibrato.getSpeed(), 0.001);
    }

    /**
     * Tests that VibratoDecorator correctly handles an empty
     * samples array produced by the base synthesizer.
     * The result should also be an empty array.
     */

    @Test
    void testEmptySamples() {
        NoteSynthesizer baseSynth = (n, tempo, volume) -> new double[]{};
        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };
        VibratoDecorator vibrato = new VibratoDecorator(baseSynth, 0.01, 5);
        double[] result = vibrato.synthesize(note, 120, 1.0);
        assertEquals(0, result.length, "Empty samples should remain empty");
    }
}
