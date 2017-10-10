package modele;

import java.util.Observable;

import vue.FicheLieu;
import vue.FicheModule;

/**
 * Permet de gérer un lieu de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class Lieu extends Module {

	/** Type de module. */
	public static final String TYPE = "lieu";

	/** Nom du lieu. */
	private String nom;

	/** Description du lieu. */
	private String description;


	/**
	 * Constructeur par défaut, uniquement utilisé pour la sérialisation.
	 */
	public Lieu() {
		this.nom = "";
		this.description = "";
	}

	/**
	 * Constructeur à partir d'une fiche associée.
	 * @param fiche         Fiche associée au module
	 * @param projetAssocie Projet à lier au module
	 */
	public Lieu(FicheModule fiche, Projet projetAssocie) {
		this();

		// Enregistrement du projet parent
		super.setProjetParent(projetAssocie);

		// Enregistrement de la fiche associée
		super.setFiche(fiche);
	}

	/**
	 * Initialiser un lieu à partir d'un nom et d'une description.
	 * @param nom le nom du lieu
	 * @param description la description du lieu
	 */
	public Lieu(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}


	/**
	 * Obtenir le nom du lieu.
	 * @return le nom du lieu
	 */
	@Override
	public String getNom() {
		return this.nom;
	}

	/**
	 * Obtenir la description du lieu.
	 * @return la descritption du lieu
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Permet de récupérer le type d'un module.
	 * @return le type du module
	 */
	@Override
	public String getType() {
		return Lieu.TYPE;
	}

	/**
	 * Permet de modifier le nom.
	 * @param nom le nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
		this.setChanged();
	}

	/**
	 * Permet de modifier la description.
	 * @param description la nouvelle description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Mettre à jour les informations concernant les lieux et personnages présents.
	 * @param observable    Elément modifié (Projet)
	 * @param arguments     Notification de modification (Notification)
	 */
	public void update(Observable observable, Object arguments) {

	}

	/**
	 * Permet de cloner un lieu.
	 * @return Un clone de lieu 
	 */
	@Override
	public Lieu clone() {
		Lieu clone = new Lieu();
		clone.nom = new String(this.nom);
		clone.description = new String(this.description);
		return clone;
	}

	/**
	 * Permet de vérifier l'égalité de deux objets Lieu entre eux.
	 * @param ob L'objet à comparer
	 * @return true si les deux objets sont logiquement identiques
	 */
	@Override
	public boolean equals(Object ob) {
		return ob instanceof Lieu
				&& ((Lieu) ob).nom == this.nom
				&& ((Lieu) ob).description == this.description;
	}
	

	/**
	 * Permet de générer la fiche associée au module,
	 * si elle est inexistante.
	 * @return La fiche module nouvellement créée,
	 *         associée au module courant
	 */
	@Override
	protected FicheModule genererVue() {
		return new FicheLieu(this);
	}
}
