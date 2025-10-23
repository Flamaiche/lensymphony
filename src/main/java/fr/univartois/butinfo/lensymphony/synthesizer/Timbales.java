package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class Timbales extends AbstractPercussionSynthesizer {

    private static final Timbales INSTANCE = new Timbales();

    private Timbales() {
        super(0.01, 0.2);
    }

    public static Timbales getInstance() {
        return INSTANCE;
    }


    @Override
    protected double computeRawSample(Note note, double t) {
        double fStart = note.getFrequency();
        double fEnd = fStart * 0.6;

        double durationSec = note.getDuration(120) / 1000.0;
        double freq = fStart + (fEnd - fStart) * (t / durationSec);
        return Math.sin(2 * Math.PI * freq * t);

    }
}
