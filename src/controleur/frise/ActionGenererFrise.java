package controleur.frise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JOptionPane;

import modele.Evenement;
import modele.Frise;
import modele.Module;
import modele.Projet;
import vue.FenetrePrincipale;
import vue.FicheFrise;

/**
 * Action permettant de générer une frise à partir de tous les événements disponibles.
 * @author	G14
 * @version 1.0
 */
public class ActionGenererFrise implements ActionListener {

	/** Fenetre parente. */
	private FenetrePrincipale fenetre;

	/** Projet lié. */
	private Projet projet;

	/**
	 * Constructeur.
	 * @param fenPrincipale	Fenetre principale liée
	 * @param projet		Projet liée
	 */
	public ActionGenererFrise(FenetrePrincipale fenPrincipale, Projet projet) {
		this.fenetre = fenPrincipale;
		this.projet = projet;
	}

	/**
	 * Action effectuée : génération automatique d'une frise.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Demande de saisie de nom de frise
		String nomFrise = (String) JOptionPane.showInputDialog(this.fenetre,
				"Veuillez saisir un nom pour la frise générée automatiquement",
				"Création d'une frise",
				JOptionPane.QUESTION_MESSAGE
				);

		// Si la saisie est valide, générer la frise
		if (nomFrise != null && nomFrise.length() > 0) {
			// Récupération de la liste complète des événements
			Collection<Module> listeEve = this.projet.getListeFiltree(Evenement.TYPE);

			// Création de la frise
			Frise friseGeneree = new Frise(nomFrise, listeEve);

			// Enregistrement de son projet parent
			friseGeneree.setProjetParent(this.projet);

			// Ajouter la frise aux modules
			this.projet.ajouter(friseGeneree);

			// Ajout de la ficheFrise à la vue principale
			this.fenetre.addVueModule(new FicheFrise(friseGeneree));
		}

		// Sinon, ne rien faire
	}

}
