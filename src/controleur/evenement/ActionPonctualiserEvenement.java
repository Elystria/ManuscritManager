package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import vue.FicheEvenement;

/**
 * Action permettant de choisir si l'événement est ponctuel ou non .
 * @author	G14
 * @version 1.0
 */
public class ActionPonctualiserEvenement implements ActionListener {

	/** Fiche évènement associée. */
	private FicheEvenement fiche;

	/** Case à étudier. */
	private JCheckBox checkBox;

    /**
     * Permet de construire un ActionListener de ponctualisation de l'événement.
     * @param fiche Fiche événement associée
     */
	public ActionPonctualiserEvenement(FicheEvenement fiche, JCheckBox checkBox){
		this.fiche = fiche;
		this.checkBox = checkBox;
	}


    /**
     * Action à effectuer : ponctualiser ou non l'événement.
     * @param e Lanceur d'événement
     */
	public void actionPerformed(ActionEvent e) {
        // Rafraichir l'affichage de la fiche :
        //   activer la gestion de la date de fin si la case est cochée
        this.fiche.activerDateFin(checkBox.isSelected());
	}

}
