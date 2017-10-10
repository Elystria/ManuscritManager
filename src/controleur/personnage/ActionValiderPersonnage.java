package controleur.personnage;

import controleur.module.ActionValiderModule;
import vue.FichePersonnage;
/**
 * Action permettant de gérer la validation des saisies sur la vue d'un personnage.
 * @author	G14
 * @version 1.0
 */

public class ActionValiderPersonnage extends ActionValiderModule {

	/**
	 * Permet de construire un ActionListener de validation des modifications
	 * sur la vue d'un personnage.
	 * @param ficheModule Fiche associée au listener.
	 */
	public ActionValiderPersonnage(FichePersonnage ficheModule) {
		super(ficheModule);
	}

}
