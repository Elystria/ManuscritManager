package controleur.personnage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Relation;
import vue.FichePersonnage;

/**
 * Action permettant de supprimer une relation.
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerRelation implements ActionListener {

	/** La fiche personnage à laquelle on supprime une relation. */
	private FichePersonnage fiche;

	/** Le nom du personnage lié. */
	private Relation relation;

	/**
	 * Permet de construire un ActionListener de suppression d'une relation.
	 * @param fiche 	Fiche à lier à l'ActionListener
	 * @param relation  La relation à supprimer
	 */
	public ActionSupprimerRelation(FichePersonnage fiche, Relation relation) {
		this.fiche = fiche;
		this.relation = relation;
	}

	/**
	 * Action à effectuer : indiquer à la fiche de supprimer la relation.
	 * @param e Evénement déclencheur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.fiche.supprRelation(this.relation);
	}
}
