package controleur.evenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FicheEvenement;

/**
 * Action permettant d'ajouter un lieu touché par un événement à celui-ci.
 * @author	G14
 * @version 1.0
 */
public class ActionAjouterLieuTouche implements ActionListener {

	/** La fiche évènement à laquelle on va ajouter le lieu touche. */
	private FicheEvenement fiche;

    /**
     * Permet de construire un ActionListener d'ajout de lieu touché.
     * @param fiche Fiche à lier à l'ActionListener
     */
	public ActionAjouterLieuTouche(FicheEvenement fiche) {
		this.fiche = fiche;
	}

    /**
     * Action à effectuer : indiquer à la fiche d'ajouter le lieu aux lieux touchés.
     * @param e Evénement déclencheur
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    this.fiche.ajouterLieuTouche();
	}
}
