package controleur.frise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Projet;
import vue.FenetrePrincipale;
import vue.FicheFrise;

/**
 * Action permettant de créer une frise vierge.
 * @author	G14
 * @version 1.0
 */
public class ActionCreerFriseVierge implements ActionListener {

	/** Fenetre parente. */
	private FenetrePrincipale fenetre;

	/** Projet lié. */
	private Projet projet;

	/** ID de la nouvelle frise vierge. */
	private int compteur;

	/**
	 * Permet de construire un ActionListener de création d'une frise vierge.
	 * @param fenPrincipale	Fenetre principale liée
	 * @param projet		Projet liée
	 */
	public ActionCreerFriseVierge(FenetrePrincipale fenPrincipale, Projet projet) {
		this.fenetre = fenPrincipale;
		this.projet = projet;
		this.compteur = 0;
	}

	/**
	 * Action effectuée : création d'une frise vierge.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Incrémentation du compteur
		this.compteur++;

		// Création d'une nouvelle frise
		FicheFrise fiche = new FicheFrise(this.projet);

		// Changement du titre de la fiche
		fiche.setTitle("Frise vierge " + this.compteur);

		// Ajout de la nouvelle frise
		this.fenetre.addVueModule(fiche);
	}

}
