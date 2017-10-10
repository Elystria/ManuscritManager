package controleur.lieu;

import controleur.module.ActionAnnulerModule;
import vue.FicheLieu;

/**
 * Action permettant d'annuler les modifications effectuées sur la vue d'un lieu.
 * @author	G14
 * @version 1.0
 */

public class ActionAnnulerLieu extends ActionAnnulerModule {

	/**
	 * Permet de construire un ActionListener d'annulation 
	 * des modifications sur la vue de lieu.
	 * @param ficheModule Fiche associée au gestionnaire d'événement de
	 *                    l'interface utilisateur
	 */
	public ActionAnnulerLieu(FicheLieu ficheModule) {
		super(ficheModule);
	}

}
