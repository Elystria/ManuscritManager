package controleur.projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetrePrincipale;

/**
 * Action permettant de quitter l'application.
 * @author	G14
 * @version 1.0
 */
public class ActionQuitter implements ActionListener {

	/** Fenetre principale liée. */
	FenetrePrincipale fenetre;

	/**
	 * Permet de générer un gestionnaire d'événement pour quitter l'application.
	 * @param fenetre Fenetre principale à quitter
	 */
	public ActionQuitter(FenetrePrincipale fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * Action effectuée : quitter.
	 * @param arg0 Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.fenetre.dispose();
	}

}