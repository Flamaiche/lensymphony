package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public final class SnareDrum extends AbstractPercussionSynthesizer {

    private static final SnareDrum INSTANCE = new SnareDrum();

    private SnareDrum() { super(0.01, 0.1); }

    public static SnareDrum getInstance() { return INSTANCE; }

    @Override
    protected double computeRawSample(Note note, double t) {
        return 2 * Math.random() - 1;
    }

    @Override
    protected double envelope(double t) {
        if (t < a) return t / a;
        return Math.exp(15 * (a - t));
    }
}