package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FicheEvenement;

/**
 * Action permettant d'ajouter un personnage présent à un événement.
 * @author	G14
 * @version 1.0
 */
public class ActionAjouterPersonnagePresent implements ActionListener {

	/** La fiche évènement à laquelle on ajoute un personnage présent. */
	private FicheEvenement fiche;

    /**
     * Permet de construire un ActionListener d'ajout de personnage présent.
     * @param fiche Fiche à lier à l'ActionListener
     */
	public ActionAjouterPersonnagePresent(FicheEvenement fiche) {
		this.fiche = fiche;
	}

    /**
     * Action à effectuer : indiquer à la fiche d'ajouter le personnage en tant que
     * personnage présent.
     * @param e Evénement déclencheur
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    this.fiche.ajouterPersoPresent();
	}
}
