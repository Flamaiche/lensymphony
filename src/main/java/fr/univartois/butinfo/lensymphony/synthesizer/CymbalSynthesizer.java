package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class CymbalSynthesizer extends AbstractPercussionSynthesizer {

    private static final CymbalSynthesizer INSTANCE = new CymbalSynthesizer();

    private CymbalSynthesizer() {
        super(0.02,0.04);
    }
    public static CymbalSynthesizer getINSTANCE() {
        return INSTANCE;
    }


    @Override
    protected double computeRawSample(Note note, double t) {
        double random = 2*Math.random() - 1;
        return random *Math.sin(4000*Math.PI*t);
    }
}
