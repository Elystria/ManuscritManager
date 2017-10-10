package controleur.personnage;

import controleur.module.ActionOuvrirModule;
import modele.Personnage;
import modele.Projet;
import vue.FenetrePrincipale;

/**
 * Action permettant d'ouvrir la fiche d'un personnage.
 * @author	G14
 * @version 1.0
 */

public class ActionOuvrirPersonnage extends ActionOuvrirModule {

	/**
	 * Permet de construire un ActionListener d'ouverture de la fiche d'un personnage.
	 * @param projet        Projet à associer à la fiche
	 * @param laFenetre     Fenetre dans laquelle on ajoute la fiche
	 */
	public ActionOuvrirPersonnage(Projet projet, FenetrePrincipale laFenetre) {
		super(projet, laFenetre, Personnage.TYPE);
	}
}
