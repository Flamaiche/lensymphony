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
    ),

    /** Triangle with high-frequency noise and short ADSR envelope. */
    TRIANGLE(
            PureTone.getINSTANCE(),
            base -> new AdsrDecorator(base, 0.01, 0.05, 0.7, 0.3),
            base -> new WhiteNoiseDecorator(base, 0.005)
    ),

    /** Bass drum with low-frequency sound and short ADSR envelope. */
    BASS_DRUM(
            PureTone.getINSTANCE(),
            base -> new AdsrDecorator(base, 0.0, 0.05, 0.5, 0.3)
    ),

    /** Snare drum with mid-frequency noise and very short ADSR envelope. */
    SNARE_DRUM(
            PureTone.getINSTANCE(),
            base -> new AdsrDecorator(base, 0.0, 0.01, 0.5, 0.2)
    ),

    /** Cymbal with high-frequency noise and quick attack/decay ADSR envelope. */
    CYMBAL(
            PureTone.getINSTANCE(),
            base -> new AdsrDecorator(base, 0.01, 0.2, 0.0, 0.0)
        ),

    /** Piccolo – bright high-pitched woodwind with short attack and vibrato. */
    PICCOLO(
            new Harmonic(8),
    base -> new AdsrDecorator(base, 0.02, 0.05, 0.8, 0.2),
    base -> new VibratoDecorator(base, 0.015, 6)
    ),

    /** Clarinet – smooth woodwind with odd harmonics, gentle attack, and vibrato. */
    CLARINET(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 6, i -> 2 * i - 1, (i, t) -> 1.0 / i),
    base -> new AdsrDecorator(base, 0.05, 0.1, 0.7, 0.3),
    base -> new VibratoDecorator(base, 0.01, 4)
    ),

    /** Alto Saxophone – rich mid-range woodwind with harmonics and vibrato. */
    ALTO_SAXOPHONE(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 7, i -> i, (i, t) -> 1.2 / i),
    base -> new AdsrDecorator(base, 0.03, 0.08, 0.6, 0.25),
    base -> new VibratoDecorator(base, 0.012, 5)
    ),

    /** Tenor Saxophone – deeper woodwind with harmonics and moderate vibrato. */
    TENOR_SAXOPHONE(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 7, i -> i, (i, t) -> 1.5 / i),
    base -> new AdsrDecorator(base, 0.04, 0.1, 0.6, 0.3),
    base -> new VibratoDecorator(base, 0.012, 5)
    ),

    /** Trumpet – bright brass with strong attack and harmonics. */
    TRUMPET(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 8, i -> i, (i, t) -> 1.0 / i),
    base -> new AdsrDecorator(base, 0.01, 0.05, 0.8, 0.2),
    base -> new VibratoDecorator(base, 0.008, 3)
    ),

    /** Horn in F – mellow brass with slow attack and gentle vibrato. */
    HORN_IN_F(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 6, i -> i, (i, t) -> 1.1 / i),
    base -> new AdsrDecorator(base, 0.05, 0.1, 0.7, 0.3),
    base -> new VibratoDecorator(base, 0.01, 2)
    ),

    /** Euphonium – warm low brass with smooth ADSR and harmonics. */
    EUPHONIUM(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 6, i -> i, (i, t) -> 1.3 / i),
    base -> new AdsrDecorator(base, 0.06, 0.12, 0.7, 0.3)
    ),

    /** Trombone – flexible brass with harmonic richness and moderate vibrato. */
    TROMBONE(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 7, i -> i, (i, t) -> 1.4 / i),
    base -> new AdsrDecorator(base, 0.04, 0.08, 0.7, 0.25),
    base -> new VibratoDecorator(base, 0.01, 3)
    ),

    /** Tuba – deep brass with slow attack, long release, and harmonic richness. */
    TUBA(
            new ComplexHarmonicsDecorator(PureTone.getINSTANCE(), 6, i -> i, (i, t) -> 1.5 / i),
    base -> new AdsrDecorator(base, 0.08, 0.15, 0.6, 0.35)
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
     * Retrieves an {@link Instrument} by its name (case-insensitive, spaces replaced by underscores).
     * Returns {@link #PURE_TONE} if the name is invalid or null.
     *
     * <p>Example: "Alto Saxophone" → {@link #ALTO_SAXOPHONE}</p>
     *
     * @param instrumentName The instrument name.
     * @return The corresponding instrument or {@link #PURE_TONE}.
     */
    public static Instrument getInstrumentByName(String instrumentName) {
        if (instrumentName == null) return PURE_TONE;
        String normalized = instrumentName.trim().replace(' ', '_').toUpperCase();
        for (Instrument instrument : Instrument.values()) {
            if (normalized.equals(instrument.name())) {
                return instrument;
            }
        }
        return PURE_TONE;
    }
}
