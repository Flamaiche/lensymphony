package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Harmonic} class.
 */
class TestHarmonic {

    private Harmonic harmonic;

    @BeforeEach
    void setUp() {
        harmonic = new Harmonic(10);
    }

    @Test
    void testConstructorWithOctave() {
        assertNotNull(harmonic, "Harmonic should be instantiated");
    }

    @Test
    void testDefaultConstructor() {
        Harmonic defaultHarmonic = new Harmonic();
        assertNotNull(defaultHarmonic, "Default Harmonic constructor should work");
    }

    @Test
    void testGetInstanceSingleton() {
        Harmonic first = Harmonic.getInstance();
        Harmonic second = Harmonic.getInstance();

        assertSame(first, second, "getInstance() should return the same instance");
    }

    @Test
    void testSetOctaveAffectsNewInstances() {
        Harmonic first = Harmonic.getInstance();
        first.setOctave(15);
        Harmonic second = Harmonic.getInstance();

        assertEquals(first, second, "Singleton instance should remain the same after octave change");
    }
    @Test
    void testSynthesizeNotNull() {
        // Création d'une note musicale : pitch = C4, valeur = QUARTER
        MusicalNote testNote = new MusicalNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);

        double[] result = harmonic.synthesize(testNote, 120, 0.8);

        assertNotNull(result, "Synthesize should not return null");
        assertTrue(result.length > 0, "Synthesize should return a non-empty array");
    }


}
