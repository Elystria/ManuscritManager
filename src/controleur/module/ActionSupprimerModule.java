package controleur.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.Projet;
import vue.FicheModule;

/**
 * Action permettant de gérer la suppression d'un module.
 * @author	G14
 * @version 1.0
 */

public abstract class ActionSupprimerModule implements ActionListener {

	/** Fiche module liée. */
	private FicheModule ficheModule;

	/**
	 * Permet de générer un supresseur de module.
	 * @param ficheModule	    La fiche module à délier
	 */
	public ActionSupprimerModule(FicheModule ficheModule) {
		this.ficheModule = ficheModule;
	}

	/**
	 * Récupérer la fiche associée.
	 * @return la fiche associée au module supprimé
	 */
	public FicheModule getFicheModule(){
		return ficheModule;
	}

	/**
	 * Action effectuée:suppression de module.
	 * @param arg0 Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Vérification de la volonté de suppression du module
		int rep = JOptionPane.showConfirmDialog(ficheModule,
				"Voulez-vous confirmer la suppression ?",
				"Demande de confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null);


		if (rep == JOptionPane.YES_OPTION) {
			// Si la liste des modules ne contient pas déjà le module, on peut fermer la fenetre sans soucis
			Projet projet = this.ficheModule.getModule().getProjetParent();
			if (!projet.contient(this.ficheModule.getModule())) {
				this.ficheModule.dispose();

			} else {
				// Supprimer le module
				projet.supprimer(this.ficheModule.getModule());

				// Fermer la fenetre
				this.ficheModule.dispose();
			}
		}
	}
}
