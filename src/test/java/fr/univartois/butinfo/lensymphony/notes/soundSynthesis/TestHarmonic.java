package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestHarmonic {

    @Test
    void testSingletonInstance() {
        Harmonic instance1 = Harmonic.getInstance();
        Harmonic instance2 = Harmonic.getInstance();

        assertNotNull(instance1, "The instance should not be null");
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }
}