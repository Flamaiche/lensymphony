package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Test instrument.
 */
public class TestInstrument {

    /**
     * Test enum has values.
     */
    @Test
    public void testEnumHasValues() {
        assertTrue(Instrument.values().length > 0,
                "The Instrument enum must contain at least one value.");
    }

    /**
     * Test pure tone synthesizer link.
     */
    @Test
    public void testPureToneSynthesizerLink() {
        NoteSynthesizer synth = Instrument.PURE_TONE.getSynthesizer();

        assertNotNull(synth, "The synthesizer of PURE_TONE must not be null.");
        assertTrue(synth instanceof PureTone,
                "The synthesizer of PURE_TONE must be an instance of PureTone.");
        assertSame(PureTone.getInstance(), synth,
                "The synthesizer of PURE_TONE must be the singleton instance from PureTone.getInstance().");
    }

    /**
     * Test all instruments have synthesizer.
     */
    @Test
    public void testAllInstrumentsHaveSynthesizer() {
        for (Instrument instrument : Instrument.values()) {
            assertNotNull(instrument.getSynthesizer(),
                    () -> "The synthesizer of " + instrument.name() + " must not be null.");
        }
    }
}
