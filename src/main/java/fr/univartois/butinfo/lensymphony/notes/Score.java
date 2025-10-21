package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.Staff;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Score implements Iterable<Staff> {
    @Override
    public Iterator<Staff> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Staff> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Staff> spliterator() {
        return Iterable.super.spliterator();
    }
}
