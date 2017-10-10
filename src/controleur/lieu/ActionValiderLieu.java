package controleur.lieu;

import controleur.module.ActionValiderModule;
import vue.FicheLieu;

/**
 * Action permettant de gérer la validation des saisies sur la vue d'un lieu.
 * @author	G14
 * @version 1.0
 */
public class ActionValiderLieu extends ActionValiderModule {

	/**
	 * Permet de construire un ActionListener de validation des saisies sur la vue de lieu.
	 * @param ficheModule Fiche associée au listener.
	 */
	public ActionValiderLieu(FicheLieu ficheModule) {
		super(ficheModule);
	}

}
