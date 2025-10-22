package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

/**
 * Decorator that adds complex harmonics to a base synthesizer.
 */
public class ComplexHarmonicsDecorator extends DecoratorNoteSynthesizer {

    private final int numHarmonics;
    private final IntUnaryOperator frequencyMultiplierFunction;
    private final BiFunction<Integer, Double, Double> harmonicAmplitudeFunction;

    public ComplexHarmonicsDecorator(NoteSynthesizer base,
                                     int numHarmonics,
                                     IntUnaryOperator frequencyMultiplierFunction,
                                     BiFunction<Integer, Double, Double> harmonicAmplitudeFunction) {
        super(base);
        if (numHarmonics < 1) {
            throw new IllegalArgumentException("Number of harmonics must be >= 1");
        }
        this.numHarmonics = numHarmonics;
        this.frequencyMultiplierFunction = frequencyMultiplierFunction;
        this.harmonicAmplitudeFunction = harmonicAmplitudeFunction;
    }

    @Override
    protected double[] applyEffect(double[] samples, Note note, int tempo, double volume) {
        int n = samples.length;
        double freq = note.getFrequency();
        double[] result = samples.clone();

        for (int i = 1; i <= numHarmonics; i++) {
            int multiplier = frequencyMultiplierFunction.applyAsInt(i);
            for (int j = 0; j < n; j++) {
                double time = j / (double) NoteSynthesizer.SAMPLE_RATE;
                double amplitude = harmonicAmplitudeFunction.apply(i, time);
                result[j] += (volume / numHarmonics) * amplitude * Math.sin(2 * Math.PI * multiplier * freq * time);
            }
        }

        return result;
    }
}
