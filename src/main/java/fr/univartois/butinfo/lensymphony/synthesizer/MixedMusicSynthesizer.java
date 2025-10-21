package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.Staff;
import fr.univartois.butinfo.lensymphony.Score;
import java.util.ArrayList;
import java.util.List;

/**
 * A composite synthesizer that allows multiple voices to be played simultaneously.
 * <p>
 * Each voice corresponds to a {@link Staff} in the {@link Score}. This class
 * implements the {@link MusicSynthesizer} interface and internally manages
 * a list of {@link SimpleMusicSynthesizer} objects, one per Staff.
 * </p>
 * <p>
 * The {@link #synthesize()} method calls the synthesize method of each
 * internal synthesizer. The {@link #getSamples()} method combines the audio
 * samples from all voices, normalizing them by the number of voices.
 * </p>
 *
 * @author Babahamou Malik
 * @version 1.0
 */
public class MixedMusicSynthesizer implements MusicSynthesizer {

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

    /**
     * Synthesizes all voices simultaneously.
     * <p>
     * This method calls {@link MusicSynthesizer#synthesize()} on each internal
     * {@link SimpleMusicSynthesizer}.
     * </p>
     */
    @Override
    public void synthesize() {
        for (MusicSynthesizer ms : synthesizers) {
            ms.synthesize();
        }
    }

    /**
     * Returns the combined audio samples of all voices.
     * <p>
     * The samples from each voice are added together and then divided by the
     * number of voices to normalize the volume. If voices have different lengths,
     * the resulting array has the length of the longest voice, with missing
     * samples treated as zero.
     * </p>
     *
     * @return The mixed audio samples as a double array.
     */
    @Override
    public double[] getSamples() {
        if (synthesizers.isEmpty()) {
            return new double[0];
        }

        int maxLength = 0;
        for (MusicSynthesizer ms : synthesizers) {
            maxLength = Math.max(maxLength, ms.getSamples().length);
        }

        double[] mixed = new double[maxLength];

        for (MusicSynthesizer ms : synthesizers) {
            double[] samples = ms.getSamples();
            for (int i = 0; i < samples.length; i++) {
                mixed[i] += samples[i];
            }
        }

        int voices = synthesizers.size();
        for (int i = 0; i < mixed.length; i++) {
            mixed[i] /= voices;
        }

        return mixed;
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