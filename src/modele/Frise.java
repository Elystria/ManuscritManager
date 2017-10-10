package modele;

import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.SortedSet;
import java.util.TreeSet;

import modele.notification.Notification;
import modele.notification.TypeNotification;
import vue.FicheFrise;
import vue.FicheModule;

/**
 * Permet de générer une frise en rapport avec l'histoire
 * 
 * @author G14
 * @version 1.0
 * 
 */
public class Frise extends Module {

	/******************** Attributs ****************************************************/
	/** Type de module */
	public static final String TYPE = "frise";

	/** Nom de la frise*/
	private String nom;

	/** Les événements de la frise. */
	private SortedSet<Evenement> evenementsDansFrise;



	/******************** Constructeurs ************************************************/
	/**
	 * Constructeur sans paramètres, utilisable pour la sérialisation.
	 */
	public Frise () {
		this.nom = "";
		this.evenementsDansFrise = new TreeSet<Evenement>();
	}

	/**
	 * Constructeur à partir d'une fiche associée.
	 * @param fiche         Fiche associée au module
	 * @param projetAssocie Projet à lier au module
	 */
	public Frise(FicheModule fiche, Projet projetAssocie) {
		this();

		// Enregistrement du projet parent
		super.setProjetParent(projetAssocie);

		// Enregistrement de la fiche associée
		super.setFiche(fiche);
	}

	/**
	 * Constructeurs de frise à partir d'une liste d'évènements.
	 * @param nom                      Nom de la frise
	 * @param lesEvenementsNonTries    Les événements à afficher dans la frise
	 */
	public Frise(String nom, Collection<Module> lesEvenementsNonTries) {
		// Enregistrement du nom de la frise
		this.nom = nom;

		// Enregistrement des événements et des dates
		this.evenementsDansFrise = new TreeSet<Evenement>();
		for (Module module : lesEvenementsNonTries) {
			if (module instanceof Evenement) {
				this.evenementsDansFrise.add((Evenement) module);
			}
		}
	}



	/******************** Getters et setters *******************************************/
	/**
	 * Permet de récupérer le nom de la frise.
	 * @return Le nom de la frise
	 */
	public String getNom() {
		assert nom != null;
		return this.nom;
	}

	/**
	 * Permet de modifier le nom de la frise.
	 * @param nom   Nouveau nom de la frise.
	 */
	public void setNom(String nom) {
		// Vérification d'erreur
		if (nom == null) {
			throw new NullPointerException("Nom de la frise vide !");
		}

		// Enregistrement du nouveau nom
		this.nom = nom;
	}

	/**
	 * Permet de récupérer l'ensemble des événements présents dans la frise.
	 * @return les événements affichés dans la frise
	 */
	public SortedSet<Evenement> getEvenementsDansFrise() {
		return evenementsDansFrise;
	}

	/**
	 * Permet de changer l'ensemble des événements présents dans la frise.
	 * @param evenementsDansFrise les nouveaux événements dans la frise
	 */
	public void setEvenementsDansFrise(SortedSet<Evenement> evenementsDansFrise) {
		// Effectuer la modification seulement s'il s'agit d'une désérialisation,
		// sinon, les ajouts et suppressions d'événements doivent se faire
		if (this.evenementsDansFrise.size() == 0) {
			this.evenementsDansFrise = evenementsDansFrise;

			// Sinon, lever une erreur
		} else {
			throw new IllegalStateException(
					"Il est impossible de modifier l'ensemble entier des événements"
							+ "dans la frise lorsque celle-ci est déjà générée."
					);
		}
	}


	/******************** Getters et setters utiles pour les graphismes ****************/
	/**
	 * Permet de récupérer l'événement le plus ancien.
	 * @return l'événement le plus ancien de la frise
	 */
	public Evenement getPlusAncienEvenement() {
		return this.evenementsDansFrise.first();
	}

	/**
	 * Permet de récupérer l'événement le plus vieux.
	 * @return l'événement le plus vieux de la frise
	 */
	public Evenement getPlusRecentEvenement() {
		return this.evenementsDansFrise.last();
	}

	/**
	 * Permet de récupérer la date la plus ancienne de la frise.
	 * @return date la plus ancienne
	 */
	public Date getPlusAncienneDate() {
		return this.getPlusAncienEvenement().getDateDebut();
	}

