package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;

/**
 * Represents a musical note with a specific pitch and duration.
 *
 * @author Jabir Danoun
 */

public class MusicalNote implements Note {

    /**
     * The pitch of the musical note.
     */

    private final NotePitch pitch;

    /**
     * The value (duration) of the musical note.
     */

    private final NoteValue noteValue;

    /**
     * Constructs a musical note with the given pitch and note value.
     *
     * @param pitch the pitch of the note
     * @param noteValue the value (duration) of the note
     */


    public MusicalNote(NotePitch pitch, NoteValue noteValue) {
        this.pitch = pitch;
        this.noteValue = noteValue;
    }

    /**
     * Returns the frequency of the note in Hertz.
     *
     * @return the frequency of the note
     */

    @Override
    public double getFrequency() {
        return pitch.frequency();
    }

    /**
     * Returns the duration of the note in milliseconds for a given tempo.
     *
     * @param tempo the tempo in beats per minute
     * @return the duration of the note in milliseconds
     */

    @Override
    public int getDuration(int tempo) {
        return noteValue.duration(tempo);
    }


}
