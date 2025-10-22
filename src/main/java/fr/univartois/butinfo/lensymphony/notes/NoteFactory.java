package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.notes.decorator.FermataOn;
import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.element.Rest;
import fr.univartois.butinfo.lensymphony.notes.element.TiedNotes;
import fr.univartois.butinfo.lensymphony.notes.decorator.DottedNotes;

import java.util.Arrays;
import java.util.List;

/**
 * Singleton factory for creating basic musical notes and rests.
 * <p>
 * This class implements the {@link AbstractNoteFactory} interface.
 * It provides concrete implementations for creating {@link MusicalNote} and {@link Rest}.
 * Other note types such as dotted notes, fermata, and tied notes are not implemented yet (Sprint 1).
 * </p>
 * <p>
 * The singleton pattern ensures that only one instance of this factory exists,
 * accessible via {@link #getINSTANCE()}.
 * </p>
 *
 * @author Babahamou Malik
 * @version 0.1.0
 */
public class NoteFactory implements AbstractNoteFactory {

    /**
     * The single instance of the NoteFactory (singleton pattern).
     */
    private static final NoteFactory INSTANCE = new NoteFactory();

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private NoteFactory() {}

    /**
     * Returns the single instance of this factory.
     *
     * @return the singleton instance of {@link NoteFactory}
     */
    public static NoteFactory getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Creates a rest with the given value.
     *
     * @param value the {@link NoteValue} of the rest
     * @return a new {@link Rest} instance with the specified value
     */
    @Override
    public Note createRest(NoteValue value) {
        return new Rest(value);
    }

    /**
     * Creates a musical note with the given pitch and value.
     *
     * @param pitch the {@link NotePitch} of the note
     * @param value the {@link NoteValue} of the note
     * @return a new {@link MusicalNote} instance with the specified pitch and value
     */
    @Override
    public Note createNote(NotePitch pitch, NoteValue value) {
        return new MusicalNote(pitch, value);
    }

    /**
     * Creates a dotted note from the given existing note.
     * <p>
     * Not implemented yet in Sprint 1.
     * </p>
     *
     * @param note the existing note
     * @return null
     */
    @Override
    public Note createDottedNote(Note note) {
        return new DottedNotes(note);
    }

    /**
     * Creates a note with a fermata applied on it.
     * <p>
     * Not implemented yet in Sprint 1.
     * </p>
     *
     * @param note the existing note
     * @return null
     */
    @Override
    public Note createFermataOn(Note note) {
        return new FermataOn(note);
    }

    /**
     * Creates a note representing the tie of the given notes.
     * <p>
     * Currently delegates to the default implementation in {@link AbstractNoteFactory}.
     * </p>
     *
     * @param notes the notes to tie together
     * @return the tied note (currently null or default behavior)
     */
    @Override
    public Note createTiedNotes(Note... notes) {
        if (notes == null || notes.length == 0) {
            throw new IllegalArgumentException("Notes must not be empty");
        }
        return new TiedNotes(Arrays.stream(notes).toList());
    }

    /**
     * Creates a note representing the tie of the given list of notes.
     * <p>
     * Not implemented yet in Sprint 1.
     * </p>
     *
     * @param notes the list of notes to tie together
     * @return null
     */
    @Override
    public Note createTiedNotes(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            throw new IllegalArgumentException("Notes must not be empty");
        }
        return new  TiedNotes(notes);
    }
}
