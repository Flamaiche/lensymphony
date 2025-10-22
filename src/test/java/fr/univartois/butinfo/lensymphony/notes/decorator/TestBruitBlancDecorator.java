package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Test white noise decorator.
 */
class TestWhiteNoiseDecorator {

    /**
     * Test non null.
     */
    @Test
    void testNonNull() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[50];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };

        WhiteNoiseDecorator decorator = new WhiteNoiseDecorator(base, 0.1);

        assertNotNull(decorator.synthesize(note, 120, 1.0));
    }

    /**
     * Test meme longueur.
     */
    @Test
    void testMemeLongueur() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[60];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };

        WhiteNoiseDecorator decorator = new WhiteNoiseDecorator(base, 0.2);

        assertEquals(60, decorator.synthesize(note, 100, 1.0).length);
    }

    /**
     * Test intensite negative.
     */
    @Test
    void testIntensiteNegative() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[10];

        assertThrows(IllegalArgumentException.class,
                () -> new WhiteNoiseDecorator(base, -0.1));
    }

    /**
     * Test bruit applique.
     */
    @Test
    void testBruitApplique() {
        NoteSynthesizer base = (note, tempo, volume) -> new double[note.getDuration(tempo)];
        Note note = new Note() {
            public double getFrequency() { return 100; }
            public int getDuration(int t) { return 1000; }
        };

        WhiteNoiseDecorator decorator = new WhiteNoiseDecorator(base, 0.5);

        double[] original = base.synthesize(note, 120, 1.0);
        double[] avecBruit = decorator.synthesize(note, 120, 1.0);

        boolean modifie = false;
        for (int i = 0; i < original.length; i++) {
            if (avecBruit[i] != original[i]) {
                modifie = true;
                break;
            }
        }
        assertTrue(modifie);
    }
}
