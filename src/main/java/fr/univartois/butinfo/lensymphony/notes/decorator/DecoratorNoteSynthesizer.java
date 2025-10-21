package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * Abstract decorator for {@link NoteSynthesizer}.
 * <p>
 * This class allows extending or modifying the behavior of an existing
 * synthesizer (e.g., adding harmonics) without changing its structure.
 * </p>
 */
public abstract class DecoratorNoteSynthesizer implements NoteSynthesizer {

    /** The base synthesizer being decorated. */
    protected final NoteSynthesizer base;

    /**
     * Creates a new decorator for the given synthesizer.
     *
     * @param base The synthesizer to decorate.
     */
    protected DecoratorNoteSynthesizer(NoteSynthesizer base) {
        this.base = base;
    }

    /**
     * Delegates synthesis to the base synthesizer.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        return base.synthesize(note, tempo, volume);
    }
}
