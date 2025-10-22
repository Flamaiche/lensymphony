package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * A decorator for a {@link NoteSynthesizer} that adds harmonics to the sound of a note.
 * <p>
 * This decorator enriches the synthesized sound by adding a number of harmonics.
 * Each harmonic has a lower amplitude than the base frequency.
 * </p>
 */
public class HarmonicsDecorator extends DecoratorNoteSynthesizer {

    /** Number of harmonics to add (must be greater than 1). */
    private final int nHarmonics;

    /**
     * Creates a new HarmonicsDecorator.
     *
     * @param base      The base NoteSynthesizer to decorate.
     * @param harmonics The number of harmonics to add (must be > 1).
     * @throws IllegalArgumentException if harmonics ≤ 1.
     */
    public HarmonicsDecorator(NoteSynthesizer base, int harmonics) {
        super(base);
        if (harmonics <= 1) {
            throw new IllegalArgumentException("Number of harmonics must be >= 2");
        }
        this.nHarmonics = harmonics;
    }

    /**
     * Applies the harmonic enrichment effect to the given sound samples.
     *
     * @param samples The base sound samples.
     * @param note The note being synthesized.
     * @param tempo The tempo in beats per minute (BPM).
     * @param volume The volume (0.0 to 1.0).
     * @return The samples with added harmonics.
     */
    @Override
    protected double[] applyEffect(double[] samples, Note note, int tempo, double volume) {
        int n = samples.length;
        double freq = note.getFrequency();
        double[] soundWithHarmonics = samples.clone();

        for (int i = 2; i <= nHarmonics; i++) {
            double amplitude = volume / nHarmonics;
            for (int j = 0; j < n; j++) {
                double time = j / (double) NoteSynthesizer.SAMPLE_RATE;
                soundWithHarmonics[j] += amplitude * Math.sin(2 * Math.PI * i * freq * time) / Math.sqrt(i);
            }
        }

        return soundWithHarmonics;
    }
}
