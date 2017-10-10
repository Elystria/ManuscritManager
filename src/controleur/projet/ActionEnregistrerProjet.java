package controleur.projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import modele.Projet;


/**
 * Action permettant de gérer la sauvegarde d'un projet.
 * @author	G14
 * @version 1.0
 */
public class ActionEnregistrerProjet implements ActionListener {

	/** Projet lié. */
	private Projet projet;

	/**
	 * Permet la création du listener.
	 * @param projet    Projet lié
	 */
	public ActionEnregistrerProjet(Projet projet) {
		this.projet = projet;
	}

	/**
	 * Action effectuée : enregistrer le projet.
	 * @param arg0  Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Tentative d'enregistrement du projet
		try {
			this.projet.enregistrer();

		} catch (NullPointerException err) {
			// S'il y a une erreur de pointeur nul, alors,
			// il faut enregistrer dans un nouveau fichier
			new ActionEnregistrerProjetSous(projet).enregistrerSous();

		} catch (FileNotFoundException err) {
			JOptionPane.showMessageDialog(null,
					"Erreur lors de l'enregistrement",
					"Enregistrement impossible : " + err.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
