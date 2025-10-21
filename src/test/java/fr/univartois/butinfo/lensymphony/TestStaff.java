package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Staff} class.
 * <p>
 * This test suite verifies the correct behavior of the {@code Staff} class,
 * which represents a musical staff containing {@link Note} objects associated with an {@link Instrument}.
 * </p>
 *
 * <p>Test coverage includes:</p>
 * <ul>
 *   <li>Proper instantiation of a {@code Staff} object</li>
 *   <li>Correct handling of note addition</li>
 *   <li>Iterator behavior over contained notes</li>
 *   <li>Verification of assigned instrument</li>
 *   <li>Behavior with an empty staff</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
class TestStaff {

    /**
     * The {@link Staff} instance under test.
     */
    private Staff staff;

    /**
     * Initializes a {@link Staff} with a default instrument before each test.
     */
    @BeforeEach
    void setUp() {
        staff = new Staff(Instrument.PURE_TONE);
    }

    /**
     * Verifies that the constructor properly initializes a {@link Staff}.
     */
    @Test
    @DisplayName("Constructor should initialize Staff correctly")
    void testConstructor() {
        assertNotNull(staff, "The Staff object should not be null.");
        assertEquals(Instrument.PURE_TONE, staff.getInstrument(),
                "The Staff should have the PURE_TONE instrument.");
    }

    /**
     * Verifies that notes can be added and iterated over in correct order.
     */
    @Test
    @DisplayName("Notes should be added and iterated correctly")
    void testAddAndIterator() {
        Note note1 = new MusicalNote(NotePitch.of(fr.univartois.butinfo.lensymphony.notes.PitchClass.C, 4),
                NoteValue.QUARTER);
        Note note2 = new MusicalNote(NotePitch.of(fr.univartois.butinfo.lensymphony.notes.PitchClass.D, 4),
                NoteValue.HALF);

        staff.add(note1);
        staff.add(note2);

        Iterator<Note> iterator = staff.iterator();

        assertTrue(iterator.hasNext(), "Iterator should contain elements.");
        assertEquals(note1, iterator.next(), "The first note should match the first added note.");
        assertEquals(note2, iterator.next(), "The second note should match the second added note.");
        assertFalse(iterator.hasNext(), "Iterator should have no more elements.");
    }

    /**
     * Verifies that the iterator works correctly in a for-each loop.
     */
    @Test
    @DisplayName("Enhanced for-loop should iterate over all notes")
    void testForEachLoop() {
        Note note = new MusicalNote(NotePitch.of(fr.univartois.butinfo.lensymphony.notes.PitchClass.A, 4),
                NoteValue.QUARTER);
        staff.add(note);

        int count = 0;
        for (Note n : staff) {
            assertNotNull(n, "Note should not be null.");
            count++;
        }

        assertEquals(1, count, "Exactly one note should have been iterated over.");
    }

    /**
     * Verifies that an empty staff behaves correctly.
     */
    @Test
    @DisplayName("Empty Staff should behave correctly")
    void testEmptyStaff() {
        Iterator<Note> iterator = staff.iterator();
        assertNotNull(iterator, "Iterator should not be null even if Staff is empty.");
        assertFalse(iterator.hasNext(), "Iterator should have no elements for an empty Staff.");
    }

    /**
     * Verifies that the Staff retains its assigned instrument.
     */
    @Test
    @DisplayName("Instrument getter should return the correct instrument")
    void testGetInstrument() {
        Staff violinStaff = new Staff(Instrument.VIOLIN);
        assertEquals(Instrument.VIOLIN, violinStaff.getInstrument(),
                "The instrument should match the one assigned in the constructor.");
    }

    /**
     * Verifies that adding a null note does not break iteration.
     */
    @Test
    @DisplayName("Adding a null note should not break iteration")
    void testAddNullNote() {
        staff.add(null);

        Iterator<Note> iterator = staff.iterator();
        assertTrue(iterator.hasNext(), "Iterator should still exist even if a null note was added.");
        assertNull(iterator.next(), "The added note should be null.");
    }
}
