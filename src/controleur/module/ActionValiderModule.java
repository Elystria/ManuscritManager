package controleur.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Module;
import modele.Projet;
import modele.notification.Notification;
import modele.notification.TypeNotification;
import vue.FicheModule;

/**
 * Action permettant de gérer la validation des modifications apportées à un module.
 * @author	G14
 * @version 1.0
 */

public abstract class ActionValiderModule implements ActionListener {

	/** Fiche module liée. */
	private FicheModule ficheModule;

	/**
	 * Permet de générer un validateur de module.
	 * @param ficheModule	    La fiche module à lier
	 */
	public ActionValiderModule(FicheModule ficheModule) {
		this.ficheModule = ficheModule;
	}

	/**
	 * Action effectuée: validation des modifications.
	 * @param arg0 Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		Projet projet = this.ficheModule.getModule().getProjetParent();

		// Sauvegarde de l'ancien état du module
		Module ancienEtat = this.ficheModule.getModule().clone();

		// Modifier le module si on peut valider les modifications
		if (this.ficheModule.validerModifications()){

			// Ajouter le module, s'il vient d'être créé au projet
			if (!projet.contient(this.ficheModule.getModule())) {
				projet.ajouter(this.ficheModule.getModule());

            // Sinon, notifier de sa modification
			} else {
                // Notifier le projet de la modification
                Module nouvelEtat = this.ficheModule.getModule();
                projet.update(
                        nouvelEtat,
                        new Notification(
                                ancienEtat,
                                TypeNotification.MODIFICATION,
                                nouvelEtat
                        )
                );
            }
	
			// Corriger la saisie à l'écran
			this.ficheModule.invaliderModifications();
		}
	}
}