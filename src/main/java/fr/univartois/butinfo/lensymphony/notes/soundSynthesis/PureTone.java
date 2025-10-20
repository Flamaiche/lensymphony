package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public class PureTone implements NoteSynthesizer {

    private static PureTone instance;

    private PureTone() {}

    public static PureTone getInstance() {
        if (instance == null) {
            instance = new PureTone();
        }
        return instance;
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double f = note.getFrequency();
        double duration = note.getDuration(tempo);
        int totalSamples = (int) (SAMPLE_RATE * duration);

        double[] samples = new double[totalSamples];
        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            samples[i] = volume * Math.sin(2 * Math.PI * f * t);
        }
        return samples;
    }
}