package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The type Harmonique 20.
 */
public class Harmonique20 implements NoteSynthesizer {

    private static Harmonique20 instance;
    private NoteSynthesizer harmonics;

    private Harmonique20() {
        // TODO a changer en fonction de ce que matheo a fait HarmoniqueDecorator
        harmonics = new HarmonicDecorator(PureTone.getInstance(), 20);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Harmonique20 getInstance() {
        if (instance == null) instance = new Harmonique20();
        return instance;
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        return harmonics.synthesize(note, tempo, volume);
    }
}
