package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controleur.frise.ActionRenommerFrise;
import controleur.frise.ActionSelectionnerEvenementFrise;
import modele.Frise;
import modele.Projet;
import modele.notification.Notification;


/**
 * Permet de gérer la vue d'une frise.
 *
 * @author      G14
 * @version     1.0
 */
public class FicheFrise extends FicheModule {

	/** Zone de dessin de la frise. */
	JZoneFrise zoneFrise;

	/** Zone de scroll contenant la frise. */
	JScrollPane scrollZone;


	/**
	 * Création d'une nouvelle frise.
	 *
	 * @param projetAssocie Projet auquel on va  associer la fiche
	 */
	public FicheFrise(Projet projetAssocie) {
		// Appel du super-constructeur
		super("Frise vierge");

		// Création d'un module vierge associé au projet
		this.moduleLie = new Frise(this, projetAssocie);

		// Création de l'interface :
		//      - Zone de dessin de frise
		this.zoneFrise = new JZoneFrise((Frise) this.moduleLie);

		//      - Zone défilable contenant la frise
		this.scrollZone = new JScrollPane(this.zoneFrise);

		//      - Ajout à la fenetre
		super.addComposantSaisie(scrollZone);


		// Ajout des boutons de gestion :
		//      - Création du panel d'accueil
		JPanel panelBoutons = new JPanel(new FlowLayout());

		//      - Bouton "Renommer la frise"
		JButton boutonRenommer = new JButton("Renommer la frise");
		boutonRenommer.addActionListener(new ActionRenommerFrise(this));
		panelBoutons.add(boutonRenommer);

		//      - Bouton "Sélectionner les événements à afficher"
		JButton boutonSelectEvents = new JButton(
				"Sélectionner les événements à afficher"
				);
		boutonSelectEvents.addActionListener(new ActionSelectionnerEvenementFrise(this));
		panelBoutons.add(boutonSelectEvents);

		//      - Ajout à la fenetre
		super.addConteneurBoutons(panelBoutons);

		// Définir une taille minimale
		super.setMinimumSize(new Dimension(500,500));

		// Afficher
		super.afficher();
	}

	/**
	 * Permet de générer une vue à partir d'un module existant.
	 *
	 * @param frise La Frise à partir de laquelle on va  générer la vue
	 */
	public FicheFrise(Frise frise) {
		// Création de la vue
		this(frise.getProjetParent());

		// Enregistrement du module
		this.moduleLie = frise;

		// Indiquer à la zone de dessin de la frise la nouvelle frise
		this.zoneFrise.setFriseLiee(frise);

		// Invalidation des modifications (pour génération de la frise)
		this.invaliderModifications();

		// Changer le titre de la fenetre
		this.invaliderTitre();
	}


	/**
	 * Permet de sauvegarder les saisies de la vue dans l'instance du modèle de module.
	 */
	@Override
	public boolean validerModifications() {
		// Lever une exception car il n'y a pas de modifications à apporter à une frise
		throw new UnsupportedOperationException(
				"Il n'est pas possible de valider des modifications sur une frise !"
				);
	}

	/**
	 * Permet d'invalider les saisies de la vue depuis l'instance du modèle de module.
	 */
	@Override
	public void invaliderModifications() {
		/* Cette fonction est utilisée pour générer la vue de la frise, à partir de la
		 * liste des événements du modèle de frise.
		 */
		this.zoneFrise.revalidate();
		this.repaint();
	}

	/**
	 * Permet de mettre à jour le titre de la fenetre.
	 */
	public void invaliderTitre() {
		this.setTitle("Frise - " + this.moduleLie.getNom());
	}

	/**
	 * Permet de mettre à jour une frise.
	 * @param observable    Objet observé (Module)
	 * @param arguments     Objet de notification (Notification)
	 */
	@Override
	public void update(Observable observable, Object arguments) {
		// Si la notification est valide, la prendre en compte
		if (observable instanceof Frise && arguments instanceof Notification) {
			// Retracer la frise
			this.invaliderModifications();
		}
	}
}