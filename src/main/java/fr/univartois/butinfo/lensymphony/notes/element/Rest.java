package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;


public class Rest implements Note {

    private final int duration;

    public Rest(int duration) {
        this.duration = duration;
    }
    @Override
    public double getFrequency() {
        return 0.0;
    }

    @Override
    public int getDuration(int tempo) {
        return duration;
    }
}
