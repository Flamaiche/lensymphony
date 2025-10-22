package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.decorator.*;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import java.util.function.UnaryOperator;

/**
 * Defines musical instruments as combinations of synthesizers and sound effects (decorators).
 * Each instrument can apply one or more decorators (ADSR, reverb, vibrato, etc.)
 * in sequence using lambda expressions.
 */
public enum Instrument {

    /**
     * Simple pure tone instrument (no effects).
     */
    PURE_TONE(PureTone.getINSTANCE()),

    /**
     * Harmonic instrument with 20 harmonics.
     */
    HARMONIQUE20(new Harmonic(20)),

    /**
     * Contrabass – deep tone with mild ADSR and reverb.
     */
    CONTRABASS(
            PureTone.getINSTANCE(),
            base -> new AdsrDecorator(base, 0.2, 0.3, 0.8, 0.4)
    ),

    /**
     * Violin – bright and expressive, with vibrato and harmonics.
     */
    VIOLIN(
            new Harmonic(5),
            base -> new AdsrDecorator(base, 0.1, 0.2, 0.7, 0.2),
            VibratoDecorator::new,
            base -> new HarmonicsDecorator(base, 3)
    ),

    /**
     * Piano – soft ADSR and light reverb.
     */
    PIANO(
            new Harmonic(4),
            base -> new AdsrDecorator(base, 0.05, 0.2, 0.6, 0.3)
    ),

    /**
     * Experimental pad with rich effects.
     */
    SPACE_PAD(
            new Harmonic(8),
            base -> new AdsrDecorator(base, 0.5, 0.7, 0.9, 1.0),
            base -> new HarmonicsDecorator(base, 2),
            VibratoDecorator::new
    );

    /** The final synthesizer for this instrument (with effects applied). */
    private final NoteSynthesizer synthesizer;

    /**
     * Constructs an instrument with one or more decorator effects.
     *
     * @param base The base synthesizer (e.g., PureTone, Harmonic).
     * @param effects A variable number of decorators to apply in sequence.
     */
    @SafeVarargs
    Instrument(NoteSynthesizer base, UnaryOperator<NoteSynthesizer>... effects) {
        NoteSynthesizer result = base;
        for (UnaryOperator<NoteSynthesizer> effect : effects) {
            result = effect.apply(result);
        }
        this.synthesizer = result;
    }

    /**
     * Gets the final synthesizer of this instrument (after all effects).
     *
     * @return The fully decorated {@link NoteSynthesizer}.
     */
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }

    /**
     * Retrieves an {@link Instrument} based on its name (case-insensitive).
     *
     * @param instrumentName The name of the instrument.
     * @return The corresponding {@link Instrument}, or {@link Instrument#PURE_TONE} if not found.
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
