package modele.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import modele.Lieu;
import modele.Projet;
import vue.FicheLieu;

public class LieuTest {

	private Lieu l1, l2;
	private FicheLieu ficheLieu;
	private Projet projet;
	
	@Before
	public void setUp() {
		// initialiser le projet
		projet = new Projet();
		// initialiser le fiche de lieu
		ficheLieu = new FicheLieu(projet);
		l1 = new Lieu("Toulouse", "Occitanie");
		l2 = new Lieu(ficheLieu, projet);
	}

	@Test
	public void testerConstructeur1() {
		assertEquals(l1.getNom(), "Toulouse");
		assertEquals(l1.getDescription(), "Occitanie");
	}

	@Test
	public void testerConstructeur2() {
		assertEquals(l2.getNom(), "");
		assertEquals(l2.getDescription(), "");
	}

	@Test
	public void testerSetNom() {
		l1.setNom("Paris");
		assertEquals(l1.getNom(), "Paris");
		l2.setNom("Locronan");
		assertEquals(l2.getNom(), "Locronan");
	}

	@Test
	public void testerSetDescription() {
		l1.setDescription("Capitale de la France");
		assertEquals(l1.getDescription(), "Capitale de la France");
		l2.setDescription("petite cité de caractère");
		assertEquals(l2.getDescription(), "petite cité de caractère");
	}

	@Test
	public void testerGetType() {
		assertEquals(l1.getType(),"lieu");
		assertEquals(l2.getType(),"lieu");
	}
	
	@Test
	public void testerEquals() {
		int i = 0;
		assertFalse(l1.equals(i));
		assertFalse(l1.equals(l2));
		l2.setNom(l1.getNom());
		assertFalse(l1.equals(l2));
		l2.setDescription(l1.getDescription());
		assertTrue(l1.equals(l2));
	}
	
	@Test
	public void testerClone() {
		Lieu l3 = l1.clone();
		assertEquals(l3.getNom(), l1.getNom());
		assertEquals(l3.getDescription(), l1.getDescription());
	}
	

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LieuTest.class.getName());
	}

}
