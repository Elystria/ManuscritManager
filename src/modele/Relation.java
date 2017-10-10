package modele;

/**
 * Permet de modéliser une relation.
 *
 * @author      G14
 * @version     1.0
 */

public class Relation implements Cloneable {

	/** Le nom du personnage impliqué dans la relation. */
	private String nomPersoImplique;

	/** Le type de la relation. */
	private String type;


	/**
	 * Constructeur par défaut, utilisé pour la sérialisation.
	 */
	public Relation() {
		this.nomPersoImplique = "";
		this.type = "";
	}
	/**
	 * Permet de créer une relation.
	 * @param nomPersoImplique nom du personnage lié à la relation
	 * @param type             type de la relation
	 */
	public Relation(String nomPersoImplique, String type) {
		this.nomPersoImplique = nomPersoImplique;
		this.type = type;
	}

	/**
	 * Obtenir le type de la relation.
	 * @return le type de la relation
	 */
	public String getType() {
		return type;
	}

	/**
	 * Permet de récupérer le nom du personnage impliqué dans la relation.
	 * @return le nom du personnage impliqué dans la relation
	 */
	public String getNomPersoImplique() {
		return nomPersoImplique;
	}

	/**
	 * Modifier le type de la relation.
	 * @param type le nouveau type de la nouvelle relation
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Permet de modifier le nom du personnage impliqué dans la relation.
	 * @param nomPersoImplique le nouveau nom du personnage impliqué dans la relation
	 */
	public void setNomPersoImplique(String nomPersoImplique) {
		this.nomPersoImplique = nomPersoImplique;
	}

	/**
	 * Permet de cloner une relation.
	 * @return une relation identique
	 */
	@Override
	public Relation clone() {
		// Créer le clone
		Relation clone = new Relation();
		clone.nomPersoImplique = new String(this.nomPersoImplique);
		clone.type = new String(this.type);

		// Renvoyer le clone
		return clone;
	}
}
