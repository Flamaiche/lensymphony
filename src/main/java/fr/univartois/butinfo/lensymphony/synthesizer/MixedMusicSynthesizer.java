package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.Score;
import fr.univartois.butinfo.lensymphony.Staff;
import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;

import java.util.ArrayList;
import java.util.List;

class MixedMusicSynthesizer implements MusicSynthesizer {

    /** The tempo in beats per minute (BPM). */
    private int tempo;

    /** The list of internal synthesizers, one per voice/Staff. */
    private List<MusicSynthesizer> synthesizers = new ArrayList<>();

    /**
     * Constructs a MixedMusicSynthesizer from a {@link Score}.
     * <p>
     * Each {@link Staff} in the score becomes a voice in the composite
     * and uses its own instrument's {@link NoteSynthesizer}.
     * </p>
     *
     * @param score The musical score containing multiple voices.
     * @param tempo The tempo to use for all voices.
     */
    public MixedMusicSynthesizer(Score score, int tempo) {
        this.tempo = tempo;
        for (Staff staff : score) {
            synthesizers.add(new SimpleMusicSynthesizer(tempo, staff, staff.getInstrument().getSynthesizer()));
        }
    }


    @Override
    public void synthesize() {

    }

    @Override
    public double[] getSamples() {
        return new double[0];
    }

    /**
     * Returns the tempo used by this synthesizer.
     *
     * @return The tempo in beats per minute (BPM).
     */
    @Override
    public int getTempo() {
        return tempo;
    }

    /**
     * Returns the default volume level.
     *
     * @return The volume (0 by default, can be adjusted if needed).
     */
    @Override
    public double getVolume() {
        return 0;
    }
}