package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.CymbalSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CymbalSynthesizer}.
 * <p>
 * These tests verify the correct behavior of both the {@code computeRawSample}
 * and {@code synthesize} methods, including handling of normal notes and rests.
 * </p>
 */
class TestCymbalSynthesizer {

    /**
     * Dummy implementation of {@link Note} used for testing.
     * Returns a fixed frequency and duration.
     */
    static class DummyNote implements Note {
        @Override
        public double getFrequency() {
            return 440; // La fréquence n’est pas importante pour la cymbale
        }

        @Override
        public int getDuration(int tempo) {
            return 500; // 500 ms
        }
    }

    /**
     * Tests that {@link CymbalSynthesizer#computeRawSample(Note, double)} returns
     * a value within the expected range for a given time.
     */
    @Test
    void testComputeRawSample() {
        CymbalSynthesizer synth = CymbalSynthesizer.getINSTANCE();
        Note note = new DummyNote();

        double t = 0.01;
        double sample = synth.computeRawSample(note, t);

        double maxPossible = Math.sin(4000 * Math.PI * t);
        assertTrue(sample >= -Math.abs(maxPossible) && sample <= Math.abs(maxPossible));
    }

    /**
     * Tests that {@link CymbalSynthesizer#synthesize(Note, int, double)} generates
     * a non-null, non-empty array of samples with values in the expected range.
     */
    @Test
    void testSynthesize() {
        CymbalSynthesizer synth = CymbalSynthesizer.getINSTANCE();
        Note note = new DummyNote();

        double[] samples = synth.synthesize(note, 120, 0.5);

        assertNotNull(samples);
        assertTrue(samples.length > 0);

        for (double s : samples) {
            assertTrue(s >= -0.5 && s <= 0.5);
        }
    }

    /**
     * Tests that {@link CymbalSynthesizer#synthesize(Note, int, double)} correctly
     * handles a rest (note with frequency 0) by returning an array filled with zeros.
     */
    @Test
    void testSynthesizeWithRest() {
        CymbalSynthesizer synth = CymbalSynthesizer.getINSTANCE();

        Note restNote = new DummyNote() {
            @Override
            public double getFrequency() {
                return 0; // Silence
            }
        };

        double[] samples = synth.synthesize(restNote, 120, 0.5);

        for (double s : samples) {
            assertEquals(0, s);
        }
    }
}
