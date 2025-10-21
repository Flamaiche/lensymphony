package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;

class MixedMusicSynthesizer implements MusicSynthesizer {

    @Override
    public void synthesize() {

    }

    @Override
    public double[] getSamples() {
        return new double[0];
    }

    @Override
    public int getTempo() {
        return 0;
    }

    @Override
    public double getVolume() {
        return 0;
    }
}