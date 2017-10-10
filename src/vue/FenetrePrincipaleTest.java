package vue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JMenu;

import org.junit.Before;
import org.junit.Test;

import modele.Projet;

public class FenetrePrincipaleTest {

	private FenetrePrincipale fenetrePrincipale1;
	private FicheModule fiche;
	private Projet projet;

	@Before
	public void setUp() {
		projet = new Projet();
		fenetrePrincipale1 = new FenetrePrincipale();
		fiche = new FicheEvenement(projet);
	}

	@Test
	public void testFenetrePrincipale() {

		//test localisation et dimension fenetre
		Rectangle dimensions = fenetrePrincipale1.getBounds();
		//assertEquals(10, dimensions.getX(), 0);
		//assertEquals(10, dimensions.getY(), 0);
		//assertEquals(700, dimensions.getHeight(), 0);
		//assertEquals(900, dimensions.getWidth(), 0);

		//Projet et Fenetres Internes initialises
		assertNotNull(fenetrePrincipale1.getProjet());
		assertNotNull(fenetrePrincipale1.getFenetresInternes());

		//Barre Menu Créé correctement
		assertNotNull(fenetrePrincipale1.getJMenuBar());
		assertEquals(4, fenetrePrincipale1.getJMenuBar().getMenuCount());
		JMenu menu1 = fenetrePrincipale1.getJMenuBar().getMenu(0);
		assertEquals(5, menu1.getItemCount());
		JMenu menu2 = fenetrePrincipale1.getJMenuBar().getMenu(1);
		assertEquals(3, menu2.getItemCount());
		JMenu menu3 = fenetrePrincipale1.getJMenuBar().getMenu(2);
		assertEquals(3, menu3.getItemCount());
		JMenu menu4 = fenetrePrincipale1.getJMenuBar().getMenu(3);
		assertEquals(3, menu4.getItemCount());

		//Fenetre Visible et operation de fermeture par defaut
		assertTrue(fenetrePrincipale1.isVisible());
		assertEquals(fenetrePrincipale1.getDefaultCloseOperation(), JFrame.DISPOSE_ON_CLOSE);

	}

	@Test
	public void testAddVueModule() {
		fenetrePrincipale1.addVueModule(fiche);
		assertEquals(this.fenetrePrincipale1.getFenetresInternes().getComponentCount(), 1);
	}

}
