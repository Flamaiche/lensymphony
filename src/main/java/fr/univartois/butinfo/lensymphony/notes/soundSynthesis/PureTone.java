package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The type Pure tone.
 */
public class PureTone implements NoteSynthesizer {

    private static final PureTone INSTANCE = new PureTone();

    private PureTone() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PureTone getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double f = note.getFrequency();

        double durationMillis = note.getDuration(tempo);
        double duration = durationMillis / 1000.0;

        int totalSamples = (int) (SAMPLE_RATE * duration);

        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            samples[i] = volume * Math.sin(2 * Math.PI * f * t);
        }

        return samples;
    }
}
