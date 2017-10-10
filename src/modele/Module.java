package modele;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import vue.FicheModule;

/**
 * Permet de définir de manière généralisée un module.
 *
 * @author      G14
 * @version     1.0
 */
public abstract class Module extends Observable implements Serializable, Observer, Cloneable {

	/**
	 * Type de module générique, attribut de classe.
	 * Exemple : "evenement" pour un Evenement.
	 */
	public static String TYPE;

	/** Fiche liée au module. */
	private transient FicheModule ficheModuleLiee;

	/** Projet auquel le module appartient. */
	private transient Projet projetParent;


	/**
	 * Permet de récupérer le type d'un module.
	 * @return le type du module
	 */
	public abstract String getType();

	/**
	 * Permet de récupérer le nom du module.
	 * @return le nom du module (pour affichage)
	 */
	public abstract String getNom();


	/**
	 * Permet de générer la fiche associée au module,
	 * si elle est inexistante.
	 * @return La fiche module nouvellement créée,
	 *         associée au module courant
	 */
	protected abstract FicheModule genererVue();

	/**
	 * Permet de récupérer la fiche associée au module.
	 * @return La fiche module associée au module
	 */
	public FicheModule getFiche() {
		// Si la fiche module est inexistante, la générer
		// et indiquer que la fenetre n'est pas visible
		if (this.ficheModuleLiee == null) {
			this.setFiche(this.genererVue());
		}

		// Renvoyer la fiche module
		return this.ficheModuleLiee;
	}

	/**
	 * Permet de récupérer le projet parent du module.
	 * @return Le projet parent du module
	 */
	public Projet getProjetParent() {
		return projetParent;
	}

	/**
	 * Permet de modifier la fiche associée au module.
	 * @param fiche Nouvelle fiche à associer au module
	 */
	protected void setFiche(FicheModule fiche) {
		// Vérification d'erreur
		if (fiche == null) {
			throw new NullPointerException(
					"La fiche renseignée n'existe pas !"
					);
		}

		// Supprimer l'ancienne frise des observateurs
		if (this.ficheModuleLiee != null) {
			this.deleteObserver(this.ficheModuleLiee);
		}

		// Enregistrement de la nouvelle fiche
		this.ficheModuleLiee = fiche;

		// Lancer l'écoute de la nouvelle fiche
		this.addObserver(this.ficheModuleLiee);
	}

	/**
	 * Permet de modifier le projet parent du module.
	 * @param projetParent Nouveau projet parent
	 */
	public void setProjetParent(Projet projetParent) {
		// Suppression de l'ancien projet des observateurs
		if (this.projetParent != null) {
			this.deleteObserver(this.projetParent);
		}

		// Enregistrement du nouveau projet parent
		this.projetParent = projetParent;

		// Enregistrement du projet en tant qu'observateur
		this.addObserver(this.projetParent);

		// Observer le projet
		this.projetParent.addObserver(this);
	}

	/**
	 * Permet de cloner/dupliquer un module.
	 * @return un module identique à celui à cloner
	 */
	public abstract Module clone();
}
