package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The type Triangle tone with harmonic series.
 */
public class Triangle implements NoteSynthesizer {

    private static final Triangle INSTANCE = new Triangle();

    private Triangle() {}

    public static Triangle getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double durationMillis = note.getDuration(tempo);
        double duration = durationMillis / 1000.0;

        int totalSamples = (int) (SAMPLE_RATE * duration);
        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            double sampleValue = 0;

            // Somme harmonique
            for (int n = 1; n <= 9; n++) {
                double harmonic = Math.exp(-5 * (0.5 + 0.3 * n)) *
                        Math.sin(4 * Math.PI * (1400 + 800 * n) * t);
                sampleValue += harmonic;
            }

            samples[i] = volume * sampleValue; // on multiplie par v
        }

        return samples;
    }
}
