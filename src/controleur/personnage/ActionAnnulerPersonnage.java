package controleur.personnage;

import controleur.module.ActionAnnulerModule;
import vue.FichePersonnage;

/**
 * Action permettant d'annuler les modifications effectuées sur la vue d'un personnage.
 * @author	G14
 * @version 1.0
 */
public class ActionAnnulerPersonnage extends ActionAnnulerModule {

	/**
	 * Permet de construire un ActionListener d'annulation des modifications sur la vue du personnage.
	 * @param ficheModule Fiche associée au gestionnaire d'événement de
	 *                    l'interface utilisateur
	 */
	public ActionAnnulerPersonnage(FichePersonnage ficheModule) {
		super(ficheModule);
	}

}
