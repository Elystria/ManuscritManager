package controleur.projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modele.Projet;


/**
 * Action permettant de gérer la sauvegarde d'un projet (dans un nouveau fichier).
 * @author	G14
 * @version 1.0
 */
public class ActionEnregistrerProjetSous implements ActionListener {

	/** Projet lié. */
	private Projet projet;

	/**
	 * Permet la création du listener.
	 * @param projet    Projet lié
	 */
	public ActionEnregistrerProjetSous(Projet projet) {
		this.projet = projet;
	}


	/**
	 * Action effectuée : enregistrer dans un nouveau fichier le projet.
	 * @param arg0  Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.enregistrerSous();

	}

	/**
	 * Permet d'enregistrer dans un nouveau fichier le projet.
	 */
	void enregistrerSous() {
		// Tentative d'enregistrement du projet
		try {
			// Récupération du chemin choisi par l'utilisateur
			JFileChooser selecteur = new JFileChooser();
			selecteur.setCurrentDirectory(new File("."));

			int option = selecteur.showDialog(null, "Enregistrer");
			if (option == JFileChooser.APPROVE_OPTION) {
				String nouveauChemin = selecteur.getSelectedFile().toString();

				// Normalisation du nom de fichier
				if (!nouveauChemin.endsWith(".xml")) {
					nouveauChemin += ".xml";
				}

				// Sauvegarde
				this.projet.enregistrerSous(nouveauChemin);
			}

		} catch (FileNotFoundException err) {
			JOptionPane.showMessageDialog(null,
					"Erreur lors de l'enregistrement",
					"Enregistrement impossible : " + err.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
