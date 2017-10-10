package modele.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.Relation;
import modele.Personnage;
import modele.Projet;
import vue.FichePersonnage;

/** Programme de test de la classe Personnage.
 * @author Mariem Jridi
 * @version 1.1
 */
public class PersonnageTest {

	/**Personnage complet.*/
	private Personnage personnageComplet;

	/**Personnage vide.*/
	private Personnage personnageVide;

	/** Une fiche personnage. */
	private FichePersonnage fichePersonnage;

	/** Le projet associé */
	private Projet projet;

	@Before
	public void setUP() {
		projet = new Projet();
		fichePersonnage = new FichePersonnage(projet);

		//Construire les personnages
		personnageComplet = new Personnage ("Johnson","Carl","Le héros de mon livre",projet,fichePersonnage);
		personnageVide = new Personnage(fichePersonnage, projet);
	}

	@Test
	public void testerConstructeur1 () {
		assertEquals("Erreur: la valeur doit être null",personnageVide.getNomFamille(),"");
		assertEquals("Erreur: la valeur doit être null",personnageVide.getPrenom(),"");
		assertEquals("Erreur: la valeur doit être null",personnageVide.getDescription(),"");
	}

	@Test
	public void testerConstruteur2() {
		assertEquals("le nom de famille est incorect!",personnageComplet.getNomFamille(),"Johnson");
		assertEquals("le prénom est incorect!",personnageComplet.getPrenom(),"Carl");
		assertEquals("la description incorecte!",personnageComplet.getDescription(),"Le héros de mon livre");
	}

	@Test
	public void testerSetNomFamille() {
		personnageComplet.setNomFamille("Gentle");
		assertEquals("le nom de famille est incorect!",personnageComplet.getNomFamille(),"Gentle");
	}

	@Test
	public void testerSetPrenom() {
		personnageComplet.setPrenom("John");
		assertEquals("le prénom est incorect!",personnageComplet.getPrenom(),"John");
	}

	@Test
	public void testerSetDescription() {
		personnageComplet.setDescription("Aucune");
		assertEquals("la description incorecte!",personnageComplet.getDescription(),"Aucune");
	}

	@Test
	public void testerGetNom() {
		assertEquals("le nom est incorect!",personnageComplet.getNom(),"Carl Johnson");
	}

	@Test
	public void testerGetType() {
		assertEquals("le type est incorect!",personnageComplet.getType(),"personnage");
	}
	
	@Test
	public void testerSetRelationsPotentielles() {
		List<String> liste = new ArrayList<String>();
		liste.add("Michel");
		liste.add("Jean");
		personnageComplet.setRelationsPotentielles(liste);
		assertEquals(personnageComplet.getRelationsPotentielles(),liste);
	}
	
	@Test
	public void testerSetRelations() {
		List<Relation> liste = new ArrayList<Relation>();
		liste.add(new Relation("Michel", "aieul"));
		liste.add(new Relation("Jean", "cousin"));
		personnageComplet.setRelations(liste);
		assertEquals(personnageComplet.getRelations(),liste);
	}

	@Test
	public void ajouterRelation() {
		Relation r = new Relation("Michel", "aieul");
		List<Relation> liste = new ArrayList<Relation>();
		liste.add(r);
		personnageVide.ajouterRelation(r);
		assertEquals(personnageVide.getRelations(),liste);
	}
	
	@Test
	public void testerEquals() {
		int i = 0;

		List<String> liste1 = new ArrayList<String>();
		liste1.add("Michel");
		liste1.add("Jean");
		personnageComplet.setRelationsPotentielles(liste1);

		List<Relation> liste2 = new ArrayList<Relation>();
		liste2.add(new Relation("Michel", "aieul"));
		liste2.add(new Relation("Jean", "cousin"));
		personnageComplet.setRelations(liste2);

		assertFalse(personnageComplet.equals(i));
		assertFalse(personnageComplet.equals(personnageVide));
		personnageVide.setPrenom(personnageComplet.getPrenom());
		assertFalse(personnageComplet.equals(personnageVide));
		personnageVide.setNomFamille(personnageComplet.getNomFamille());
		assertFalse(personnageComplet.equals(personnageVide));
		personnageVide.setDescription(personnageComplet.getDescription());
		assertFalse(personnageComplet.equals(personnageVide));
		personnageVide.setRelationsPotentielles(personnageComplet.getRelationsPotentielles());
		assertFalse(personnageComplet.equals(personnageVide));
		personnageVide.setRelations(personnageComplet.getRelations());
		assertTrue(personnageVide.equals(personnageComplet));
	}

	@Test
	public void testerClone() {
		Personnage perso = personnageComplet.clone();
		assertTrue(perso.equals(personnageComplet));
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(PersonnageTest.class.getName());

	}
}






