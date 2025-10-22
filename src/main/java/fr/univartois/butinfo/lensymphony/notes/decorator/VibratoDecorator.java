package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;


public class VibratoDecorator extends DecoratorNoteSynthesizer{

    private final double depth;

    private final double speed;

    public VibratoDecorator(NoteSynthesizer base, double depth, double speed) {
        super(base);
        this.depth = depth;
        this.speed = speed;
    }

    @Override
    protected double[] applyEffect(double[] samples, Note note, int tempo, double volume) {
        double[] vibratoSound = new double[samples.length];

        for(int i=0;i<samples.length;i++){
            double t = (double) i / NoteSynthesizer.SAMPLE_RATE;
            double delta = depth * Math.sin(2 * Math.PI * speed * t);
            vibratoSound[i] = samples[i] + delta;
        }

        return vibratoSound;
    }

    public double getDepth() {
        return depth;
    }

    public double getSpeed() {
        return speed;
    }


}
