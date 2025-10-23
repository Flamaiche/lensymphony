package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Singleton class that generates the sound of a bass drum.
 * Produces a low-frequency atonal tone with exponentially decaying amplitude.
 * Frequency decreases from ~60 Hz to ~40 Hz over the duration of the note.
 */
public class BassDrumSynthesizer extends AbstractPercussionSynthesizer {

    private static final BassDrumSynthesizer INSTANCE = new BassDrumSynthesizer();

    // Frequency constants
    private static final double F_START = 60.0;
    private static final double F_END = 40.0;

    private BassDrumSynthesizer() {
        super(0.01, 0.3);
    }

    public static BassDrumSynthesizer getInstance() {
        return INSTANCE;
    }

    /**
     * Computes the raw sound sample at a given time for the bass drum, taking tempo into account.
     *
     * @param note  the {@link Note} being played (ignored for bass drum)
     * @param t     the time in seconds since the start of the note
     * @param tempo the tempo in beats per minute
     * @return the raw sample value at time t
     */
    public double computeRawSample(Note note, double t, int tempo) {
        double durationSec = note.getDuration(tempo) / 1000.0;
        double f = F_START + t * (F_END - F_START) / durationSec;
        return Math.exp(-5 * t) * Math.sin(2 * Math.PI * f * t);
    }

    @Override
    public double envelope(double t) {
        return 1.0;
    }

}
