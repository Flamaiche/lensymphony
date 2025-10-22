package fr.univartois.butinfo.lensymphony.notes.soundSynthesis;

import fr.univartois.butinfo.lensymphony.notes.decorator.*;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import java.util.function.UnaryOperator;

/**
 * Définit les instruments en combinant un synthétiseur de base avec des décorateurs (ADSR, Vibrato, Harmonics, etc.)
 */
public enum Instrument {
    PURE_TONE(PureTone.getINSTANCE()),

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

    VIOLIN(
            new Harmonic(10),
            base -> new AdsrDecorator(base, 0.1, 0.2, 0.7, 0.3),
            base -> new VibratoDecorator(base, 0.01, 5)
    ),

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

    PIANO(
            new ComplexHarmonicsDecorator(
                    PureTone.getINSTANCE(),
                    10,
                    i -> i,
                    (i, t) -> Math.exp(-2 * i * t) / i
            ),
            base -> new AdsrDecorator(base, 0.01, 0.3, 0.2, 0.5)
    ),

    FLUTE(
            new ComplexHarmonicsDecorator(
                    PureTone.getINSTANCE(),
                    5,
                    i -> 2 * i - 1,
                    (i, t) -> 1.0 / (3 * i - 1)
            ),
            base -> new AdsrDecorator(base, 0.09, 0.0, 1.0, 0.3),
//            base -> new NoiseDecorator(base, 0.003),
            base -> new VibratoDecorator(base, 0.01, 5)
    );

    private final NoteSynthesizer synthesizer;

    @SafeVarargs
    Instrument(NoteSynthesizer base, UnaryOperator<NoteSynthesizer>... effects) {
        NoteSynthesizer result = base;
        for (UnaryOperator<NoteSynthesizer> effect : effects) {
            result = effect.apply(result);
        }
        this.synthesizer = result;
    }

    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }

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
