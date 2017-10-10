package controleur.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import modele.Module;
import modele.Projet;
import vue.FenetrePrincipale;
import vue.FicheModule;

/**
 * Action permettant de gérer l'ouverture d'un module.
 * @author	G14
 * @version 1.0
 */

public abstract class ActionOuvrirModule implements ActionListener {

	/** Projet lié à ce listener. */
	protected Projet projetLie;

	/** Fenetre principale liée. */
	private FenetrePrincipale fenetre;

	/** Type de module à lister. */
	private String typeModule;


	/**
	 * Permet de construire un ActionListener d'ouverture de module.
	 * @param projet        Projet lié
	 * @param laFenetre     Fenetre principale liée
	 * @param typeModule    Type de module à lister
	 */
	public ActionOuvrirModule(Projet projet, FenetrePrincipale laFenetre, String typeModule) {
		this.projetLie = projet;
		this.fenetre = laFenetre;
		this.typeModule = typeModule;

	}

	/**
	 * Action effectuée : lister les modules ouvrables.
	 * @param arg0  Evenement déclencheur
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Génération de la liste
		Map<String, Integer> listeNomModules = new HashMap<String, Integer>();
		Map<Module, Integer> modulesDuProjet = this.projetLie.getListeModules(typeModule);
		for (Module mod : modulesDuProjet.keySet()) {
			listeNomModules.put(mod.getNom(), modulesDuProjet.get(mod));
		}

		// Si la liste des noms de modules est vide, indiquer une erreur
		if (listeNomModules.isEmpty()) {
			JOptionPane.showMessageDialog(this.fenetre,
					"Aucun module disponible dans cette catégorie !",
					"Choix d'un module",
					JOptionPane.INFORMATION_MESSAGE);

			// Sinon :
		} else {
			// Affichage et gestion de la sélection
			Object saisie = JOptionPane.showInputDialog(this.fenetre,
					"Veuillez choisir le module à ouvrir",
					"Choix d'un module",
					JOptionPane.QUESTION_MESSAGE,
					null,
					listeNomModules.keySet().toArray(), null);

			// Affichage de la vue si nécessaire
			if (saisie != null) {
				// Récupération de la saisie
				int id = listeNomModules.get(saisie);

				// Récupération du module
				Module module = this.projetLie.recupererModule(id);

				// Récupération de sa fiche
				FicheModule fiche = module.getFiche();

				// Affichage de la fiche
				fiche.setVisible(true);
			}
		}
	}
}
