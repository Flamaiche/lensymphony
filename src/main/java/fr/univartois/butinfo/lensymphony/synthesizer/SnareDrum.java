package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class SnareDrum implements  NoteSynthesizer {

    private final static double A = 0.01;
    //private final static  V = 1.0;


    private static SnareDrum INSTANCE;

    public static SnareDrum getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SnareDrum();
        }
        return INSTANCE;
    }


    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double duration = note.getDuration(tempo);
        int totalSamples = (int) (duration * SAMPLE_RATE);

        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = (double) i / SAMPLE_RATE ;


            double envelope;
            if (t < A){
                envelope = t / A ;
            } else {
                envelope = Math.exp( 15 * (A - t));
            }
        }
        return null;
    }
}
