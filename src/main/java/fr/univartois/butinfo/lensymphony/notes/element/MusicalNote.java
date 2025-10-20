package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;

public class MusicalNote implements Note {

    private final NotePitch pitch;

    private final NoteValue noteValue;


    public MusicalNote(NotePitch pitch, NoteValue noteValue) {
        this.pitch = pitch;
        this.noteValue = noteValue;
    }

    @Override
    public double getFrequency() {
        return pitch.frequency();
    }

    @Override
    public int getDuration(int tempo) {
        return noteValue.duration(tempo);
    }


}
