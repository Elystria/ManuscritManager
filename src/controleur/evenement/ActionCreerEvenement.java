package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Projet;
import vue.FenetrePrincipale;
import vue.FicheEvenement;

/**
 * Action permettant de créer un nouvel événement.
 * @author	G14
 * @version 1.0
 */
public class ActionCreerEvenement implements ActionListener {

	/** Fenetre parente. */
	private FenetrePrincipale fenetre;

	/** Projet lié. */
	private Projet projet;

	/**
	 * Permet de construire un ActionListener de création d'un nouvel événement.
	 * @param fenPrincipale	Fenetre principale liée
	 * @param projet		Projet liée
	 */
	public ActionCreerEvenement(FenetrePrincipale fenPrincipale, Projet projet) {
		this.fenetre = fenPrincipale;
		this.projet = projet;
	}

	/**
	 * Action effectuée : affichage d'une nouvelle vue.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.fenetre.addVueModule(new FicheEvenement(this.projet));
	}

}
