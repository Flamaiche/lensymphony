package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.Timbales;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Timbales class.
 * Tests singleton behavior, envelope computation, raw sample generation,
 * and synthesis with different notes and volume levels.
 * Author: Jabir
 */


class TestTimbales {

    /**
     * Tests that Timbales is a singleton.
     */

    @Test
    void testSingleton() {
        Timbales t1 = Timbales.getInstance();
        Timbales t2 = Timbales.getInstance();
        assertSame(t1, t2);
    }

    /**
     * Tests the computeRawSample method of the Timbales class.
     * It verifies that the generated raw audio sample for a given note and time
     * is within the expected range [-1, 1].
     */

    @Test
    void testComputeRawSample() {
        Timbales timbales = Timbales.getInstance();
        Note note = new Note() {
            @Override
            public int getDuration(int tempo) { return 1000; }
            @Override
            public double getFrequency() { return 440; }
        };

        double sample = timbales.computeRawSample(note, 0.5);
        assertTrue(sample >= -1 && sample <= 1);
    }

    /**
     * Tests the envelope method of the Timbales class.
     * Verifies that the envelope correctly computes both the attack and decay phases:
     * - For attack (t < a), the envelope should linearly scale as t / a.
     * - For decay (t >= a), the envelope should follow the exponential decay formula.
     */

    @Test
    void testEnvelope() {
        Timbales timbales = Timbales.getInstance();

        double attack = timbales.envelope(0.005);
        assertEquals(0.005 / 0.01, attack, 1e-12);

        double decay = timbales.envelope(0.05);
        assertEquals(Math.exp((0.01 - 0.05) / 0.2), decay, 1e-12);
    }
    /**
     * Tests the synthesize method of the Timbales class.
     * Verifies that the generated audio samples for a given note:
     * - Produce an array of the expected length based on the note duration.
     * - All sample values remain within the valid range [-1, 1].
     */

    @Test
    void testSynthesize() {
        Timbales timbales = Timbales.getInstance();
        Note note = new Note() {
            @Override
            public int getDuration(int tempo) { return 500; }
            @Override
            public double getFrequency() { return 440; }
        };

        double[] samples = timbales.synthesize(note, 120, 1.0);
        assertEquals((int)(Timbales.SAMPLE_RATE * 0.5), samples.length);
        for (double s : samples) {
            assertTrue(s >= -1 && s <= 1);
        }
    }
}
