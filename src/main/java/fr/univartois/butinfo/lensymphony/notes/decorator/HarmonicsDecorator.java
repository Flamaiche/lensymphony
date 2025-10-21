package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public class HarmonicsDecorator extends DecoratorNoteSynthesizer {

    private final int harmonics;

    public HarmonicsDecorator(NoteSynthesizer base,int harmonics) {
        super(base);
        if(harmonics<1){
            throw new IllegalArgumentException("Number of harmonics must be >= 1");
        }
        this.harmonics = harmonics;

    }
}
