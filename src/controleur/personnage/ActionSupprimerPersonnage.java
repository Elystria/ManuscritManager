package controleur.personnage;

import controleur.module.ActionSupprimerModule;
import vue.FichePersonnage;

/**
 * Action permettant de gérer la suppression d'un personnage.
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerPersonnage extends ActionSupprimerModule{

	/**
	 * Permet de construire un ActionListener de suppression de personnage.
	 * @param ficheModule Fiche liée au listener.
	 */
	public ActionSupprimerPersonnage(FichePersonnage ficheModule) {
		super(ficheModule);
	}

}
