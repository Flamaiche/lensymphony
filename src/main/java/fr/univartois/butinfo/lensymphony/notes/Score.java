package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.Staff;

import java.util.Iterator;
import java.util.List;

/**
 * La classe {@code Score} représente une partition musicale complète,
 * composée d'une liste de portées ({@link Staff}).
 * <p>
 * Chaque portée peut correspondre à une voix ou un instrument distinct.
 * Cette classe permet d'itérer sur l'ensemble des portées qui composent
 * la partition.
 * </p>
 *
 * @author [Ton Nom]
 * @version 1.0
 */
public class Score implements Iterable<Staff> {

    /**
     * La liste des portées constituant la partition.
     */
    private final List<Staff> staffs;

    /**
     * Crée une nouvelle instance de {@code Score} avec la liste de portées spécifiée.
     *
     * @param staffs La liste des portées qui composent cette partition.
     *               Elle ne doit pas être {@code null}.
     */
    public Score(List<Staff> staffs) {
        this.staffs = staffs;
    }

    /**
     * Retourne un itérateur sur les portées de la partition.
     * <p>
     * Cela permet d'utiliser la boucle « for-each » pour parcourir
     * les différentes portées.
     * </p>
     *
     * @return Un itérateur sur les objets {@link Staff} de la partition.
     */
    @Override
    public Iterator<Staff> iterator() {
        return staffs.iterator();
    }
}
