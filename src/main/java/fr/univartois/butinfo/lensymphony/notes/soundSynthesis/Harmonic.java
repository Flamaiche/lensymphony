package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.decorator.HarmonicsDecorator;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The type Harmonic 20.
 */
public class Harmonic implements NoteSynthesizer {

    private static Harmonic instance;
    private NoteSynthesizer harmonics;
    private static int octave = 20;

    public Harmonic(int octave) {
        harmonics = new HarmonicsDecorator(PureTone.getInstance(), octave);
    }

    public Harmonic() {
        this(20);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Harmonic getInstance() {
        if (instance == null) instance = new Harmonic(octave);
        return instance;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        return harmonics.synthesize(note, tempo, volume);
    }
}
