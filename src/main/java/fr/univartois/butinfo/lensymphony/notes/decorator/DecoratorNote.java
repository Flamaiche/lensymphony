package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

public abstract class DecoratorNote implements Note {

    protected final Note note;

    protected DecoratorNote(Note note) {
        this.note = note;
    }
}
