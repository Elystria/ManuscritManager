package controleur.personnage;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import vue.FichePersonnage;


/**
 * Action permettant d'ajouter une relation.
 * @author	G14
 * @version 1.0
 */
public class ActionAjouterRelation implements ActionListener {

	/** La fiche personnage à laquelle on ajoute une relation. */
	private FichePersonnage fiche;

	/**
	 * Permet de construire un ActionListener d'ajout d'une relation.
	 * @param fiche Fiche à lier à l'ActionListener
	 */
	public ActionAjouterRelation(FichePersonnage fiche) {
		this.fiche = fiche;
	}

	/**
	 * Action à effectuer : indiquer à la fiche d'ajouter la relation.
	 * @param e Evénement déclencheur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.fiche.ajouterRelation();
	}
}
