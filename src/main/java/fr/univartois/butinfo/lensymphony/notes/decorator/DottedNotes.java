package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * A decorator that adds a single dot to a note.
 * The dot increases the note's duration by half.
 */
public class DottedNotes extends DecoratorNote {

    /**
     * Creates a dotted note based on another note.
     *
     * @param note the note to decorate
     */
    public DottedNotes(Note note) {
        super(note);
    }

    /** Returns the note’s frequency (unchanged). */
    @Override
    public double getFrequency() {
        return note.getFrequency();
    }

    /** Returns the duration of the note with one dot applied. */
    @Override
    public int getDuration(int tempo) {
        return (int)(note.getDuration(tempo) * 1.5);
    }
}