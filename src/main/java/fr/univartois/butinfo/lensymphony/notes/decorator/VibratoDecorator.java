package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * A decorator that adds a vibrato effect to a note
 * The vibrato is a periodic modulation of the note's frequency
 * It is characterized by its depth and speed (frequency in Hz)
 * Author: Jabir Danoun
 */

public class VibratoDecorator extends DecoratorNoteSynthesizer{

    /** Depth of the vibrato */

    private final double depth;

    /** Speed of the vibrato in Hz */

    private final double speed;

    /**
     * Creates a VibratoDecorator for the given synthesizer.
     *
     * @param base the base NoteSynthesizer to decorate
     * @param depth the depth of the vibrato
     * @param speed the speed (frequency in Hz) of the vibrato
     */

    public VibratoDecorator(NoteSynthesizer base, double depth, double speed) {
        super(base);
        this.depth = depth;
        this.speed = speed;
    }

    /**
     * Creates a VibratoDecorator with default depth and speed values
     *
     * @param base the base NoteSynthesizer to decorate
     */

    public VibratoDecorator(NoteSynthesizer base) {
        super(base);
        this.depth = 0.05;
        this.speed = 0.05;
    }

    /**
     * Applies the vibrato effect to the synthesized samples.
     *
     * @param samples the array of samples from the base synthesizer
     * @param note the note being played
     * @param tempo the tempo in BPM
     * @param volume the volume of the note
     * @return the modified array of samples with vibrato applied
     */

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

    /** Returns the depth of the vibrato. */


    public double getDepth() {
        return depth;
    }

    /** Returns the speed of the vibrato. */


    public double getSpeed() {
        return speed;
    }


}
