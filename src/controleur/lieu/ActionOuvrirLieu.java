package controleur.lieu;

import controleur.module.ActionOuvrirModule;
import modele.Lieu;
import modele.Projet;
import vue.FenetrePrincipale;

/**
 * Action permettant d'ouvrir la fiche d'un lieu existant.
 * @author	G14
 * @version 1.0
 */
public class ActionOuvrirLieu extends ActionOuvrirModule {

	/**
	 * Permet de construire un ActionListener d'ouverture d'un lieu existant.
	 * @param projet        Projet à associer à la fiche
	 * @param laFenetre     Fenetre dans laquelle on ajoute la fiche
	 */
	public ActionOuvrirLieu(Projet projet, FenetrePrincipale laFenetre) {
		super(projet, laFenetre, Lieu.TYPE);
	}
}

