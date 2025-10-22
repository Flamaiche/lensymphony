package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public class VibratoDecorator extends DecoratorNoteSynthesizer{

    private final double depth;

    private final double speed;

    public VibratoDecorator(NoteSynthesizer base, double depth, double speed) {
        super(base);
        this.depth = depth;
        this.speed = speed;
    }
}
