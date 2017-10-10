package controleur.personnage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Projet;
import vue.FenetrePrincipale;
import vue.FichePersonnage;

/**
 * Action permettant de créer un personnage.
 * @author	G14
 * @version 1.0
 */
public class ActionCreerPersonnage implements ActionListener {

	/** Fenetre parente. */
	private FenetrePrincipale fenetre;

	/** Projet lié. */
	private Projet projet;

	/**
	 * Permet de construire un ActionListener de création d'un personnage.
	 * @param fenPrincipale	Fenetre principale liée
	 * @param projet		Projet liée
	 */
	public ActionCreerPersonnage(FenetrePrincipale fenPrincipale, Projet projet) {
		this.fenetre = fenPrincipale;
		this.projet = projet;
	}

	/**
	 * Action effectuée : affichage d'une nouvelle vue.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.fenetre.addVueModule(new FichePersonnage(this.projet));
	}

}
