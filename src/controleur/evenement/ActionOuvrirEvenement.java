package controleur.evenement;

import controleur.module.ActionOuvrirModule;
import modele.Evenement;
import modele.Projet;
import vue.FenetrePrincipale;

/**
 * Action permettant d'ouvrir la fiche d'un événement existant.
 * @author	G14
 * @version 1.0
 */
public class ActionOuvrirEvenement extends ActionOuvrirModule {

	/**
	 * Permet de construire un ActionListener d'ouverture d'un événement.
	 * @param projet        Projet à associer à la fiche
	 * @param laFenetre     Fenetre dans laquelle ajouter la fiche
	 */
	public ActionOuvrirEvenement(Projet projet, FenetrePrincipale laFenetre) {
		super(projet, laFenetre, Evenement.TYPE);
	}
}
