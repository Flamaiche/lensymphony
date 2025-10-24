package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
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
     * Tests that computeRawSample generates a value within [-1, 1].
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

        int tempo = 120;
        double sample = timbales.computeRawSample(note, 0.5, tempo);
        assertTrue(sample >= -1 && sample <= 1);
    }

    /**
     * Tests that computeRawSample returns 0.0 when duration is zero or negative.
     */
    @Test
    void testComputeRawSampleWithZeroOrNegativeDuration() {
        Timbales timbales = Timbales.getInstance();

        Note zeroDuration = new Note() {
            @Override
            public int getDuration(int tempo) { return 0; }
            @Override
            public double getFrequency() { return 440; }
        };

        Note negativeDuration = new Note() {
            @Override
            public int getDuration(int tempo) { return -100; }
            @Override
            public double getFrequency() { return 440; }
        };

        int tempo = 120;

        double sampleZero = timbales.computeRawSample(zeroDuration, 0.1, tempo);
        double sampleNegative = timbales.computeRawSample(negativeDuration, 0.1, tempo);

        assertEquals(0.0, sampleZero, 1e-12);
        assertEquals(0.0, sampleNegative, 1e-12);
    }

    /**
     * Tests the envelope computation for attack and decay phases.
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
     * Tests that synthesize generates valid samples in [-1, 1].
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
        assertEquals((int)(NoteSynthesizer.SAMPLE_RATE * 0.5), samples.length);
        for (double s : samples) {
            assertTrue(s >= -1 && s <= 1);
        }
    }
}
