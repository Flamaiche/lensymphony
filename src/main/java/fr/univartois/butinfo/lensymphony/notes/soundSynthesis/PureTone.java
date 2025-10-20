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

        // La durée retournée par note.getDuration(tempo) est en millisecondes → conversion en secondes
        double durationMillis = note.getDuration(tempo);
        double duration = durationMillis / 1000.0;

        // Nombre total d’échantillons à générer
        int totalSamples = (int) (SAMPLE_RATE * duration);

        // Création du tableau d’échantillons
        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            samples[i] = volume * Math.sin(2 * Math.PI * f * t);
        }

        return samples;
    }
}
