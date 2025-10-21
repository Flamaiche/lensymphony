package fr.univartois.butinfo.lensymphony.notes;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Score implements Iterable<Note> {

    @Override
    public Iterator<Note> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Note> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Note> spliterator() {
        return Iterable.super.spliterator();
    }
}
