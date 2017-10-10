package controleur.frise;

import controleur.module.ActionOuvrirModule;
import modele.Frise;
import modele.Projet;
import vue.FenetrePrincipale;

/**
 * Action permettant d'ouvrir la fiche d'une frise existante.
 * @author	G14
 * @version 1.0
 */
public class ActionOuvrirFrise extends ActionOuvrirModule {

	/**
	 * Permet de construire un ActionListener d'ouverture de la fiche d'une frise existente.
	 * @param projet        Projet à associer à la fiche
	 * @param laFenetre     Fenetre dans laquelle ajouter la fiche
	 */
	public ActionOuvrirFrise(Projet projet, FenetrePrincipale laFenetre) {
		super(projet, laFenetre, Frise.TYPE);
	}
}

