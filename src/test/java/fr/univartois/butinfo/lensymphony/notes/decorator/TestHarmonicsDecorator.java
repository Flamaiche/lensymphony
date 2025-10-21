package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHarmonicsDecorator {

    @Test
    void testNotNull() {
        NoteSynthesizer base = (n, t, v) -> new double[50];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };
        HarmonicsDecorator decorator = new HarmonicsDecorator(base, 5);
        assertNotNull(decorator.synthesize(note, 120, 1.0));
    }

    @Test
    void testLengthSame() {
        NoteSynthesizer base = (n, t, v) -> new double[60];
        Note note = new Note() {
            public double getFrequency() { return 440; }
            public int getDuration(int t) { return 1000; }
        };
        HarmonicsDecorator decorator = new HarmonicsDecorator(base, 3);
        assertEquals(60, decorator.synthesize(note, 100, 1.0).length);
    }

    @Test
    void testInvalidThrows() {
        NoteSynthesizer base = (n, t, v) -> new double[10];
        assertThrows(IllegalArgumentException.class, () -> new HarmonicsDecorator(base, 1));
    }

}
