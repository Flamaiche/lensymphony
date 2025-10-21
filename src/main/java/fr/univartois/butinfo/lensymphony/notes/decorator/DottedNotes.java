package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class DottedNotes implements Note {

    private final Note note;
    private final int exponent;

    public DottedNotes(Note note) {
        int nextExponent = 1;
        if(note instanceof DottedNotes dotted) {
            nextExponent=dotted.getExponent()+1;
        }
        if(nextExponent>3) {
            throw new IllegalArgumentException("The note can't have more than 3 dots");
        }
        this.note = note;
        this.exponent = nextExponent;
    }

    @Override
    public double getFrequency() {
        return note.getFrequency();
    }

    @Override
    public int getDuration(int tempo) {
        double baseDuration = note.getDuration(tempo);
        double additionnal = baseDuration / Math.pow(2, exponent);
        return (int) Math.round(baseDuration + additionnal);
    }

    public int getExponent() {
        return exponent;
    }

    public Note getNote() {
        return note;
    }
}
