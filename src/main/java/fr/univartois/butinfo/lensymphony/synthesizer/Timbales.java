package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Represents a timpani (timbales) synthesizer.
 * The sound frequency depends on the note played.
 * The synthesizer produces a tone that starts at the note's frequency
 * and gradually decreases to approximately 60% of that frequency.
 * The sound formula at time t is:
 * s(t) = V * e(t) * sin(2 * PI * f(t) * t)
 * where f(t) linearly interpolates from the note frequency to 60% of that frequency.
 * The envelope e(t) has:
 * - Attack: t / a if t < a
 * - Decay: exp((a - t) / d) if t >= a
 * Attack duration a = 0.01 seconds, decay d = 0.2 seconds.
 * The class is implemented as a singleton to ensure only one instance exists.
 * Author: Jabir Danoun
 * Version: 1.0
 */

public class Timbales extends AbstractPercussionSynthesizer {

    /** Unique instance of the timbales */
    private static final Timbales INSTANCE = new Timbales();

    /** Private constructor to prevent multiple instances */
    private Timbales() {
        super(0.01, 0.2);
    }

    /**
     * Returns the unique instance of Timbales.
     *
     * @return the singleton instance
     */

    public static Timbales getInstance() {
        return INSTANCE;
    }

    /**
     * Computes the raw audio sample at a given time t.
     * The frequency interpolates from the note's frequency to 60% of it.
     *
     * @param note the note being played
     * @param t the current time in seconds
     * @return the raw sample value
     */

    @Override
    public double computeRawSample(Note note, double t) {
        double fStart = note.getFrequency();
        double fEnd = fStart * 0.6;

        double durationSec = note.getDuration(120) / 1000.0;
        double freq = fStart + (fEnd - fStart) * (t / durationSec);
        return Math.sin(2 * Math.PI * freq * t);

    }
}
