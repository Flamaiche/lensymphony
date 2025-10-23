package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public abstract class AbstractPercussionSynthesizer implements NoteSynthesizer {

    protected final double a;
    protected final double d;


    public AbstractPercussionSynthesizer(double a, double d) {
        this.a = a;
        this.d= d;
    }


    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double durationSec = note.getDuration(tempo) / 1000.0;
        int totalSamples = (int) (SAMPLE_RATE * durationSec);
        double[] samples = new double[totalSamples];

        if (note.getFrequency() == 0) {
            return samples;
        }

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            double env = envelope(t);
            samples[i] = volume * env * computeRawSample(note, t);
        }
        return samples;
    }

    public double envelope(double t) {
        if (t < a) return t / a;
        return Math.exp((a - t) / d);
    }

    protected abstract double computeRawSample(Note note, double t);
}
