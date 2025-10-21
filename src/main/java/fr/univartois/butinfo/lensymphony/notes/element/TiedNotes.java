package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import java.util.List;

/**
 * Represents a tied note composed of multiple notes of the same pitch
 * The total duration is the sum of all tied notes' durations
 *
 * @author Jabir Danoun
 */

public class TiedNotes implements Note {

    /**
     * The list of tied notes
     */
    private final List<Note> listTiedNotes;

    /**
     * Creates a tied note from the given list of notes
     *
     * @param tiedNotes the notes to tie together
     * @throws IllegalArgumentException if the list is null or empty
     */

    public TiedNotes(List<Note> tiedNotes) {
        if (tiedNotes == null || tiedNotes.isEmpty()) {
            throw new IllegalArgumentException("Tied notes list cannot be null or empty");
        }
        this.listTiedNotes = tiedNotes;
    }

    /**
     * Returns the frequency of the tied notes
     *
     * @return the frequency in Hertz
     */

    @Override
    public double getFrequency() {
        return listTiedNotes.get(0).getFrequency();
    }

    /**
     * Returns the total duration of the tied notes for a given tempo.
     *
     * @param tempo the tempo in beats per minute
     * @return the total duration in milliseconds
     */

    @Override
    public int getDuration(int tempo) {
        int total = 0;
        for (Note note : listTiedNotes) {
            total += note.getDuration(tempo);
        }
        return total;
    }


}
