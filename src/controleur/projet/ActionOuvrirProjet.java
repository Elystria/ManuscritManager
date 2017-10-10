package controleur.projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modele.Projet;
import vue.FenetrePrincipale;
import vue.FicheModule;

/**
 * Action permettant de gérer l'ouverture d'un projet.
 * @author	G14
 * @version 1.0
 */
public class ActionOuvrirProjet implements ActionListener {

	/** Projet lié. */
	private Projet projet;

	/** Fenetre principale liée. */
	private FenetrePrincipale laFenetrePrincipale;

	/**
	 * Permet la création du listener.
	 * @param laFenetrePrincipale   Fenetre principale liée
	 * @param projet                Projet lié
	 */
	public ActionOuvrirProjet(FenetrePrincipale laFenetrePrincipale, Projet projet) {
		this.laFenetrePrincipale = laFenetrePrincipale;
		this.projet = projet;
	}


	/**
	 * Action effectuée : ouvrir le projet.
	 * @param arg0  Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Récupération du chemin choisi par l'utilisateur
		JFileChooser selecteur = new JFileChooser();
		selecteur.setCurrentDirectory(new File("."));

		int option = selecteur.showDialog(null, "Ouvrir");
		if (option == JFileChooser.APPROVE_OPTION) {
			String cheminChargement = selecteur.getSelectedFile().toString();

			// Demander l'ouverture
			this.ouvrir(cheminChargement);
		}
	}

	/**
	 * Permet de demander l'ouverture d'un projet.
	 * @param cheminChargement Chemin du fichier à charger
	 */
	public void ouvrir(String cheminChargement) {
		// Tentative de chargement du projet
		try {
			// Chargement du projet
			this.projet.charger(cheminChargement);

			// Ajout des fenetres masquées à la fenetre principale
			for (FicheModule fiche : this.projet.getFichesModules()) {
				this.laFenetrePrincipale.addVueModule(fiche);
			}

		} catch (FileNotFoundException err) {
			JOptionPane.showMessageDialog(null,
					"Chargement impossible :\n" + err.getMessage(),
					"Erreur lors du chargement",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}