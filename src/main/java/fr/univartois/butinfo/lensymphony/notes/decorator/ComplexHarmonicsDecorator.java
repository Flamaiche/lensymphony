package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

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
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] baseSound = base.synthesize(note, tempo, volume);
        int n = baseSound.length;
        double freq = note.getFrequency();
        double[] result = baseSound.clone();

        for (int i = 1; i <= numHarmonics; i++) {
            for (int j = 0; j < n; j++) {
                double time = j / (double) NoteSynthesizer.SAMPLE_RATE;
                double amplitude = harmonicAmplitudeFunction.apply(i, time);
                result[j] += (volume / numHarmonics)
                        * amplitude
                        * Math.sin(2 * Math.PI * frequencyMultiplierFunction.applyAsInt(i) * freq * time);
            }
        }

        return result;
    }
}
