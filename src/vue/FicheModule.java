package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import controleur.module.ActionAnnulerModule;
import controleur.module.ActionSupprimerModule;
import controleur.module.ActionValiderModule;
import modele.Module;

/**
 * Permet de gérer la vue d'un module.
 *
 * @author  G14
 * @version 1.0
 */
public abstract class FicheModule extends JInternalFrame implements Observer {

	/** Module lié à la vue. */
	protected Module moduleLie;

	/**
	 * Permet de générer la fenetre interne.
	 * @param titreFenetre      Titre de la fenetre
	 */
	public FicheModule(String titreFenetre) {
		// Appel au super-constructeur
		super(titreFenetre, true, true, true, true);

		// Gestion de la fermeture
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

		// Gestion du layout
		this.setLayout(new BorderLayout());
	}


	/**
	 * Permet d'ajouter les champs de saisie à la fenetre.
	 * @param panel Panel de champs de saisie
	 */
	protected void addComposantSaisie(JComponent panel) {
		this.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Permet d'ajouter le boutons de validation, d'annulation et de suppression.
	 */
	protected void addBoutons(ActionValiderModule actionValider,
			ActionAnnulerModule actionAnnuler,
			ActionSupprimerModule actionSupprimer) {
		JPanel conteneurBoutons = new JPanel(new FlowLayout()); 
		// Bouton de validation
		JButton valider = new JButton("Valider");
		valider.addActionListener(actionValider);
		conteneurBoutons.add(valider);

		// Bouton d'annulation
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(actionAnnuler);
		conteneurBoutons.add(annuler);

		// Bouton de suppression
		JButton supprimer = new JButton("Supprimer");
		supprimer.addActionListener(actionSupprimer);
		conteneurBoutons.add(supprimer);

		// Ajout des boutons
		this.addConteneurBoutons(conteneurBoutons);
	}

	/**
	 * Permet d'ajouter la zone de boutons à la fenetre.
	 * @param conteneurBoutons Les boutons à ajouter
	 */
	protected void addConteneurBoutons(JComponent conteneurBoutons) {
		this.add(conteneurBoutons, BorderLayout.PAGE_END);
	}

	/**
	 * Permet d'afficher la fenetre.
	 */
	public void afficher() {
		this.pack();
		this.setVisible(true);
	}


	/**
	 * Permet de récupérer le module lié à la vue.
	 * @return le module lié à la vue
	 */
	public Module getModule() {
		return this.moduleLie;
	}

	/**
	 * Permet de sauvegarder les saisies de la vue dans l'instance du modèle de module.
	 * @return vrai si on peut sauvegarder les saisies
	 */
	public abstract boolean validerModifications();

	/**
	 * Permet de remettre à zéro les saisies de la vue, à partir de l'instance du modèle de module.
	 */
	public abstract void invaliderModifications();
}
