package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour {@link Score}.
 * <p>
 * Vérifie la création d'une partition et le bon fonctionnement de l'itération
 * sur les portées qui la composent.
 * </p>
 */
public class TestScore {

    private List<Staff> staffs;
    private Score score;

    /**
     * Initialise les objets utilisés dans les tests avant chaque exécution.
     */
    @BeforeEach
    void setUp() {
        staffs = new ArrayList<>();
        staffs.add(new Staff("Piano"));
        staffs.add(new Staff("Violin"));
        staffs.add(new Staff("Flute"));
        score = new Score(staffs);
    }

    @Test
    @DisplayName("Test du constructeur Score(List<Staff>)")
    void testConstructor() {
        assertNotNull(score, "L'objet Score ne doit pas être null.");
    }

    @Test
    @DisplayName("Test que les portées sont bien conservées dans la partition")
    void testStaffsContent() {
        Iterator<Staff> iterator = score.iterator();

        assertTrue(iterator.hasNext(), "La partition doit contenir au moins une portée.");
        assertEquals("Piano", iterator.next().getName(), "La première portée doit être 'Piano'.");
        assertEquals("Violin", iterator.next().getName(), "La deuxième portée doit être 'Violin'.");
        assertEquals("Flute", iterator.next().getName(), "La troisième portée doit être 'Flute'.");
    }

    @Test
    @DisplayName("Test de l'itérateur : doit parcourir toutes les portées")
    void testIterator() {
        int count = 0;
        for (Staff s : score) {
            assertNotNull(s, "Chaque portée doit être non nulle.");
            count++;
        }
        assertEquals(3, count, "La partition doit contenir exactement 3 portées.");
    }

    @Test
    @DisplayName("Test du comportement avec une liste vide")
    void testEmptyScore() {
        Score emptyScore = new Score(new ArrayList<>());
        Iterator<Staff> iterator = emptyScore.iterator();
        assertFalse(iterator.hasNext(), "Une partition vide ne doit pas avoir d'éléments à parcourir.");
    }
}
