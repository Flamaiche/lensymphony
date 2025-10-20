package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.*;
import fr.univartois.butinfo.lensymphony.synthesizer.SimpleMusicSynthesizer;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;
import java.util.List;

/**
 * The type Test pure tone.
 */
public class TestPureTone {

    /**
     * Test pure tone.
     *
     * @throws LineUnavailableException the line unavailable exception
     */
    @Test
    void testPureTone() throws LineUnavailableException {
        PureTone tone = PureTone.getInstance();
        AbstractNoteFactory noteFactory = new AbstractNoteFactory() {
            @Override
            public Note createDottedNote(Note note) {
                return null;
            }

            @Override
            public Note createFermataOn(Note note) {
                return null;
            }

            @Override
            public Note createTiedNotes(List<Note> notes) {
                return null;
            }
        };
        try {
        List<Note> listNote = List.of(noteFactory.createNote(NotePitch.of(PitchClass.C, 5), NoteValue.EIGHTH));

        SimpleMusicSynthesizer synthesizer = new SimpleMusicSynthesizer(120, listNote, tone);

        synthesizer.synthesize();
        synthesizer.play();

        // TODO : A finir

        System.out.println("Pure tone sound synthesis test passed.");

        } catch (Exception e) {
            throw new AssertionError("PureTone synthesis failed", e);
        }
    }
}