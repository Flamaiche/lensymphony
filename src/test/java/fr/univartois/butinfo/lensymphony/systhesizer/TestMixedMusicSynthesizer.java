package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.Score;
import fr.univartois.butinfo.lensymphony.Staff;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;
import fr.univartois.butinfo.lensymphony.synthesizer.MixedMusicSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MixedMusicSynthesizer} class.
 * <p>
 * These tests verify that the class correctly handles tempo, synthesis triggering,
 * sample mixing across multiple voices, and edge cases such as empty or uneven
 * sample arrays. The tests use a simple {@link FakeSynthesizer} class instead of
 * relying on external mocking frameworks.
 * </p>
 *
 * <p>
 * Author: Babahamou Malik
 * </p>
 */
class MixedMusicSynthesizerTest {

    /**
     * A simple fake implementation of {@link MusicSynthesizer} for testing purposes.
     * It provides predefined sample data and tracks whether the synthesize method
     * has been called.
     */
    private static class FakeSynthesizer implements MusicSynthesizer {
        private final double[] samples;
        private final int tempo;
        private boolean synthesized = false;

        public FakeSynthesizer(double[] samples, int tempo) {
            this.samples = samples;
            this.tempo = tempo;
        }

        @Override
        public void synthesize() {
            synthesized = true;
        }

        @Override
        public double[] getSamples() {
            return samples;
        }

        @Override
        public int getTempo() {
            return tempo;
        }

        public boolean isSynthesized() {
            return synthesized;
        }
    }

    /**
     * Ensures that the tempo returned by {@link MixedMusicSynthesizer#getTempo()}
     * matches the value passed to the constructor.
     */
    @Test
    void testGetTempoReturnsConstructorValue() {
        MixedMusicSynthesizer mixed = new MixedMusicSynthesizer(new Score(List.of()), 180);
        assertEquals(180, mixed.getTempo(), "Tempo should match constructor parameter");
    }

    /**
     * Ensures that calling {@link MixedMusicSynthesizer#getSamples()} on an empty
     * synthesizer list returns an empty array.
     */
    @Test
    void testEmptySynthesizerListReturnsEmptyArray() {
        MixedMusicSynthesizer mixed = new MixedMusicSynthesizer(new Score(List.of()), 120);
        double[] samples = mixed.getSamples();
        assertEquals(0, samples.length, "Should return empty array when no voices are present");
    }

    /**
     * Verifies that {@link MixedMusicSynthesizer#synthesize()} calls the synthesize
     * method of each internal {@link MusicSynthesizer}.
     */
    @Test
    void testSynthesizeCallsAllInternalSynthesizers() throws Exception {
        Instrument instrument = Instrument.PURE_TONE;
        Staff staff1 = new Staff(instrument);
        Staff staff2 = new Staff(instrument);

        Score score = new Score(List.of(staff1, staff2));
        MixedMusicSynthesizer mixed = new MixedMusicSynthesizer(score, 100);

        // Replace internal synthesizers list using reflection
        var field = MixedMusicSynthesizer.class.getDeclaredField("synthesizers");
        field.setAccessible(true);

        FakeSynthesizer fake1 = new FakeSynthesizer(new double[]{1.0, 2.0}, 100);
        FakeSynthesizer fake2 = new FakeSynthesizer(new double[]{3.0, 4.0}, 100);
        List<MusicSynthesizer> fakes = new ArrayList<>(List.of(fake1, fake2));

        field.set(mixed, fakes);

        mixed.synthesize();

        assertTrue(fake1.isSynthesized(), "First synthesizer should have been synthesized");
        assertTrue(fake2.isSynthesized(), "Second synthesizer should have been synthesized");
    }

    /**
     * Verifies that {@link MixedMusicSynthesizer#getSamples()} correctly combines
     * and averages samples across multiple voices.
     */
    @Test
    void testGetSamplesCombinesAndAveragesCorrectly() throws Exception {
        FakeSynthesizer fake1 = new FakeSynthesizer(new double[]{1.0, 2.0, 3.0}, 100);
        FakeSynthesizer fake2 = new FakeSynthesizer(new double[]{3.0, 2.0, 1.0, 0.0}, 100);

        MixedMusicSynthesizer mixed = new MixedMusicSynthesizer(new Score(List.of()), 100);

        var field = MixedMusicSynthesizer.class.getDeclaredField("synthesizers");
        field.setAccessible(true);
        field.set(mixed, List.of(fake1, fake2));

        double[] result = mixed.getSamples();

        assertEquals(4, result.length, "Result length should equal the longest input array");
        assertArrayEquals(new double[]{2.0, 2.0, 2.0, 0.0}, result, 1e-9,
                "Samples should be averaged across all synthesizers");
    }

    /**
     * Verifies that {@link MixedMusicSynthesizer#getSamples()} handles voices
     * of different lengths correctly, filling missing samples with zeros.
     */
    @Test
    void testUnevenVoiceLengthsHandledGracefully() throws Exception {
        FakeSynthesizer fake1 = new FakeSynthesizer(new double[]{1.0, 2.0}, 100);
        FakeSynthesizer fake2 = new FakeSynthesizer(new double[]{2.0, 4.0, 6.0}, 100);

        MixedMusicSynthesizer mixed = new MixedMusicSynthesizer(new Score(List.of()), 100);

        var field = MixedMusicSynthesizer.class.getDeclaredField("synthesizers");
        field.setAccessible(true);
        field.set(mixed, List.of(fake1, fake2));

        double[] result = mixed.getSamples();

        assertEquals(3, result.length, "Result should have the length of the longest voice");
        assertArrayEquals(new double[]{1.5, 3.0, 3.0}, result, 1e-9,
                "Samples should be averaged correctly even with uneven lengths");
    }
}
