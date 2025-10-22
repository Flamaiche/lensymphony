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

    /**
     * Harmonic instrument with 20 harmonics (generic).
     */
    HARMONIQUE20(new Harmonic(20)),

    /**
     * Contrabass – very low frequency instrument (around 2–3 octaves below middle C).
     */
    CONTRABASS(new Harmonic(2)),

    /**
     * Violoncello (Cello) – low to mid register instrument (about 3 octaves range).
     */
    VIOLONCELLO(new Harmonic(3)),

    /**
     * Viola – mid register instrument (slightly above cello).
     */
    VIOLA(new Harmonic(4)),

    /**
     * Violin – higher register string instrument.
     */
    VIOLIN(new Harmonic(5)),

    /**
     * Piano – wide range instrument (uses middle reference octave).
     */
    PIANO(new Harmonic(4));

    private final NoteSynthesizer synthesizer;

    Instrument(NoteSynthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Gets the synthesizer associated with this instrument.
     *
     * @return the note synthesizer
     */
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }


    /**
     * Retrieves an {@link Instrument} instance based on the given instrument name.
     * <p>
     * The comparison is case-insensitive and ignores leading or trailing whitespace.
     * If the provided name does not match any known instrument, the method defaults
     * to returning {@link Instrument#PURE_TONE}.
     * </p>
     *
     * @param instrumentName The name of the instrument as read from the MusicXML file.
     *                       May contain extra spaces or case variations (e.g., "piano", " Piano ").
     * @return The matching {@link Instrument} if found, or {@link Instrument#PURE_TONE} otherwise.
     */
    public static Instrument getInstrumentByName(String instrumentName) {
        if (instrumentName == null) return PURE_TONE;

        String name = instrumentName.trim().toUpperCase();

        for (Instrument instrument : Instrument.values()) {
            if (name.equals(instrument.name())) {
                return instrument;
            }
        }

        return PURE_TONE;
    }
}