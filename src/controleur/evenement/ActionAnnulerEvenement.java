package controleur.evenement;

import controleur.module.ActionAnnulerModule;
import vue.FicheEvenement;

/**
 * Action permettant d'annuler les modifications effectuées sur la vue d'un événement.
 * @author	G14
 * @version 1.0
 */
public class ActionAnnulerEvenement extends ActionAnnulerModule {

	/**
	 * Permet de construire un ActionListener d'annulation des modifications 
	 * sur la vue d'un événement.
	 * @param ficheModule Fiche associée au gestionnaire d'événement de
	 *                    l'interface utilisateur
	 */
	public ActionAnnulerEvenement(FicheEvenement ficheModule) {
		super(ficheModule);
	}

}