	/**
	 * Permet de récupérer la date la plus récente de la frise.
	 * @return date la plus récente
	 */
	public Date getPlusRecenteDate() {
		// Date la plus vieille
		Date dateMax = new Date(0);

		// Parcours des événements à afficher dans la frise
		for (Evenement eve : this.evenementsDansFrise) {
			// Si la date de l'événement actuel est plus récente,
			// l'enregistrer comme nouvelle date maximale
			// (si l'événement n'est pas ponctuel)
			if (eve.getEstUnePeriode()
					&& dateMax.compareTo(eve.getDateFin()) < 0) {

				dateMax = eve.getDateFin();

			} else if (dateMax.compareTo(eve.getDateDebut()) < 0) {
				dateMax = eve.getDateDebut();
			}
		}

		// Retourner la date trouvée
		return dateMax;
	}

	/**
	 * Permet de récupérer les dates présentes dans la frise.
	 * @return un ensemble des dates présentes dans la frise
	 */
	public SortedSet<Date> getDatesTriees() {
		// Création de l'ensemble
		SortedSet<Date> ensemble = new TreeSet<Date>();

		// Ajout de toutes les dates
		for (Evenement eve : this.evenementsDansFrise) {
			// Ajout de la date de début
			ensemble.add(eve.getDateDebut());

			// Ajout de la date de fin si l'événement n'est pas ponctuel
			if (eve.getEstUnePeriode()) {
				ensemble.add(eve.getDateFin());
			}
		}

		// Retourner l'ensemble généré
		return ensemble;
	}



	/******************** Méthodes de gestion des événements de la frise ***************/
	/**
	 * Permet d'ajouter un événement à la frise.
	 * @param evenementAAjouter Evénement à rajouter à la frise
	 */
	public void addEvenement(Evenement evenementAAjouter) {
		this.evenementsDansFrise.add(evenementAAjouter);
	}

	/**
	 * Permet de retirer un événement de la frise.
	 * @param evenementAEnlever Evénement à enlever de la frise
	 */
	public void removeEvenement(Evenement evenementAEnlever) {
		this.evenementsDansFrise.remove(evenementAEnlever);
	}

	/**
	 * Permet de vérifier si la frise contient un événement ou pas.
	 * @param evenementAChercher Evénement pour lequel vérifier l'appartenance à la frise
	 * @return TRUE si l'événement est dans la frise
	 */
	public boolean containsEvenement(Evenement evenementAChercher) {
		return this.evenementsDansFrise.contains(evenementAChercher);
	}

	/**
	 * Permet de récupérer un itérateur de parcours de la frise.
	 * @return itérateur de parcours de frise
	 */
	public Iterator<Evenement> getIterateur() {
		return this.evenementsDansFrise.iterator();
	}



	/******************** Méthodes à implanter par héritage de Module  *****************/
	/**
	 * Permet de récupérer le type d'un module.
	 * @return le type du module
	 */
	public String getType() {
		return this.TYPE;
	}

	/**
	 * Permet de générer la fiche associée au module,
	 * si elle est inexistante.
	 *
	 * @return La fiche module nouvellement créée,
	 * associée au module courant
	 */
	protected FicheModule genererVue() {
		return new FicheFrise(this);
	}

	/**
	 * Permet de dupliquer une frise.
	 * @return une copie de la frise
	 */
	@Override
	public Frise clone() {
		Frise clone = new Frise();

		// Copie
		clone.evenementsDansFrise = new TreeSet<Evenement>(this.evenementsDansFrise);
		clone.nom = new String(this.nom);

		// Retour de la valeur calculée
		return clone;
	}


	/******************** Observation **************************************************/
	/**
	 * Mettre à jour les informations concernant les lieux et personnages présents.
	 * @param observable    Elément modifié (Projet)
	 * @param arguments     Notification de modification (Notification)
	 */
	@Override
	public void update(Observable observable, Object arguments) {
		// Si la notification est recevable
		if (observable instanceof Projet && arguments instanceof Notification) {
			// Extraire la notification
			Notification notif = (Notification) arguments;

			// Dans le cas d'un ajout d'événement, ne rien faire
			// Dans le cas d'une suppression d'événement, le supprimer de la frise
			if (notif.getAction() == TypeNotification.SUPPRESSION
					&& notif.getEtatPrecedent() instanceof Evenement) {

				this.evenementsDansFrise.remove(notif.getEtatPrecedent());
			}

			// Dans le cas d'une modification, reconstruire la liste
			if (notif.getAction() == TypeNotification.MODIFICATION
					&& notif.getNouvelEtat() instanceof Evenement) {

				SortedSet<Evenement> ancienEnsemble = this.evenementsDansFrise;
				this.evenementsDansFrise = new TreeSet<Evenement>();

				for (Evenement e : ancienEnsemble) {
					this.evenementsDansFrise.add(e);
				}
			}

			// Mettre à jour le graphique
			this.getFiche().update(this, arguments);
		}
	}
}
