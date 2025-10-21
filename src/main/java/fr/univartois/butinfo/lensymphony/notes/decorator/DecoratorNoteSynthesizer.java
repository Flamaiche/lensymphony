package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public abstract class DecoratorNoteSynthesizer implements NoteSynthesizer {

    protected final NoteSynthesizer base;

    protected DecoratorNoteSynthesizer(NoteSynthesizer base) {
        this.base = base;
    }


    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        return base.synthesize(note, tempo, volume);
    }
}
