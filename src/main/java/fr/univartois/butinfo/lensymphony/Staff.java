package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;

import java.util.ArrayList;
import java.util.Iterator;

public class Staff implements Iterable<Note> {
    private ArrayList<Note> notes;
    private Instrument instrument;

    public Staff(Instrument instrument) {
        this.notes = new ArrayList<>();
        this.instrument = instrument;
    }

    public void add(Note note) {
        notes.add(note);
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}
