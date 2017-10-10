package controleur.evenement;

import controleur.module.ActionSupprimerModule;
import vue.FicheEvenement;

/**
 * Action permettant de gérer la suppression d'un événement.
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerEvenement extends ActionSupprimerModule {

	/**
	 * Permet de construire un ActionListener de suppression d'un événement.
	 * @param ficheModule Fiche liée au listener.
	 */
	public ActionSupprimerEvenement(FicheEvenement ficheModule) {
		super(ficheModule);
	}

}
