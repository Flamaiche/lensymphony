package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import fr.univartois.butinfo.lensymphony.notes.element.MusicalNote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Test triangle.
 */
class TestTriangle {

    /**
     * Test singleton instance.
     */
    @Test
    void testSingletonInstance() {
        Triangle instance1 = Triangle.getINSTANCE();
        Triangle instance2 = Triangle.getINSTANCE();

        assertNotNull(instance1, "The instance should not be null");
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }

    /**
     * Test synthesize produces samples.
     */
    @Test
    void testSynthesizeProducesSamples() {
        Triangle triangle = Triangle.getINSTANCE();

        MusicalNote note = new MusicalNote(NotePitch.of(PitchClass.A, 4),
                NoteValue.QUARTER);

        double volume = 0.8;
        int tempo = 120;

        double[] samples = triangle.synthesize(note, tempo, volume);

        assertNotNull(samples, "The generated samples should not be null");
        assertTrue(samples.length > 0, "There should be at least one sample");

        for (double s : samples) {
            assertTrue(s <= volume && s >= -volume, "Sample value out of expected range");
        }
    }
}
