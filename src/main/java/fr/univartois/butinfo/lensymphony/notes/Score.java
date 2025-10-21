package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.Staff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Score implements Iterable<Staff> {

    private final List<Staff> staffs;

    public Score(List<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public Iterator<Staff> iterator() {
        return staffs.iterator();
    }
}
