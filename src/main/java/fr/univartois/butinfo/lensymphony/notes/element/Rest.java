package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;

/**
 * Represents a musical rest (silence) with a specific duration.
 */
public class Rest implements Note {

    private final NoteValue noteValue;

    /**
     * Creates a rest with the given note value.
     *
     * @param noteValue the duration of the rest
     */
    public Rest(NoteValue noteValue) {
        this.noteValue = noteValue;
    }

    /**
     * Returns the frequency of the rest (always 0.0 Hz).
     *
     * @return 0.0
     */
    @Override
    public double getFrequency() {
        return 0.0;
    }

    /**
     * Returns the duration of the rest in milliseconds for a given tempo.
     *
     * @param tempo the tempo in beats per minute
     * @return the duration in milliseconds
     */
    @Override
    public int getDuration(int tempo) {
        return noteValue.duration(tempo);
    }
}
