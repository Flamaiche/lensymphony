package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

public class HarmonicsDecorator extends DecoratorNoteSynthesizer {

    private final int nHarmonics;

    public HarmonicsDecorator(NoteSynthesizer base,int harmonics) {
        super(base);
        if(harmonics<1){
            throw new IllegalArgumentException("Number of harmonics must be >= 1");
        }
        this.nHarmonics = harmonics;

    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] baseSound = base.synthesize(note, tempo, volume);
        int n = baseSound.length;
        double freq = note.getFrequency();
        double[] soundWithHarmonics;

        soundWithHarmonics = baseSound.clone();

        for (int i = 2; i <= nHarmonics; i++) {
            double amplitude = volume / nHarmonics;
            for (int j = 0; j < n; j++) {
                double time = j / (double) NoteSynthesizer.SAMPLE_RATE;
                soundWithHarmonics[j] += amplitude * Math.sin(2 * Math.PI * i * freq * time) / Math.sqrt(i);
            }
        }

        return soundWithHarmonics;
    }


}
