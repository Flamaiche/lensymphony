package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * The type Decorator note.
 */
public abstract class DecoratorNote implements Note {

    /**
     * The Note.
     */
    protected final Note note;

    /**
     * Instantiates a new Decorator note.
     *
     * @param note the note
     */
    protected DecoratorNote(Note note) {
        this.note = note;
    }
}
