package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import java.util.List;

public class createTiedNotes implements Note {

    private final List<Note> tiedNotes;

    public createTiedNotes(List<Note> tiedNotes) {
        if (tiedNotes == null || tiedNotes.isEmpty()) {
            throw new IllegalArgumentException("Tied notes list cannot be null or empty");
        }
        this.tiedNotes = tiedNotes;
    }

    @Override
    public double getFrequency() {
        return tiedNotes.get(0).getFrequency();
    }

    @Override
    public int getDuration(int tempo) {
        int total = 0;
        for (Note note : tiedNotes) {
            total += note.getDuration(tempo);
        }
        return total;
    }
}
