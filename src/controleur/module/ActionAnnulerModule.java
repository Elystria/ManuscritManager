package controleur.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Projet;
import vue.FicheModule;

/**
 * Action permettant de gérer l'annulation des modifications d'une fiche module.
 * @author	G14
 * @version 1.0
 */

public abstract class ActionAnnulerModule implements ActionListener {

	/** Fiche module liée. */
	private FicheModule ficheModule;

	/**
	 * Permet de générer un annulateur des modifications de module.
	 * @param ficheModule	    La fiche module à lier
	 */
	public ActionAnnulerModule(FicheModule ficheModule) {
		this.ficheModule = ficheModule;
	}

	/**
	 * Action effectuée: annulation des modifications de module.
	 * @param arg0 Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Si la liste des modules ne contient pas déjà le module,
		// on peut fermer la fenetre sans problème
		Projet projet = this.ficheModule.getModule().getProjetParent();

		if (!projet.contient(this.ficheModule.getModule())) {
			this.ficheModule.dispose();

		} else {
			// Sinon, il faut invalider les modifications
			this.ficheModule.invaliderModifications();
		}
	}

}