package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class MusicalNote implements Note {
    private final double frequency;
    private final int duration;


    public MusicalNote(int duration, double frequency) {
        this.frequency = frequency;
        this.duration = duration;
    }

    @Override
    public double getFrequency() {
        return this.frequency;
    }

    @Override
    public int getDuration(int tempo) {
        return this.duration;
    }


}
