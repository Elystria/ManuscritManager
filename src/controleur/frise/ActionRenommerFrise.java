package controleur.frise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modele.Frise;
import vue.FicheFrise;

/**
 * Action permettant de renommer une frise.
 * @author	G14
 * @version 1.0
 */
public class ActionRenommerFrise implements ActionListener {

	/** Fiche frise liée. */
	FicheFrise fiche;

	/**
	 * Constructeur.
	 * @param fiche La fiche de la frise liée
	 */
	public ActionRenommerFrise(FicheFrise fiche) {
		this.fiche = fiche;
	}

	/**
	 * Action effectuée : renommage de la frise.
	 * @param arg0	Evénement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Demande de saisie de nom de frise
		String nomFrise = (String) JOptionPane.showInputDialog(this.fiche,
				"Veuillez saisir un nouveau nom pour la frise :",
				"Renommage d'une frise",
				JOptionPane.QUESTION_MESSAGE
				);

		// Si la saisie est valide, générer la frise
		if (nomFrise != null && nomFrise.length() > 0) {
			// Enregistrement du nouveau nom
			((Frise) this.fiche.getModule()).setNom(nomFrise);

			// Correction de l'affichage
			this.fiche.invaliderTitre();
		}

		// Sinon, ne rien faire
	}

}
