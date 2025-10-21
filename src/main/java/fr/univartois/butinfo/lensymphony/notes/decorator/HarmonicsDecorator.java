package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * A decorator for a NoteSynthesizer that adds harmonics to the sound of a note.
 * <p>
 * The decorator adds multiple harmonics to enrich the note's sound.
 * Each harmonic has a lower amplitude than the fundamental frequency.
 * </p>
 */
public class HarmonicsDecorator extends DecoratorNoteSynthesizer {

    /** Number of harmonics to add (must be greater than 1). */
    private final int nHarmonics;

    /**
     * Creates a HarmonicsDecorator that adds harmonics to a base synthesizer.
     *
     * @param base the base NoteSynthesizer to decorate
     * @param harmonics the number of harmonics to add (must be > 1)
     * @throws IllegalArgumentException if harmonics is less than or equal to 1
     */
    public HarmonicsDecorator(NoteSynthesizer base, int harmonics) {
        super(base);
        if (harmonics <= 1) {
            throw new IllegalArgumentException("Number of harmonics must be >= 2");
        }
        this.nHarmonics = harmonics;
    }

    /**
     * Synthesizes a note including the specified number of harmonics.
     *
     * @param note the note to synthesize
     * @param tempo the tempo in beats per minute (BPM)
     * @param volume the volume (0.0 to 1.0)
     * @return an array of audio samples including the harmonics
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] baseSound = base.synthesize(note, tempo, volume);
        int n = baseSound.length;
        double freq = note.getFrequency();
        double[] soundWithHarmonics = baseSound.clone();

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
