package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import vue.FicheEvenement;

/**
 * Action permettant de  supprimer un lieu touché par un événement 
 * (et l'indiquer comme non touché par l'événement). .
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerLieuTouche implements ActionListener {

	/** La fiche évènement à laquelle on supprime un lieu touché par l'événement. */
	private FicheEvenement fiche;

	/** Le label du lieu lié. */
	private JLabel labelLieu;

    /**
     * Permet de construire un ActionListener de suppression de lieu touché.
     * @param fiche         Fiche à lier à l'ActionListener
     * @param labelLieu     Label du lieu lié
     */
	public ActionSupprimerLieuTouche(FicheEvenement fiche, JLabel labelLieu) {
		this.fiche = fiche;
		this.labelLieu = labelLieu;
	}

    /**
     * Action à effectuer : indiquer à la fiche de supprimer le lieu touché (et donc
     * de le mettre comme lieu non touché par l'événement).
     * @param e Evénement déclencheur
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    this.fiche.supprLieuTouche(this.labelLieu.getText());
	}
}
