package fr.univartois.butinfo.lensymphony.notes;

import java.util.List;

public class NoteFactory implements AbstractNoteFactory {
    private static final NoteFactory INSTANCE = new NoteFactory();

    public static NoteFactory getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Note createRest(NoteValue value) {
        return AbstractNoteFactory.super.createRest(value);
    }

    @Override
    public Note createNote(NotePitch pitch, NoteValue value) {
        return AbstractNoteFactory.super.createNote(pitch, value);
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
