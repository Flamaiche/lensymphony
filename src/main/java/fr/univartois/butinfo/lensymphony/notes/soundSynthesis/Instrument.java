package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * The enum Instrument.
 */
public enum Instrument {

    /**
     * Pure tone instrument.
     */
    PURE_TONE(PureTone.getInstance()),
    HARMONIQUE20(Harmonic.getInstance());

    private final NoteSynthesizer synthesizer;

    Instrument(NoteSynthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Gets synthesizer.
     *
     * @return the synthesizer
     */
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }
}