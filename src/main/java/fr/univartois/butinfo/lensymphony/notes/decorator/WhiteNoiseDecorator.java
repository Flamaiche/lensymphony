package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

import java.util.Random;

/**
 * The type White noise decorator.
 */
public class WhiteNoiseDecorator extends DecoratorNoteSynthesizer {

    private final double noiseLevel;

    private static final Random rand = new Random();

    /**
     * Instantiates a new White noise decorator.
     *
     * @param base       the base
     * @param noiseLevel the noise level
     */
    public WhiteNoiseDecorator(NoteSynthesizer base, double noiseLevel) {
        super(base);
        if (noiseLevel < 0) {
            throw new IllegalArgumentException("Noise level must be >= 0");
        }
        this.noiseLevel = noiseLevel;
    }

    @Override
    protected double[] applyEffect(double[] samples, Note note, int tempo, double volume) {
        double[] noisySamples = samples.clone();
        for (int i = 0; i < noisySamples.length; i++) {
            double noise = rand.nextDouble(-noiseLevel, noiseLevel);
            noisySamples[i] += noise;
        }
        return noisySamples;
    }
}
