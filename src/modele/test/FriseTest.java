package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import modele.Module;
import modele.Frise;
import modele.Projet;
import vue.FicheEvenement;
import vue.FicheFrise;
import modele.Date;
import modele.Evenement;

public class FriseTest {
	
	/** Evenements complet. */
	private Frise frise1;

	/** Evenement vide. */
	private Frise frise2;

	/** Une fiche Evenement. */
	private FicheFrise ficheFrise;

	/** Le projet associé. */
	private Projet projet;
	
	@Before
	public void setUp() {
		// construire le projet
		projet = new Projet();
		// construire la fiche evenement
		ficheFrise = new FicheFrise(projet);
		// construire les frises
		frise1 = new Frise("nomFrise",new TreeSet<Module>());
		frise2 = new Frise(ficheFrise, projet);
	}
	
	@Test
	public void testerConstucteur1() {
		assertEquals(frise1.getNom(), "nomFrise");
		assertEquals(frise1.getEvenementsDansFrise(), new TreeSet<Module>());
	}
	
	@Test
	public void testerConstructeur2() {
		assertEquals(frise2.getNom(), "");
		assertEquals(frise2.getEvenementsDansFrise(), new TreeSet<Module>());
	}

	@Test
	public void testerSetNom() {
		frise1.setNom("nouvelle frise");
		assertEquals(frise1.getNom(), "nouvelle frise");
	}
	
	@Test
	public void testerSetEvenementsDansFrise() {
		Evenement evenement1 = new Evenement();
		SortedSet<Evenement> liste = new TreeSet<Evenement>();
		liste.add(evenement1);
		frise1.setEvenementsDansFrise(liste);
		assertEquals(frise1.getEvenementsDansFrise(), liste);
	}
	
	@Test
	public void testerGetPlusAncienEvenement() {
		Evenement evenement1 = new Evenement();
		Evenement evenement2 = new Evenement();
		SortedSet<Evenement> liste = new TreeSet<Evenement>();
		liste.add(evenement1);
		liste.add(evenement2);
		frise1.setEvenementsDansFrise(liste);
		assertEquals(frise1.getPlusAncienEvenement(), evenement2);
		assertEquals(frise1.getPlusRecentEvenement(), evenement1);
	}
	
	@Test
	public void testerAddEvenement() {
		Evenement evenement1 = new Evenement();
		SortedSet<Evenement> liste = new TreeSet<Evenement>();
		liste.add(evenement1);
		frise1.addEvenement(evenement1);
		assertEquals(frise1.getEvenementsDansFrise(), liste);
		assertTrue(frise1.containsEvenement(evenement1));
		liste.remove(evenement1);
		frise1.removeEvenement(evenement1);
		assertEquals(frise1.getEvenementsDansFrise(), liste);
		assertFalse(frise1.containsEvenement(evenement1));
	}
	
	@Test
	public void testerGetType() {
		assertEquals(frise1.getType(), "frise");
	}
	@Test
	public void testerClone() {
		Frise friseClone = frise1.clone();
		friseClone.equals(frise1);
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(FriseTest.class.getName());
	}
}
