package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public class AdsrDecorator extends DecoratorNoteSynthesizer{

    private final double a,d,s,r,T;

    public AdsrDecorator(NoteSynthesizer base, double a, double d, double s, double r, double T) {
        super(base);
        this.a = a;
        this.d = d;
        this.s = s;
        this.r = r;
        this.T = T;
    }
}
