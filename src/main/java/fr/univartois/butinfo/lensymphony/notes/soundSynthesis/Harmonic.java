package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.decorator.HarmonicsDecorator;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The type Harmonic 20.
 */
public class Harmonic implements NoteSynthesizer {

    private final NoteSynthesizer harmonics;

    public Harmonic(int octave) {
        harmonics = new HarmonicsDecorator(PureTone.getINSTANCE(), octave);
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        return harmonics.synthesize(note, tempo, volume);
    }
}
