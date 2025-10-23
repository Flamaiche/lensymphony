package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.decorator.*;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;

/**
 * Represents musical instruments by directly wrapping base synthesizers
 * with decorators to produce the characteristic timbre of each instrument.
 *
 * <p>All instruments are created by nesting decorators inside each other.</p>
 *
 * <p>Example:</p>
 * <pre>
 * VIOLIN(
 *     new VibratoDecorator(
 *         new AdsrDecorator(
 *             new Harmonic(10),
 *             0.1, 0.2, 0.7, 0.3
 *         ),
 *         0.01, 5
 *     )
 * )
 * </pre>
 */
public enum Instrument {

    PURE_TONE(PureTone.getINSTANCE()),

    CONTRABASS(
            new ComplexHarmonicsDecorator(
                    new AdsrDecorator(
                            new Harmonic(2),
                            0.2, 0.3, 0.8, 0.4
                    ),
                    8,
                    i -> i,
                    (i, t) -> 1.0 / i
            )
    ),

    VIOLIN(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new Harmonic(10),
                            0.1, 0.2, 0.7, 0.3
                    ),
                    0.01, 5
            )
    ),

    GUITAR(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    8,
                                    i -> i,
                                    (i, t) -> 1.5 * i
                            ),
                            0.008, 0.05, 0.2, 2.5
                    ),
                    0.02, 3
            )
    ),

    PIANO(
            new AdsrDecorator(
                    new ComplexHarmonicsDecorator(
                            PureTone.getINSTANCE(),
                            10,
                            i -> i,
                            (i, t) -> Math.exp(-2 * i * t) / i
                    ),
                    0.01, 0.3, 0.2, 0.5
            )
    ),

    FLUTE(
            new VibratoDecorator(
                    new WhiteNoiseDecorator(
                            new AdsrDecorator(
                                    new ComplexHarmonicsDecorator(
                                            PureTone.getINSTANCE(),
                                            5,
                                            i -> 2 * i - 1,
                                            (i, t) -> 1.0 / (3 * i - 1)
                                    ),
                                    0.09, 0.0, 1.0, 0.3
                            ),
                            0.003
                    ),
                    0.01, 5
            )
    ),

    PICCOLO(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new Harmonic(8),
                            0.02, 0.05, 0.8, 0.2
                    ),
                    0.015, 6
            )
    ),

    CLARINET(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    6,
                                    i -> 2 * i - 1,
                                    (i, t) -> 1.0 / i
                            ),
                            0.05, 0.1, 0.7, 0.3
                    ),
                    0.01, 4
            )
    ),

    ALTO_SAXOPHONE(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    7,
                                    i -> i,
                                    (i, t) -> 1.2 / i
                            ),
                            0.03, 0.08, 0.6, 0.25
                    ),
                    0.012, 5
            )
    ),

    TENOR_SAXOPHONE(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    7,
                                    i -> i,
                                    (i, t) -> 1.5 / i
                            ),
                            0.04, 0.1, 0.6, 0.3
                    ),
                    0.012, 5
            )
    ),

    TRUMPET(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    8,
                                    i -> i,
                                    (i, t) -> 1.0 / i
                            ),
                            0.01, 0.05, 0.8, 0.2
                    ),
                    0.008, 3
            )
    ),

    HORN_IN_F(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    6,
                                    i -> i,
                                    (i, t) -> 1.1 / i
                            ),
                            0.05, 0.1, 0.7, 0.3
                    ),
                    0.01, 2
            )
    ),

    EUPHONIUM(
            new AdsrDecorator(
                    new ComplexHarmonicsDecorator(
                            PureTone.getINSTANCE(),
                            6,
                            i -> i,
                            (i, t) -> 1.3 / i
                    ),
                    0.06, 0.12, 0.7, 0.3
            )
    ),

    TROMBONE(
            new VibratoDecorator(
                    new AdsrDecorator(
                            new ComplexHarmonicsDecorator(
                                    PureTone.getINSTANCE(),
                                    7,
                                    i -> i,
                                    (i, t) -> 1.4 / i
                            ),
                            0.04, 0.08, 0.7, 0.25
                    ),
                    0.01, 3
            )
    ),

    TUBA(
            new AdsrDecorator(
                    new ComplexHarmonicsDecorator(
                            PureTone.getINSTANCE(),
                            6,
                            i -> i,
                            (i, t) -> 1.5 / i
                    ),
                    0.08, 0.15, 0.6, 0.35
            )
    ),

    TRIANGLE(
            new AdsrDecorator(
                    new ComplexHarmonicsDecorator(
                            PureTone.getINSTANCE(),
                            7,
                            i -> 2000 + 800 * i,
                            (i, t) -> Math.exp(-5 * (0.5 + 0.3 * i))
                    ),
                    0.01, 0.05, 0.7, 0.2
            )
    );

    /** The final synthesizer after all decorators are applied. */
    private final NoteSynthesizer synthesizer;

    /**
     * Constructs an instrument with a pre-wrapped synthesizer.
     *
     * @param synthesizer The fully decorated synthesizer.
     */
    Instrument(NoteSynthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Returns the final synthesizer of this instrument.
     *
     * @return The complete {@link NoteSynthesizer}.
     */
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }

    /**
     * Retrieves an instrument by name (case-insensitive, spaces replaced with underscores).
     * Returns {@link #PURE_TONE} if the name is invalid or null.
     *
     * @param instrumentName Name of the instrument.
     * @return Corresponding {@link Instrument} or {@link #PURE_TONE}.
     */
    public static Instrument getInstrumentByName(String instrumentName) {
        return TRIANGLE;
//        if (instrumentName == null) return PURE_TONE;
//        String normalized = instrumentName.trim().replace(' ', '_').toUpperCase();
//        for (Instrument instrument : Instrument.values()) {
//            if (normalized.equals(instrument.name())) {
//                return instrument;
//            }
//        }
//        return PURE_TONE;
    }
}
