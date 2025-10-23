package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.SnareDrum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SnareDrum class.
 * Tests singleton behavior, envelope computation, raw sample generation,
 * and synthesis with zero and non-zero frequency notes.
 * Author: Jabir
 */
class TestSnarDrum {

    /**
     * Tests that SnareDrum is a singleton.
     */
    @Test
    void testSingletonInstance() {
        SnareDrum drum1 = SnareDrum.getInstance();
        SnareDrum drum2 = SnareDrum.getInstance();
        assertSame(drum1, drum2, "SnareDrum should be a singleton instance");
    }

    /**
     * Tests that raw samples are within the range [-1, 1].
     */
    @Test
    void testComputeRawSampleRange() {
        SnareDrum drum = SnareDrum.getInstance();
        Note stubNote = new Note() {
            @Override
            public int getDuration(int tempo) { return 500; }
            @Override
            public double getFrequency() { return 1; }
        };
        for (int i = 0; i < 100; i++) {
            int tempo = 120;
            double sample = drum.computeRawSample(stubNote, i / 1000.0, tempo);

            assertTrue(sample >= -1.0 && sample <= 1.0, "Raw sample out of range");
        }
    }

    /**
     * Tests the envelope computation for attack (t < a) and decay (t >= a) phases.
     */
    @Test
    void testEnvelopeAttackAndDecay() {
        SnareDrum drum = SnareDrum.getInstance();
        double a = 0.01;

        double tAttack = 0.005;
        assertEquals(tAttack / a, drum.envelope(tAttack), 1e-6, "Envelope attack incorrect");

        double tDecay = 0.02;
        assertEquals(Math.exp(15 * (a - tDecay)), drum.envelope(tDecay), 1e-6, "Envelope decay incorrect");
    }

    /**
     * Tests synthesis for a note with non-zero frequency.
     * The output array should have the correct length.
     */
    @Test
    void testSynthesizeWithFrequencyGreaterThanZero() {
        SnareDrum drum = SnareDrum.getInstance();
        Note stubNote = new Note() {
            @Override
            public int getDuration(int tempo) { return 500; }
            @Override
            public double getFrequency() { return 1; }
        };
        double[] samples = drum.synthesize(stubNote, 120, 1.0);
        assertEquals((int)(NoteSynthesizer.SAMPLE_RATE * 0.5), samples.length, "Synthesized sample length mismatch");
    }

    /**
     * Tests synthesis for a note with zero frequency.
     * All samples should be zero.
     */
    @Test
    void testSynthesizeWithZeroFrequency() {
        SnareDrum drum = SnareDrum.getInstance();
        Note silentNote = new Note() {
            @Override
            public int getDuration(int tempo) { return 500; }
            @Override
            public double getFrequency() { return 0; }
        };
        double[] samples = drum.synthesize(silentNote, 120, 1.0);
        for (double s : samples) {
            assertEquals(0.0, s, 1e-10, "Sample should be zero for zero frequency note");
        }
    }

    /**
     * Tests that volume scaling is applied correctly during synthesis.
     */
    @Test
    void testSynthesizeVolumeScaling() {
        SnareDrum drum = SnareDrum.getInstance();
        Note stubNote = new Note() {
            @Override
            public int getDuration(int tempo) { return 500; }
            @Override
            public double getFrequency() { return 1; }
        };

        double volume = 0.5;
        double[] samples = drum.synthesize(stubNote, 120, volume);

        for (double s : samples) {
            assertTrue(s >= -volume && s <= volume, "Sample exceeds expected volume range");
        }
    }
}
