package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * CymbalSynthesizer is a singleton class that generates the sound of a cymbal.
 * <p>
 * The sound is produced using high-frequency noise modulated by an exponential
 * envelope. This class extends {@link AbstractPercussionSynthesizer} to reuse
 * the attack/decay envelope logic.
 * </p>
 *
 * <p>The raw sound formula is:</p>
 * <pre>
 * s(t) = random(-1, 1) * sin(4000 * π * t)
 * </pre>
 *
 * <p>The envelope parameters are set as {@code a = 0.02} for attack and {@code d = 0.04} for decay.</p>
 *
 * <p>This class uses the Singleton pattern to ensure only one instance exists.</p>
 */
public class CymbalSynthesizer extends AbstractPercussionSynthesizer {

    /** The single instance of CymbalSynthesizer. */
    private static final CymbalSynthesizer INSTANCE = new CymbalSynthesizer();

    /**
     * Private constructor to prevent instantiation outside the class.
     * Initializes the attack and decay values for the cymbal sound.
     */
    private CymbalSynthesizer() {
        super(0.02, 0.04);
    }

    /**
     * Returns the singleton instance of {@link CymbalSynthesizer}.
     *
     * @return the single instance of CymbalSynthesizer
     */
    public static CymbalSynthesizer getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Computes the raw sound sample at a given time for the cymbal.
     * <p>
     * The sound is based on high-frequency noise modulated by a sine function.
     * </p>
     *
     * @param note the {@link Note} being played
     * @param t    the time in seconds since the start of the note
     * @return the raw sample value at time t
     */
    @Override
    public double computeRawSample(Note note, double t) {
        double random = 2 * Math.random() - 1;
        return random * Math.sin(4000 * Math.PI * t);
    }
}