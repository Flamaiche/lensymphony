package fr.univartois.butinfo.lensymphony.systhesizer;

import fr.univartois.butinfo.lensymphony.synthesizer.SnareDrum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TestSnarDrum {

    @Test
    void testSingletonInstance() {
        SnareDrum drum1 = SnareDrum.getInstance();
        SnareDrum drum2 = SnareDrum.getInstance();
        assertSame(drum1, drum2, "SnareDrum should be a singleton");
    }
}
