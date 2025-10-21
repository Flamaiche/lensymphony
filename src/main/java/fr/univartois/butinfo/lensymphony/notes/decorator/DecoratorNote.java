package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

public abstract class DecoratorNote implements Note {

    protected final Note note;

    protected DecoratorNote(Note note) {
        this.note = note;
    }

    @Override
    public double getFrequency() {
        return note.getFrequency();
    }

    @Override
    public int getDuration(int tempo) {
        return note.getDuration(tempo);
    }
}
