package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * A decorator for {@link NoteSynthesizer} that applies an ADSR envelope to the synthesized note.
 * <p>
 * ADSR stands for Attack, Decay, Sustain, and Release, representing the four phases of a note's amplitude:
 * <ul>
 *     <li><b>Attack (a):</b> The initial increase in volume at the start of the note.</li>
 *     <li><b>Decay (d):</b> The decrease in volume following the attack down to the sustain level.</li>
 *     <li><b>Sustain (s):</b> The steady amplitude level maintained for most of the note's duration.</li>
 *     <li><b>Release (r):</b> The decrease in volume at the end of the note.</li>
 * </ul>
 * </p>
 * <p>
 * This decorator multiplies each sample of the base synthesized note by the ADSR envelope,
 * resulting in a more realistic and expressive sound.
 * </p>
 */
public class AdsrDecorator extends DecoratorNoteSynthesizer {

    /** Attack duration in seconds. */
    private final double a;

    /** Decay duration in seconds. */
    private final double d;

    /** Sustain level (0.0 to 1.0). */
    private final double s;

    /** Release duration in seconds. */
    private final double r;

    /**
     * Creates a new {@code AdsrDecorator} for the specified base synthesizer.
     *
     * @param base The base {@link NoteSynthesizer} to decorate.
     * @param a    Attack duration in seconds.
     * @param d    Decay duration in seconds.
     * @param s    Sustain level (0.0 to 1.0).
     * @param r    Release duration in seconds.
     */
    public AdsrDecorator(NoteSynthesizer base, double a, double d, double s, double r) {
        super(base);
        this.a = a;
        this.d = d;
        this.s = s;
        this.r = r;
    }

    /**
     * Applies the ADSR envelope to the base synthesized samples.
     *
     * @param samples The array of samples produced by the base synthesizer.
     * @param note    The {@link Note} being synthesized.
     * @param tempo   The tempo in beats per minute (BPM).
     * @param volume  The base volume (0.0 to 1.0).
     * @return A new array of samples with the ADSR envelope applied.
     */
    @Override
    protected double[] applyEffect(double[] samples, Note note, int tempo, double volume) {
        double duration = note.getDuration(tempo) / 1000.0; // convert milliseconds to seconds
        int n = samples.length;
        double[] output = new double[n];

        for (int i = 0; i < n; i++) {
            double t = i / (double) NoteSynthesizer.SAMPLE_RATE;
            double envelope = getEnvelopeValue(t, duration);
            output[i] = samples[i] * envelope;
        }

        return output;
    }

    /**
     * Calculates the ADSR envelope value at a given point in time.
     *
     * @param currentTime The time of the current sample in seconds.
     * @param totalTime   The total duration of the note in seconds.
     * @return The envelope multiplier (0.0 to 1.0) for the current sample.
     */
    private double getEnvelopeValue(double currentTime, double totalTime) {
        if (currentTime < 0 || currentTime > totalTime) {
            return 0.0;
        } else if (currentTime < a) {
            return currentTime / a;
        } else if (currentTime < a + d) {
            return 1 - ((currentTime - a) / d) * (1 - s);
        } else if (currentTime < totalTime - r) {
            return s;
        } else {
            return s * (1 - (currentTime - (totalTime - r)) / r);
        }
    }
}
