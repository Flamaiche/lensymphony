package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Represents a snare drum (caisse claire) synthesizer.
 * The class is a singleton and extends AbstractPercussionSynthesizer.
 * It generates snare drum sound using simple white noise.
 * The sound formula at time t is:
 * s(t) = V * e(t) * random(-1, 1)
 * The envelope e(t) is defined as:
 * - Attack: t / a if t < a
 * - Decay: exp(15 * (a - t)) if t >= a
 * The attack duration is a = 0.01 seconds.
 * The singleton pattern ensures only one instance exists.
 * Author: Jabir Danoun
 * Version: 1.0
 */


public final class SnareDrum extends AbstractPercussionSynthesizer {

    /** Unique instance of the snare drum */
    private static final SnareDrum INSTANCE = new SnareDrum();
    /**
     * Private constructor to prevent multiple instances.
     * Sets attack and a decay value (not used for snare drum).
     */

    private SnareDrum() {
        super(0.01, 0.1);
    }

    /**
     * Returns the unique instance of the snare drum.
     * @return the singleton instance of SnareDrum
     */

    public static SnareDrum getInstance() {
        return INSTANCE;
    }

    /**
     * Computes the raw sample at time t.
     * For snare drum, it is simply random noise between -1 and 1.
     * @param note the note (atonal, frequency is ignored)
     * @param t the current time in seconds
     * @return the raw sample value at time t
     */

    @Override
    protected double computeRawSample(Note note, double t) {
        return 2 * Math.random() - 1;
    }

    /**
     * Computes the envelope of the sound at time t.
     * @param t the current time in seconds
     * @return the envelope value e(t)
     */

    @Override
    protected double envelope(double t) {
        if (t < a) return t / a;
        return Math.exp(15 * (a - t));
    }
}