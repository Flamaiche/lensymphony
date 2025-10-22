package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class TestPureTone {

    @Test
    void testSingletonInstance() {
        PureTone instance1 = PureTone.getINSTANCE();
        PureTone instance2 = PureTone.getINSTANCE();

        assertNotNull(instance1, "The instance should not be null");
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }
}