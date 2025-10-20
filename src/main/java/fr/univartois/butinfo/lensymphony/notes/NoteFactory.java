package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.element.Rest;

import java.util.List;

public class NoteFactory implements AbstractNoteFactory {
    private static final NoteFactory INSTANCE = new NoteFactory();

    public static NoteFactory getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Note createRest(NoteValue value) {
        return new Rest(value);
    }

    @Override
    public Note createNote(NotePitch pitch, NoteValue value) {
        return new MusicalNote(pitch, value);
    }

    @Override
    public Note createDottedNote(Note note) {
        return null;
    }

    @Override
    public Note createFermataOn(Note note) {
        return null;
    }

    @Override
    public Note createTiedNotes(Note... notes) {
        return AbstractNoteFactory.super.createTiedNotes(notes);
    }

    @Override
    public Note createTiedNotes(List<Note> notes) {
        return null;
    }
}
