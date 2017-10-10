package modele.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import modele.Date;
import modele.Evenement;
import modele.Projet;
import vue.FicheEvenement;

/** Programme de test de la classe Evenement
 * @author Ines Carrasco
 * @version 1.1
 */

public class EvenementTest {

	/** Evenements complet. */
	private Evenement evenementComplet;

	/** Evenement vide. */
	private Evenement evenementVide;

	/** Une fiche Evenement. */
	private FicheEvenement ficheEvenement;

	/** Le projet associé. */
	private Projet projet;

	@Before
	public void setUp() {
		// construire le projet
		projet = new Projet();
		// construire la fiche evenement
		ficheEvenement = new FicheEvenement(projet);
		// construire les dates
		Date dateDebut1 = new Date(31,7,1980);
		Date dateFin1 = new Date(31,7,1980);
		// construire les évènements
		evenementComplet = new Evenement(dateDebut1,dateFin1,"Naissance de Harry","Aucune",projet);
		evenementVide = new Evenement(ficheEvenement, projet);
	}

	@Test
	public void testerConstructeur1() {
		Date dateDebut = new Date(31,7,1980);
		Date dateFin = new Date(31,7,1980);
		assertEquals("Constructeur complet : date de fin", evenementComplet.getDateFin(), dateFin);
		assertEquals("Constructeur complet : date de début", evenementComplet.getDateDebut(), dateDebut);
		assertEquals("Constructeur complet : nom", evenementComplet.getNom(), "Naissance de Harry");
		assertEquals("Constructeur complet : description", evenementComplet.getDescription(), "Aucune");
	}

	@Test
	public void testerConstructeur2() {
		assertEquals("Constructeur vide : date de fin", evenementVide.getDateFin().toInteger(), 0);
		assertEquals("Constructeur vide : date de début", evenementVide.getDateDebut().toInteger(), 0);
		assertEquals("Constructeur vide : nom", evenementVide.getNom(), "");
		assertEquals("Constructeur vide : description", evenementVide.getDescription(), "");
	}

	@Test
	public void testerGetDateFin() {
		Date dateFin = new Date(31,7,1980);
		assertEquals("getDateFin : mauvaise date", evenementComplet.getDateFin(),dateFin);
	}

	@Test
	public void testerGetDateDebut() {
		Date dateDebut = new Date(31,7,1980);
		assertEquals("getDateDebut : mauvaise date", evenementComplet.getDateDebut(),dateDebut);
	}

	@Test
	public void testerGetNom() {
		assertEquals("getNom : mauvais nom", evenementComplet.getNom(),"Naissance de Harry");
	}

	@Test
	public void testerGetDescription() {
		assertEquals("getDescription : mauvaise description ", evenementComplet.getDescription(),"Aucune");
	}

	@Test
	public void testerSetDateFin() {
		Date dateFin = new Date(31,10,1981);
		evenementComplet.setDateFin(dateFin);
		assertEquals("setDateFin : mauvaise date", evenementComplet.getDateFin(),dateFin);
	}

	@Test
	public void testerSetDateDebut() {
		Date dateDebut = new Date(31,10,1981);
		evenementComplet.setDateDebut(dateDebut);
		assertEquals("setDateDebut : mauvaise date", evenementComplet.getDateDebut(),dateDebut);
	}

	@Test
	public void testerSetDescription() {
		evenementComplet.setDescription("Frôle la mort");
		assertEquals("setDescription : mauvaise description ", evenementComplet.getDescription(), "Frôle la mort");
	}

	@Test
	public void testerSetNom() {
		evenementComplet.setNom("Début de l'histoire");
		assertEquals("setNom : mauvais nom", evenementComplet.getNom(), "Début de l'histoire");
	}

	@Test
	public void testerGetType() {
		assertEquals("getType : mauvais type", evenementComplet.getType(),"evenement");
	}

