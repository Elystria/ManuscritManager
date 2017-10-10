package controleur.frise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import vue.FicheFrise;

/**
 * Action permettant de sélectionner un événement de la frise.
 * @author	G14
 * @version 1.0
 */
public class ActionSelectionnerEvenementFrise implements ActionListener {

	/** Fiche frise liée. */
	FicheFrise fiche;

	/**
	 * Permet de construire un ActionListener de séléction des événements.
	 * @param fiche La fiche frise liée
	 */
	public ActionSelectionnerEvenementFrise(FicheFrise fiche) {
		this.fiche = fiche;
	}

	/**
	 * Action effectuée : sélection des événements.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Demande de saisie de nom de frise
		JOptionPane.showMessageDialog(this.fiche,
				"Fonction non implémentée !",
				"Sélection des événements de la frise",
				JOptionPane.INFORMATION_MESSAGE
				);
	}

}
