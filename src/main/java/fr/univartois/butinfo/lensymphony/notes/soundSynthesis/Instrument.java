package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.decorator.*;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import java.util.function.UnaryOperator;

/**
 * Represents different musical instruments by combining a base synthesizer
 * with a sequence of sound decorators. Each instrument applies its decorators
 * in order to produce its characteristic timbre.
 *
 * <p>Available instruments:</p>
 * <ul>
 *     <li>{@link #PURE_TONE} : Pure tone, no effects.</li>
 *     <li>{@link #CONTRABASS} : Contrabass with harmonics and moderate ADSR envelope.</li>
 *     <li>{@link #VIOLIN} : Violin with ADSR envelope and vibrato.</li>
 *     <li>{@link #GUITAR} : Electric guitar with complex harmonics, ADSR, and vibrato.</li>
 *     <li>{@link #PIANO} : Piano with exponential harmonics and gentle ADSR.</li>
 *     <li>{@link #FLUTE} : Flute with odd harmonics, ADSR, slight noise, and vibrato.</li>
 * </ul>
 */
public enum Instrument {

    /** Pure tone without decorators. */
    PURE_TONE(PureTone.getINSTANCE()),

    /** Contrabass with harmonics and ADSR envelope for deep bass. */
    CONTRABASS(
            new Harmonic(2),
            base -> new AdsrDecorator(base, 0.2, 0.3, 0.8, 0.4),
            base -> new ComplexHarmonicsDecorator(
                    base,
                    8,
                    i -> i,
                    (i, t) -> 1.0 / i
            )
    ),

    /** Violin simulated with ten harmonics, ADSR envelope, and vibrato. */
    VIOLIN(
            new Harmonic(10),
            base -> new AdsrDecorator(base, 0.1, 0.2, 0.7, 0.3),
            base -> new VibratoDecorator(base, 0.01, 5)
    ),

    /** Electric guitar with complex harmonics, ADSR envelope, and moderate vibrato. */
    GUITAR(
            new ComplexHarmonicsDecorator(
                    PureTone.getINSTANCE(),
                    8,
                    i -> i,
                    (i, t) -> 1.5 * i
            ),
            base -> new AdsrDecorator(base, 0.008, 0.05, 0.2, 2.5),
            base -> new VibratoDecorator(base, 0.02, 3)
    ),

    /** Piano with exponential harmonics and gentle ADSR envelope. */
    PIANO(
            new ComplexHarmonicsDecorator(
                    PureTone.getINSTANCE(),
                    10,
                    i -> i,
                    (i, t) -> Math.exp(-2 * i * t) / i
            ),
            base -> new AdsrDecorator(base, 0.01, 0.3, 0.2, 0.5)
    ),

    /** Flute with odd harmonics, ADSR envelope, slight noise, and vibrato. */
    FLUTE(
            new ComplexHarmonicsDecorator(
                    PureTone.getINSTANCE(),
                    5,
                    i -> 2 * i - 1,
                    (i, t) -> 1.0 / (3 * i - 1)
            ),
            base -> new AdsrDecorator(base, 0.09, 0.0, 1.0, 0.3),
            base -> new WhiteNoiseDecorator(base, 0.003),
            base -> new VibratoDecorator(base, 0.01, 5)
    );

    /** The final synthesizer after applying all decorators for this instrument. */
    private final NoteSynthesizer synthesizer;

    /**
     * Constructs an instrument by applying a sequence of decorators to a base synthesizer.
     *
     * @param base The base synthesizer (e.g., {@link PureTone}, {@link Harmonic}).
     * @param effects Decorators to apply in order.
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
     * Returns the final synthesizer of this instrument after all decorators are applied.
     *
     * @return The complete {@link NoteSynthesizer} for this instrument.
     */
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }

    /**
     * Retrieves an {@link Instrument} by its name (case-insensitive).
     * Returns {@link #PURE_TONE} if the name is invalid or null.
     *
     * @param instrumentName The instrument name.
     * @return The corresponding instrument or {@link #PURE_TONE}.
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
