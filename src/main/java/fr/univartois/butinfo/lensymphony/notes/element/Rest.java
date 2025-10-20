package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;


public class Rest implements Note {

    private final NoteValue noteValue;

    public Rest(NoteValue noteValue) {
        this.noteValue = noteValue;
    }

    @Override
    public double getFrequency() {
        return 0.0;
    }

    @Override
    public int getDuration(int tempo) {
        return noteValue.duration(tempo);
    }
}
