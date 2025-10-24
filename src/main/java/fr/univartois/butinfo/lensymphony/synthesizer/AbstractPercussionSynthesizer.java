package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Base class for percussion instruments.
 * <p>
 * Provides an attack/decay envelope and calls
 * {@link #computeRawSample(Note, double, int)} for the actual sound.
 * </p>
 */
public abstract class AbstractPercussionSynthesizer implements NoteSynthesizer {

    /**
     * The A.
     */
    protected final double a;
    /**
     * The D.
     */
    protected final double d;

    /**
     * Creates a percussion synthesizer with attack and decay times.
     *
     * @param a attack time in seconds
     * @param d decay time in seconds
     */
    public AbstractPercussionSynthesizer(double a, double d) {
        this.a = a;
        this.d= d;
    }
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double durationSec = note.getDuration(tempo) / 1000.0;
        int totalSamples = (int) (SAMPLE_RATE * durationSec);
        double[] samples = new double[totalSamples];

        if (note.getFrequency() == 0) {
            return samples;
        }

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            double env = envelope(t);
            samples[i] = volume * env * computeRawSample(note, t, tempo);
        }
        return samples;
    }

    /**
     * Computes the envelope value at time t.
     *
     * @param t time in seconds
     * @return envelope value
     */
    public double envelope(double t) {
        if (t < a) return t / a;
        return Math.exp((a - t) / d);
    }

    /**
     * Computes the raw sample of the instrument at time t.
     * Must be implemented by subclasses.
     *
     * @param note  the note
     * @param t     time in seconds
     * @param tempo the tempo
     * @return raw sample value
     */
    public abstract double computeRawSample(Note note, double t, int tempo);
}
