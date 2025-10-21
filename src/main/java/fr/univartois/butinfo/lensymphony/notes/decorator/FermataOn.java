package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class FermataOn extends DecoratorNote {

    public FermataOn(Note note) {
        super(note);
        if (note == null){
            throw new NullPointerException("the note for FermataOn cannot be null");
        }
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
