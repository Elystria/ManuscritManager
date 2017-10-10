package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import modele.notification.Notification;
import modele.notification.TypeNotification;
import vue.FicheModule;
import vue.FichePersonnage;

/**
 * Permet de gérer un personnage de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class Personnage extends Module {

	/** Type de module. */
	public static final String TYPE = "personnage";

	/** Le nom du personnage. */
	private String nomFamille;

	/** Le prénom du personnage. */
	private String prenom;

	/** La description du personnage. */
	private String description;

	/** La liste des personnages pouvant faire une relation. */
	private List<String> relationsPotentielles;

	/** La liste des relations. */
	private List<Relation> relations;

	/**
	 * Constructeur par défaut, uniquement utilisé pour la sérialisation.
	 */
	public Personnage() {
		this.nomFamille = "";
		this.prenom = "";
		this.description = "";
		this.relationsPotentielles = new ArrayList<String>();
		this.relations = new ArrayList<Relation>();
	}

	/**
	 * Constructeur à partir d'une fiche associée.
	 * @param fiche         Fiche associée au module
	 * @param projetAssocie Projet à lier au module
	 */
	public Personnage(FicheModule fiche, Projet projetAssocie) {
		this();

		// Enregistrement du projet parent
		super.setProjetParent(projetAssocie);

		// Enregistrement de la fiche associée
		super.setFiche(fiche);

		// Récupération de la liste des personnages
		this.relationsPotentielles = getProjetParent().getListeNom("personnage");
	}

	/**
	 * Génerer un personnage à partir de son nom,son prénom et sa description.
	 * @param nom         le nom du personnage
	 * @param prenom      le prénom du personnage
	 * @param description la description du personnage
	 */
	public Personnage(String nom, String prenom, String description, Projet projetAssocie, FicheModule fiche) {
		this(fiche, projetAssocie);
		setProjetParent(projetAssocie);
		assert (nom != null && prenom != null && description != null);
		this.nomFamille = nom;
		this.prenom = prenom;
		this.description = description;
		this.relationsPotentielles = getProjetParent().getListeNom("personnage");
		this.relations = new ArrayList<Relation>();
	}


	/**
	 * Obtenir le prénom d'un personnage.
	 * @return le prénom du personnage
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Obtenir le nom d'un personnage.
	 * @return le nom du personnage
	 */
	public String getNomFamille() {
		return this.nomFamille;
	}

	/**
	 * Obtenir le nom d'affichage d'un personnage.
	 * @return le nom (pour affichage) du personnage
	 */
	@Override
	public String getNom() {
		return this.prenom + " " + this.nomFamille;
	}

	/**
	 * Obtenir la description du personnage.
	 * @return la description du personnage
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Permet de récupérer la liste des relations potentielles.
	 * @return la liste des relations potentielles
	 */
	public List<String> getRelationsPotentielles() {
		return relationsPotentielles;
	}

	/**
	 * Permet de récupérer la liste des relations entre ce personnage et un autre.
	 * @return la liste des relations entre ce personnage et un autre
	 */
	public List<Relation> getRelations() {
		return relations;
	}
	
	/**
	 * Permet de récupérer le type d'un module.
	 * @return le type du module
	 */
	@Override
	public String getType() {
		return Personnage.TYPE;
	}

	/**
	 * Modifier le prenom du personnage.
	 * @param prenom le nouveau prénom
	 */
	public void setPrenom(String prenom) {
		assert (prenom != null);
		this.prenom = prenom;
		this.setChanged();
	}

	/**
	 * Modifier le nom de famille du personnage.
	 * @param nom le nouveau nom
	 */
	public void setNomFamille(String nom) {
		assert (nom != null);
		this.nomFamille = nom;
		this.setChanged();
	}

	/**
	 * Modifier la description du personnage.
	 * @param description la nouvelle description
	 */
	public void setDescription(String description) {
		assert (description != null);
		this.description = description;
	}

	
	/**
	 * Permet de changer la liste des relations potentielles.
	 * @param relationsPotentielles la nouvelle liste de relations potentielles
	 */
	public void setRelationsPotentielles(List<String> relationsPotentielles) {
		this.relationsPotentielles = relationsPotentielles;
	}

	/**
	 * Permet de changer la liste des relations entre ce personnage et un autre.
	 * @param relations la nouvelle liste des relations entre ce personnage et un autre
	 */
	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	/**
	 * Permet d'ajouter une relation.
	 * @param relationAAjouter la nouvelle relation
	 */
	public void ajouterRelation(Relation relationAAjouter) {
		this.relations.add(relationAAjouter);
	}
	

	/**
	 * Mettre à jour les informations concernant les lieux et personnages présents.
	 * @param observable    Elément modifié (Projet)
	 * @param arguments     Notification de modification (Notification)
	 */
	public void update(Observable observable, Object arguments) {
		// Si la notification est recevable, la traiter
		if (observable instanceof Projet && arguments instanceof Notification) {
			// Extraction de la notification
			Notification notif = (Notification) arguments;

			// S'il s'agit d'un ajout de module
			if (notif.getAction() == TypeNotification.AJOUT) {
				// Dans le cas d'un ajout de personnage : l'ajouter aux relations potentielles
				if (notif.getNouvelEtat() instanceof Personnage) {
					// L'ajouter aux relations potentielles
					this.relationsPotentielles.add(notif.getNouvelEtat().getNom());
				}
			}

			// S'il s'agit d'une suppression de module
			if (notif.getAction() == TypeNotification.SUPPRESSION) {
				// Dans le cas d'une suppression de personnage
				if (notif.getEtatPrecedent() instanceof Personnage) {
					String nom = notif.getEtatPrecedent().getNom();

					// Le retirer des relations potentielles
					this.relationsPotentielles.remove(nom);

					// Le retirer des relations existantes
					Iterator<Relation> it = this.relations.iterator();
					while (it.hasNext()) {
						Relation relation = it.next();

						if (nom.equals(relation.getNomPersoImplique())) {
							it.remove();
						}
					}
				}
			}

			// S'il s'agit d'une modification de module
			if (notif.getAction() == TypeNotification.MODIFICATION) {
				// Dans le cas d'une modification de personnage
				if (notif.getEtatPrecedent() instanceof Personnage) {
					String ancienNom = notif.getEtatPrecedent().getNom();
					String nouveauNom = notif.getNouvelEtat().getNom();

					// Modifier les relations potentielles
					this.relationsPotentielles.remove(ancienNom);
					this.relationsPotentielles.add(nouveauNom);

					// Modifier les relations existantes
					Iterator<Relation> it = this.relations.iterator();
					while (it.hasNext()) {
						Relation relation = it.next();

						if (ancienNom.equals(relation.getNomPersoImplique())) {
							relation.setNomPersoImplique(nouveauNom);
						}
					}
				}
			}

			// Retirer le personnage courant de la liste des relations potentielles
			this.relationsPotentielles.remove(this.getNom());

			// Notifier la fiche d'un changement
			this.getFiche().update(this, arguments);
		}
	}
	
	/**
	 * Permet de cloner un personnage.
	 * @return Un clone de personnage 
	 */
	@Override
	public Personnage clone() {
		Personnage clone = new Personnage();
		clone.nomFamille = new String(this.nomFamille);
		clone.prenom = new String(this.prenom);
		clone.description = new String(this.description);
		clone.relationsPotentielles = new ArrayList<String>(this.relationsPotentielles);
		clone.relations = new ArrayList<Relation>(this.relations);
		return clone;
	}

	/**
	 * Permet de comparer deux objets personnages entre eux.
	 *
	 * @param objet L'objet à comparer
	 * @return true si les deux objets sont logiquement identiques
	 */
	@Override
	public boolean equals(Object objet) {
		boolean conditionEgalite = true;

		// les types sont les mêmes
		if (objet instanceof Personnage) {
			// même prénom
			String prenomObjet = ((Personnage) objet).getPrenom();
			conditionEgalite = this.getPrenom().equals(prenomObjet);

			// même nom
			String nomFamilleObjet = ((Personnage) objet).getNomFamille();
			conditionEgalite = (conditionEgalite && this.getNomFamille().equals(nomFamilleObjet));

			// même description
			String descriptionObjet = ((Personnage) objet).getDescription();
			conditionEgalite = (conditionEgalite && this.getDescription().equals(descriptionObjet));

			// même liste de relations potentielles
			List<String> relationsPotentiellesObjet = ((Personnage) objet).relationsPotentielles;
			conditionEgalite = conditionEgalite
					&& this.relationsPotentielles.equals(relationsPotentiellesObjet);

			// meme liste de relations
			List<Relation> relationsObjet = ((Personnage) objet).relations;
			conditionEgalite = conditionEgalite && this.relations.equals(relationsObjet);

		} else {
			conditionEgalite = false;
		}
		return  conditionEgalite;
	}

	/**
	 * Permet de générer la fiche associée au module,
	 * si elle est inexistante.
	 *
	 * @return La fiche module nouvellement créée,
	 * associée au module courant
	 */
	@Override
	protected FicheModule genererVue() {
		return new FichePersonnage(this);
	}
}
