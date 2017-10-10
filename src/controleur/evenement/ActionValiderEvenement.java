package controleur.evenement;

import controleur.module.ActionValiderModule;
import vue.FicheEvenement;

/**
 * Action permettant de gérer la validation des saisies sur la vue d'un événement.
 * @author	G14
 * @version 1.0
 */
public class ActionValiderEvenement extends ActionValiderModule {

	/**
	 * Permet de construire un ActionListener de validation des saisies sur la vue d'un événement.
	 * @param ficheModule Fiche associée au listener.
	 */
	public ActionValiderEvenement(FicheEvenement ficheModule) {
		super(ficheModule);
	}

}
