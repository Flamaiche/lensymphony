package fr.univartois.butinfo.lensymphony.notes.decorator;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * A decorator that extends a note’s duration by adding dots.
 * <p>
 * Each dot increases the note’s duration by half of the previous addition.
 * A note can have up to three dots.
 * </p>
 */
public class DottedNotes extends DecoratorNote {

    /**
     * The number of dots applied to this note.
     */
    private final int exponent;

    /**
     * Creates a dotted version of the given note.
     * If the note is already dotted, the dot count increases automatically.
     *
     * @param note The note to decorate.
     * @throws IllegalArgumentException If more than three dots are applied.
     */
    public DottedNotes(Note note) {
        super(note);
        int nextExponent = 1;
        if (note instanceof DottedNotes dotted) {
            nextExponent = dotted.getExponent() + 1;
        }
        if (nextExponent > 3) {
            throw new IllegalArgumentException("The note can't have more than 3 dots");
        }
        this.exponent = nextExponent;
    }

    /**
     * Returns the note’s frequency (unchanged).
     */
    @Override
    public double getFrequency() {
        return note.getFrequency();
    }

    /**
     * Returns the dotted note’s duration, extended based on its dot count.
     */
    @Override
    public int getDuration(int tempo) {
        double base = note.getDuration(tempo);
        double additional = base / Math.pow(2, exponent);
        return (int) Math.round(base + additional);
    }

    /**
     * Returns the number of dots applied to this note.
     */
    public int getExponent() {
        return exponent;
    }
}