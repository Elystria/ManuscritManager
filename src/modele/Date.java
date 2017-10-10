package modele;

/**
 * Permet de gérer les dates de l'histoire.
 *
 * @author      G14
 * @version     1.1
 */
public class Date implements Comparable<Date>, Cloneable {

	/** le nombre de mois par année. */
	private static final int NB_MOIS = 12;

	/** le nombre de jours par mois. */
	private static final int NB_JOURS = 31;
	/**
	 * Jour, si égal à 0, la date n'a pas de jour renseigné.
	 */
	private int jour;

	/**
	 * Mois, si égal à 0 la date n'a pas de mois renseigné.
	 */
	private int mois;

	/**
	 * Année.
	 */
	private int annee;

	
	/**
	 * Permet de créer une date à partir d'un jour, d'un mois et d'une année.
	 * Pré-conditions :
	 * - le jour est compris entre 0 et 31
	 * - le mois est compris entre 0 et NB_MOIS
	 *
	 * @param jour  Le jour
	 * @param mois  Le mois
	 * @param annee L'annee
	 */
	public Date(int jour, int mois, int annee) {
		assert 0 <= jour && jour <= NB_JOURS;
		assert 0 <= mois && mois <= NB_MOIS;

		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}

	/**
	 * Permet de créer une date à partir d'un mois et d'une année.
	 * Pré-condition :
	 * - le mois est compris entre 0 et NB_MOIS
	 *
	 * @param mois  Le mois
	 * @param annee L'annee
	 */
	public Date(int mois, int annee) {
		this(0, mois, annee);
	}

	/**
	 * Permet de créer une date à partir d'une année.
	 * @param annee L'annee
	 */
	public Date(int annee) {
		this(0, 0, annee);
	}

	/**
	 * Permet de générer une date vide.
	 */
	public Date() {
		this(0, 0, 0);
	}

	/**
	 * Modifier le jour.
	 * Pré-condition : le jour est compris entre 0 et 31.
	 *
	 * @param jour Le nouveau jour
	 */
	public void setJour(int jour) {
		assert 0 <= jour && jour <= NB_JOURS;

		this.jour = jour;
	}

	/**
	 * Modifier un mois.
	 * Pré-condition : le mois est compris entre 0 et 12.
	 *
	 * @param mois Le nouveau mois
	 */
	public void setMois(int mois) {
		assert 0 <= mois && mois <= NB_MOIS;

		this.mois = mois;
	}

	/**
	 * Modifier une année.
	 * @param annee La nouvelle année
	 */
	public void setAnnee(int annee) {
		this.annee = annee;
	}

	/**
	 * Permet de récupérer le jour associé à la date.
	 * @return jour associé
	 */
	public int getJour() {
		return this.jour;
	}

	/**
	 * Permet de récupérer le mois associé à la date.
	 * @return mois associé
	 */
	public int getMois() {
		return this.mois;
	}

	/**
	 * Permet de récupérer l'année associée à la date.
	 * @return année associée
	 */
	public int getAnnee() {
		return this.annee;
	}

	/**
	 * Modifier une date.
	 * Pré-conditions :
	 * - le jour est compris entre 0 et 31
	 * - le mois est compris entre 0 et 12
	 *
	 * @param jour  Le nouveau jour
	 * @param mois  Le nouveau mois
	 * @param annee La nouvelle année
	 */
	public void setDate(int jour, int mois, int annee) {
		assert 0 <= jour && jour <= NB_JOURS;
		assert 0 <= mois && mois <= NB_MOIS;

		this.setJour(jour);
		this.setMois(mois);
		this.setAnnee(annee);
	}

	/**
	 * Permet de cloner une date.
	 * @return Un clone de la date
	 */
	@Override
	public Date clone() {
		Date clone = new Date();
		clone.setAnnee(this.annee);
		clone.setMois(this.mois);
		clone.setJour(this.jour);
		return clone;
	}

	/**
	 * Permet de vérifier l'égalité de deux objets dates entre eux.
	 * @param ob L'objet à comparer
	 * @return true si les deux objets sont logiquement identiques
	 */
	@Override
	public boolean equals(Object ob) {
		return ob instanceof Date
				&& ((Date) ob).jour == this.jour
				&& ((Date) ob).mois == this.mois
				&& ((Date) ob).annee == this.annee;
	}


	/**
	 * Permet de pouvoir comparer deux dates.
	 * @param autreDate Date à comparer
	 * @return une valeur négative, nulle ou positive, si la date courante est
	 *         inférieur, égale ou supérieur à la date à comparer
	 */
	@Override
	public int compareTo(Date autreDate) {
		int dateCourante = this.toInteger();
		int dateComparee = autreDate.toInteger();

		// Renvoi de la valeur
		return dateCourante - dateComparee;
	}

	/**
	 * Permet de récupérer un entier représentant la date.
	 * @return l'entier représentant la date
	 */
	public int toInteger() {
		final int conversionAnnee = 10000;
		final int conversionMois = 100;
		return this.annee * conversionAnnee
				+ this.mois * conversionMois + this.jour;
	}

	/**
	 * Permet d'obtenir la représentation en chaine de caractère de la date.
	 * @return une chaine du type "19/05/1996", "05/1996" ou "1996"
	 */
	@Override
	public String toString() {
		// Séparateur de champ (année, mois, jour)
		String separateur = "/";

		// Date à renvoyer
		String date = String.valueOf(this.annee);

		// Si la date a un mois, le prendre en compte
		if (this.mois != 0) {
			date = this.mois + separateur + date;

			// Si la date a un jour, le compter
			if (this.jour != 0) {
				date = this.jour + separateur + date;
			}
		}

		// Retourner la chaine calculée
		return date;
	}
}