	@Test
	public void testerSetPersoPresents() {
		Set<String> listePresent = new HashSet<String>();
		Set<String> listeAbsent = new HashSet<String>();

		assertEquals(evenementVide.getListePersoPresents(), listePresent);
		assertEquals(evenementVide.getListePersoAbsents(), listeAbsent);
		
		listePresent.add("Patrick");
		evenementVide.setPersoPresent("Patrick");
		assertEquals(evenementVide.getListePersoPresents(), listePresent);

		
		listePresent.add("Claude");
		evenementVide.setPersoPresent("Claude");
		assertEquals(evenementVide.getListePersoPresents(), listePresent);

		listePresent.remove("Patrick");
		listeAbsent.add("Patrick");
		evenementVide.setPersoAbsent("Patrick");
		assertEquals(evenementVide.getListePersoPresents(), listePresent);
		assertEquals(evenementVide.getListePersoAbsents(), listeAbsent);
		
		listePresent.remove("Claude");
		listeAbsent.add("Claude");
		evenementVide.setPersoAbsent("Claude");
		assertEquals(evenementVide.getListePersoPresents(), listePresent);
		assertEquals(evenementVide.getListePersoAbsents(), listeAbsent);
	}
	
	@Test
	public void testerLieuTouches() {
		Set<String> listeTouches = new HashSet<String>();
		Set<String> listeNonTouches = new HashSet<String>();

		assertEquals(evenementVide.getListeLieuxTouches(), listeTouches);
		assertEquals(evenementVide.getListeLieuxNonTouches(), listeNonTouches);
		
		listeTouches.add("Brest");
		evenementVide.setLieuTouche("Brest");
		assertEquals(evenementVide.getListeLieuxTouches(), listeTouches);

		
		listeTouches.add("Quimper");
		evenementVide.setLieuTouche("Quimper");
		assertEquals(evenementVide.getListeLieuxTouches(), listeTouches);

		listeTouches.remove("Brest");
		listeNonTouches.add("Brest");
		evenementVide.setLieuNonTouche("Brest");
		assertEquals(evenementVide.getListeLieuxTouches(), listeTouches);
		assertEquals(evenementVide.getListeLieuxNonTouches(), listeNonTouches);
		
		listeTouches.remove("Quimper");
		listeNonTouches.add("Quimper");
		evenementVide.setLieuNonTouche("Quimper");
		assertEquals(evenementVide.getListeLieuxTouches(), listeTouches);
		assertEquals(evenementVide.getListeLieuxNonTouches(), listeNonTouches);
	}
	
	@Test
	public void testerSetEstUnePeriode() {
		evenementVide.setEstUnePeriode(false);
		assertFalse(evenementVide.getEstUnePeriode());
		evenementComplet.setEstUnePeriode(true);
		assertTrue(evenementComplet.getEstUnePeriode());
		
	}
	
	@Test
	public void testerEquals() {
		int i = 0;
		assertFalse(evenementVide.equals(i));
		assertFalse(evenementVide.equals(evenementComplet));
		evenementVide.setDateDebut(evenementComplet.getDateDebut());
		evenementVide.setDateFin(evenementComplet.getDateFin());
		evenementVide.setDescription(evenementComplet.getDescription());
		evenementVide.setNom(evenementComplet.getNom());
		evenementVide.setEstUnePeriode(evenementComplet.getEstUnePeriode());
		evenementVide.setListeLieuxNonTouches(evenementComplet.getListeLieuxNonTouches());
		evenementVide.setListeLieuxTouches(evenementComplet.getListeLieuxTouches());
		evenementVide.setListePersoAbsents(evenementComplet.getListePersoAbsents());
		evenementVide.setListePersoPresents(evenementComplet.getListePersoPresents());		
		assertTrue(evenementVide.equals(evenementComplet));
	}
	
	@Test
	public void testerClone() {
		Evenement evenementClone = evenementComplet.clone();
		assertTrue(evenementClone.equals(evenementComplet));
	}
	
	@Test
	public void testerCompareTo() {
		assertTrue(evenementVide.compareTo(evenementComplet) < 0);
		assertFalse(evenementComplet.compareTo(evenementVide) < 0);
	}
	
	@Test
	public void testerGetIndice() {
		assertTrue(evenementVide.getIndice("Michel") == -1);
		evenementVide.setPersoPresent("Michel");
		assertTrue(evenementVide.getIndice("Michel") == 0);
		evenementVide.setPersoAbsent("Michel");
		assertTrue(evenementVide.getIndice("Michel") == -1);
	}
	
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(EvenementTest.class.getName());
	}

}
