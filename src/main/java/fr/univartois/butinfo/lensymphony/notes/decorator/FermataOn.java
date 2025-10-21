package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Represents a note with a fermata  applied.
 * The note is played longer than its original duration.
 * @author Jabir Danoun
 */

public class FermataOn extends DecoratorNote {

    /**
     * Creates a FermataOn decorator for the given note.
     *
     * @param note the note to decorate with a fermata
     * @throws NullPointerException if the note is null
     */

    public FermataOn(Note note) {
        super(note);
        if (note == null){
            throw new NullPointerException("the note for FermataOn cannot be null");
        }
    }

    /**
     * Returns the frequency of the decorated note.
     *
     * @return the frequency in Hertz
     */

    @Override
    public double getFrequency() {
        return note.getFrequency();
    }

    /**
     * Returns the duration of the decorated note.
     * Currently, this implementation does not modify the duration.
     *
     * @param tempo the tempo in beats per minute
     * @return the duration in milliseconds
     */

    @Override
    public int getDuration(int tempo) {
        return note.getDuration(tempo);
    }
}
