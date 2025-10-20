package fr.univartois.butinfo.lensymphony.notes.element;

import fr.univartois.butinfo.lensymphony.notes.Note;


public class Rest implements Note {
    @Override
    public double getFrequency() {
        return 0;
    }

    @Override
    public int getDuration(int tempo) {
        return 0;
    }
}
