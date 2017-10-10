package modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import modele.notification.Notification;
import modele.notification.TypeNotification;
import vue.FicheEvenement;
import vue.FicheModule;

/**
 * Permet de gérer les événements de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class Evenement extends Module implements Comparable<Evenement> {

	/** Type de module. */
	public static final String TYPE = "evenement";

	/** Date de début. */
	private Date dateDebut;

	/**
	 * Indique si l'événement est une période.
	 * Par défaut, un événement n'est pas une période. */
	private boolean estUnePeriode;

	/** Date de fin. */
	private Date dateFin;

	/** Nom de l'évènement. */
	private String nom;

	/** Description de l'évènement. */
	private String description;

	/** Personnages absents. */
	private Set<String> listePersoAbsents;

	/** Personnages présents. */
	private Set<String> listePersoPresents;

	/** Lieux touchés par l'événement. */
	private Set<String> listeLieuxTouches;

	/** Lieux non touchés par l'événements. */
	private Set<String> listeLieuxNonTouches;


	/**
	 * Constructeur sans paramètre, utilisé seulement pour la sérialisation.
	 */
	public Evenement() {
		this.nom = "";
		this.dateDebut = new Date();
		this.dateFin = new Date();
		this.estUnePeriode = false;
		this.description = "";
		this.listePersoPresents = new HashSet<String>();
		this.listePersoAbsents = new HashSet<String>();
		this.listeLieuxTouches = new HashSet<String>();
		this.listeLieuxNonTouches = new HashSet<String>();
	}

	/**
	 * Constructeur à partir d'une fiche associée.
	 * @param fiche         Fiche associée au module
	 * @param projetAssocie Projet à lier au module
	 */
	public Evenement(FicheModule fiche, Projet projetAssocie) {
		this();

		// Enregistrement du projet parent
		super.setProjetParent(projetAssocie);

		// Enregistrement de la fiche associée
		super.setFiche(fiche);

		/* Enregistrement du fait que, par défaut,
		l'événement n'est pas une période */
		this.estUnePeriode = false;

		// Création de la liste de personnages absent
		for (String nomPerso : getProjetParent().getListeNom(Personnage.TYPE)) {
			this.listePersoAbsents.add(nomPerso);
		}

		// Création de la liste des lieux non touchés
		for (String nomLieu : getProjetParent().getListeNom(Lieu.TYPE)) {
			this.listeLieuxNonTouches.add(nomLieu);
		}
	}

	/**
	 * Conctructeur de la classe Evenement.
	 * @param dateDebut La date du début de l'évènement
	 * @param dateFin La date de fin de l'évènement
	 * @param nom Nom de l'évènement
	 * @param description Description de l'évènement
	 */
	public Evenement(Date dateDebut, Date dateFin, String nom,
			String description, Projet projetAssocie) {

		setProjetParent(projetAssocie);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nom = nom;
		this.description = description;
		this.listePersoAbsents = new HashSet<String>();
		for (String nomPerso : getProjetParent().getListeNom("personnage")) {
			this.listePersoAbsents.add(nomPerso);
		}
		this.listePersoPresents = new HashSet<String>();
	}


	/**
	 * Donne la date de fin de l'évènement.
	 * @return Calendar La date de fin
	 */
	public Date getDateFin() {
		return this.dateFin;
	}

	/**
	 * Donne la date de début de l'évènement.
	 * @return Calendar La date de début
	 */
	public Date getDateDebut() {
		return this.dateDebut;
	}

	/**
	 * Donne le nom de l'évènement.
	 * @return String Le nom de l'évènement
	 */
	@Override
	public String getNom() {
		return this.nom;
	}

	/**
	 * Donne la description de l'évènement.
	 * @return String La description de l'évènement
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Permet de récupérer la liste des personnages absents de l'évènement.
	 * @return List<module> liste des personnages absents de l'évènement
	 */
	public Set<String> getListePersoAbsents() {
		return this.listePersoAbsents;
	}

	/** Permet de récupérer la liste des personnages présents lors de l'évènement.
	 * @return List<module> liste des personnages présents lors de l'évènement
	 */
	public Set<String> getListePersoPresents() {
		return this.listePersoPresents;
	}

	/**
	 * Modifie la date de fin de l'évènement.
	 * @param dateFin la nouvelle date de fin
	 * @exception IllegalArgumentException Si la date de fin est inférieure
	 *                                     à la date de début.
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
		this.setChanged();
	}

	/**
	 * Modifie la date de début de l'évènement.
	 * @param dateDebut la nouvelle date de début
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
		this.setChanged();
	}

	/**
	 * Modifie la description de l'évènement.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Modifie le nom de l'évènement.
	 */
	public void setNom(String nom) {
		this.nom = nom;
		this.setChanged();
	}

	/**
	 * Permet de récupérer le type d'un module.
	 * @return le type du module
	 */
	@Override
	public String getType() {
		return Evenement.TYPE;
	}

	/** 
	 * Permet de récupérer l'indicateur de période de l'évènement.
	 * @return true si l'évènement est une période
	 */
	public boolean getEstUnePeriode() {
		return this.estUnePeriode;
	}
	
	/**
	 * Récupèrer la position d'un personnage dans la
	 * liste des personnages présents à l'évènement.
	 * @param nom2 le nom du personnage recherché
	 * @return la position du personnage dans la liste
	 */
	public int getIndice(String nom2) {
		int compteur = 0;
		for(String unNom : this.listePersoPresents){
			if (unNom.equals(nom2)){
				return compteur;
			}
			compteur++;
		}
		return -1;
	}

	/**
	 * Permet de récupérer la liste des lieux non touchés par l'évènement.
	 * @return liste des lieux non touchés par l'évènement
	 */
	public Set<String> getListeLieuxNonTouches() {
		return this.listeLieuxNonTouches;
	}

	/**
	 * Permet de récupérer la liste des lieux touchés par l'évènement.
	 * @return liste des lieux touchés par l'évènement
	 */
	public Set<String> getListeLieuxTouches() {
		return this.listeLieuxTouches;
	}
	/**
	 * Permet d'ajouter un personnage à la liste de personnages présents.
	 * @param nom du personnage
	 */
	public void setPersoPresent(String nom) {
		this.listePersoAbsents.remove(nom);
		this.listePersoPresents.add(nom);
	}

	/**
	 * Permet d'ajouter un personnage à la liste de personnages absents.
	 * @param nom du personnage
	 */
	public void setPersoAbsent(String nom) {
		this.listePersoAbsents.add(nom);
		this.listePersoPresents.remove(nom);
	}

	/**
	 * Permet d'ajouter un lieu à la liste des lieux touchés.
	 * @param nom nom du lieu
	 */
	public void setLieuTouche(String nom) {
		this.listeLieuxNonTouches.remove(nom);
		this.listeLieuxTouches.add(nom);
	}

	/**
	 * Permet d'ajouter un lieu à la liste des lieux non touchés.
	 * @param nom nom du lieu
	 */
	public void setLieuNonTouche(String nom) {
		this.listeLieuxNonTouches.add(nom);
		this.listeLieuxTouches.remove(nom);
	}

	/**
	 * Permet des définir si l'évènement est une période.
	 * @param estPeriode, vrai si l'évènement est une période
	 */
	public void setEstUnePeriode(boolean estPeriode) {
		this.setChanged();
		this.estUnePeriode = estPeriode;
	}
	
	/**
	 * Permet de modifier la liste des personnages absents.
	 * @param listePersoAbsents Nouvel ensemble de personnages absents
	 */
	public void setListePersoAbsents(Set<String> listePersoAbsents) {
		this.listePersoAbsents = listePersoAbsents;
	}

	/**
	 * Permet de modifier la liste des personnages présents.
	 * @param listePersoPresents Nouvel ensemble de personnages présents
	 */
	public void setListePersoPresents(Set<String> listePersoPresents) {
		this.listePersoPresents = listePersoPresents;
	}

	/**
	 * Permet de changer la liste des lieux touchés.
	 * @param listeLieuxTouches nouvelle liste des lieux touchés par l'événement
	 */
	public void setListeLieuxTouches(Set<String> listeLieuxTouches) {
		this.listeLieuxTouches = listeLieuxTouches;
	}

	/**
	 * Permet de changer la liste des lieux non touchés par l'événement.
	 * @param listeLieuxNonTouches nouvelle liste des lieux non touchés par l'événement
	 */
	public void setListeLieuxNonTouches(Set<String> listeLieuxNonTouches) {
		this.listeLieuxNonTouches = listeLieuxNonTouches;
	}

	
	/**
	 * Mettre à jour les informations concernant les lieux et personnages présents.
	 * @param observable    Elément modifié (Projet)
	 * @param arguments     Notification de modification (Notification)
	 */
	public void update(Observable observable, Object arguments) {
		// Si la notification est valide, la prendre en compte
		if (observable instanceof Projet && arguments instanceof Notification) {
			// Récupération de la notification
			Notification notif = (Notification) arguments;

			// Traitement de la notification :
			//      - Ajout d'un module
			if (notif.getAction() == TypeNotification.AJOUT) {
				// Si le module est un lieu, l'ajouter aux lieux possibles
				if (notif.getNouvelEtat() instanceof Lieu) {
					this.listeLieuxNonTouches.add(notif.getNouvelEtat().getNom());
				}

				// Si le module est un personnage, l'ajouter aux personnages absents
				if (notif.getNouvelEtat() instanceof Personnage) {
					this.listePersoAbsents.add(notif.getNouvelEtat().getNom());
				}
			}

			//      - Modification d'un module
			if (notif.getAction() == TypeNotification.MODIFICATION) {
				// Si le module est un lieu, corriger son nom
				if (notif.getNouvelEtat() instanceof Lieu) {
					String nomPrecedent = notif.getEtatPrecedent().getNom();
					String nouveauNom = notif.getNouvelEtat().getNom();

					// ... dans les lieux touchés par l'événement
					if (this.listeLieuxTouches.contains(nomPrecedent)) {
						this.listeLieuxTouches.remove(nomPrecedent);
						this.listeLieuxTouches.add(nouveauNom);

						// ... ou dans les lieux possibles
					} else {
						this.listeLieuxNonTouches.remove(nomPrecedent);
						this.listeLieuxNonTouches.add(nouveauNom);
					}
				}

				// Si le module est un personnage, corriger son nom
				if (notif.getNouvelEtat() instanceof Personnage) {
					String nomPrecedent = notif.getEtatPrecedent().getNom();
					String nouveauNom = notif.getNouvelEtat().getNom();

					// ... dans les personnages présents
					if (this.listePersoPresents.contains(nomPrecedent)) {
						this.listePersoPresents.remove(nomPrecedent);
						this.listePersoPresents.add(nouveauNom);

						// ... ou dans les personnages absents
					} else {
						this.listePersoAbsents.remove(nomPrecedent);
						this.listePersoAbsents.add(nouveauNom);
					}
				}
			}

			//      - Suppression d'un module
			if (notif.getAction() == TypeNotification.SUPPRESSION) {
				// Si le module est un lieu, le supprimer des listes
				if (notif.getEtatPrecedent() instanceof Lieu) {
					String nomLieuSupprime = notif.getEtatPrecedent().getNom();

					this.listeLieuxTouches.remove(nomLieuSupprime);
					this.listeLieuxNonTouches.remove(nomLieuSupprime);
				}

				// Si le module est un personnage, le supprimer des listes
				if (notif.getEtatPrecedent() instanceof Personnage) {
					String nomPersoSupprime = notif.getEtatPrecedent().getNom();

					this.listePersoAbsents.remove(nomPersoSupprime);
					this.listePersoPresents.remove(nomPersoSupprime);
				}
			}

			// Notifier la fiche d'un changement
			this.getFiche().update(this, arguments);
		}
	}

	/**
	 * Permet de comparer deux objets evenements entre eux.
	 *
	 * @param ob L'objet à comparer
	 * @return true si les deux objets sont logiquement identiques
	 */
	@Override
	public boolean equals(Object ob) {
		boolean conditionEgalite = true;

		// les types d'évènement sont les mêmes
		if (ob instanceof Evenement
				&& ((Evenement) ob).getEstUnePeriode() == this.estUnePeriode){
			// On a une date de fin
			if (this.estUnePeriode) {
				conditionEgalite = (((Evenement) ob).getDateFin().equals(this.dateFin));
			}
			//Date de début
			conditionEgalite = (conditionEgalite
					&& ((Evenement) ob).getDateDebut().equals(this.dateDebut));
			//Nom
			conditionEgalite = (conditionEgalite 
					&& ((Evenement) ob).getNom().equals(this.nom));
			//Description
			conditionEgalite = (conditionEgalite 
					&& ((Evenement) ob).getDescription().equals(this.description));
			//Liste des personnages absents
			conditionEgalite = (conditionEgalite 
					&& ((Evenement) ob).getListePersoPresents().equals(this.listePersoPresents));
			//Liste des personnages présents
			conditionEgalite = (conditionEgalite 
					&& ((Evenement) ob).getListePersoAbsents().equals(this.listePersoAbsents));
		}
		// les types d'évènements sont pas les mêmes
		else {
			conditionEgalite = false;
		}
		return ob instanceof Evenement
				&& conditionEgalite;
	}

	/**
	 * Permet de cloner un évènement.
	 * @return Un clone de l'évènement 
	 */
	@Override
	public Evenement clone() {
		Evenement clone = new Evenement();
		clone.dateDebut = this.dateDebut.clone();
		clone.estUnePeriode = this.estUnePeriode;
		clone.dateFin = this.dateFin.clone();
		clone.nom = new String(this.nom);
		clone.description = new String(this.description);
		clone.listePersoAbsents = new HashSet<String>(this.listePersoAbsents);
		clone.listePersoPresents = new HashSet<String>(this.listePersoPresents);
		return clone;
	}

	/**
	 * Permet de pouvoir comparer deux evenements.
	 * @param autreEvenement Evenement à comparer
	 * @return une valeur négative, nulle ou positive, si l'Evenement courant est
	 *         inférieur, égal ou supérieur à l'évènement à comparer
	 */
	@Override
	public int compareTo(Evenement autreEvenement) {
		// Comparer les dates de début
		int comparaisonDates = this.dateDebut.compareTo(autreEvenement.getDateDebut());

		// Si les dates de début sont identiques
		if (comparaisonDates == 0) {
			// Si les deux événements sont ponctuels, comparer aussi les dates de fin
			if (this.estUnePeriode && autreEvenement.estUnePeriode) {
				comparaisonDates = this.dateFin.compareTo(autreEvenement.getDateFin());

				// Sinon, prendre la date de fin de celui qui n'est pas une période
			} else {
				if (this.estUnePeriode) {
					comparaisonDates = this.getDateFin().toInteger();
				} else {
					comparaisonDates = autreEvenement.getDateFin().toInteger();
				}
			}
		}

		// Renvoyer le résultat
		return comparaisonDates;
	}

	/**
	 * Permet de générer la fiche associée au module,
	 * si elle est inexistante.
	 * @return La fiche module nouvellement créée,
	 *         associée au module courant
	 */
	@Override
	protected FicheModule genererVue() {
		return new FicheEvenement(this);
	}	
}
