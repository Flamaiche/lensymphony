package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The type Staff.
 */
public class Staff implements Iterable<Note> {
    private final ArrayList<Note> notes;
    private final Instrument instrument;

    /**
     * Instantiates a new Staff.
     *
     * @param instrument the instrument
     */
    public Staff(Instrument instrument) {
        this.notes = new ArrayList<>();
        this.instrument = instrument;
    }

    /**
     * Add.
     *
     * @param note the note
     */
    public void add(Note note) {
        notes.add(note);
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    /**
     * Gets instrument.
     *
     * @return the instrument
     */
    public Instrument getInstrument() {
        return instrument;
    }


}
