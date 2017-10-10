package controleur.lieu;

import controleur.module.ActionSupprimerModule;
import vue.FicheLieu;

/**
 * Action permettant de gérer la suppression d'un lieu.
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerLieu extends ActionSupprimerModule{

	/**
	 * Permet de construire un ActionListener de suppression d'un lieu.
	 * @param ficheModule Fiche liée au listener.
	 */
	public ActionSupprimerLieu(FicheLieu ficheModule) {
		super(ficheModule);
	}

}
