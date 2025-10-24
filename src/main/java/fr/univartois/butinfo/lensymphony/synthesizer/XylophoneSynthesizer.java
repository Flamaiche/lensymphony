package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * The type Xylophone synthesizer.
 */
public class XylophoneSynthesizer extends AbstractPercussionSynthesizer {

    private static final XylophoneSynthesizer INSTANCE = new XylophoneSynthesizer();

    private static final int N_HARMONICS = 5;

    private XylophoneSynthesizer() {
        super(0.01, 0.1);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static XylophoneSynthesizer getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public double computeRawSample(Note note, double t, int tempo) {
        double f = note.getFrequency();
        double sample = 0.0;

        for (int i = 0; i < N_HARMONICS; i++) {
            double harmonicFreq = f * Math.pow(2, i);
            double amplitude = Math.exp(-(2 * i + 1));
            sample += Math.sin(2 * Math.PI * harmonicFreq * t) * amplitude;
        }

        return sample;
    }
}
