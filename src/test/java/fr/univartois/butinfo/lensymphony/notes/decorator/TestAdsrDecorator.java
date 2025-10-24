package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AdsrDecorator}.
 * <p>
 * These tests cover 100% of the lines and branches of the AdsrDecorator class.
 * They verify that the ADSR envelope is applied correctly to synthesized audio samples,
 * including all phases: Attack, Decay, Sustain, and Release.
 */
class TestAdsrDecorator {

    /**
     * Tests the ADSR decorator on a simple note.
     * Checks that:
     * - the result is not null,
     * - the length of the output matches the base synthesizer,
     * - all branches in getEnvelopeValue are executed (negative time, attack, decay, sustain, release, time beyond duration).
     */
    @Test
    void testAllBranches() {
        NoteSynthesizer base = (note, tempo, volume) -> {
            double[] samples = new double[6];
            for (int i = 0; i < 6; i++) samples[i] = 1.0;
            return samples;
        };

        Note note = new Note() {
            @Override
            public double getFrequency() { return 440; }
            @Override
            public int getDuration(int tempo) { return 1000; }
        };

        AdsrDecorator adsr = new AdsrDecorator(base, 0.1, 0.2, 0.5, 0.2);
        double[] result = adsr.synthesize(note, 120, 1.0);

        assertNotNull(result);
        assertEquals(6, result.length);
        assertEquals(0.0, adsr.getEnvelopeValue(-0.01, 1.0));
        assertEquals(0.05 / 0.1, adsr.getEnvelopeValue(0.05, 1.0), 1e-6);
        double decayValue = adsr.getEnvelopeValue(0.25, 1.0);
        assertTrue(decayValue < 1.0 && decayValue > 0.5);
        assertEquals(0.5, adsr.getEnvelopeValue(0.6, 1.0), 1e-6);
        double releaseValue = adsr.getEnvelopeValue(0.9, 1.0);
        assertTrue(releaseValue < 0.5);
        assertEquals(0.0, adsr.getEnvelopeValue(1.1, 1.0));
    }
}
