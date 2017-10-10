package controleur.projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetrePrincipale;
/**
 * Action permettant de gérer la création d'un projet.
 * @author G14
 * @version 1.0
 */
public class ActionNouveauProjet implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		new FenetrePrincipale();
	}

}
