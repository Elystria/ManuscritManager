package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import vue.FicheEvenement;
/**
 * Action permettant de  supprimer un personnage présent à un événement.
 * @author	G14
 * @version 1.0
 */
public class ActionSupprimerPersonnagePresent implements ActionListener {

	/** La fiche évènement à laquelle on supprime un personnage présent. */
	private FicheEvenement fiche;

	/** Le label du personnage lié. */
	private JLabel labelPerso;

    /**
     * Permet de construire un ActionListener de suppression de personnage présent.
     * @param fiche         Fiche à lier à l'ActionListener
     * @param labelPerso    Label du nom du personnage lié
     */
	public ActionSupprimerPersonnagePresent(FicheEvenement fiche, JLabel labelPerso) {
		this.fiche = fiche;
		this.labelPerso = labelPerso;
	}

    /**
     * Action à effectuer : indiquer à la fiche de supprimer le personnage en tant que
     * personnage présent (donc le mettre en tant que personnage absent).
     * @param e Evénement déclencheur
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    this.fiche.supprPersoPresent(this.labelPerso.getText());
	}
}
