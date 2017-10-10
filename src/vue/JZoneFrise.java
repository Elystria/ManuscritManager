package vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JPanel;

import modele.Date;
import modele.Evenement;
import modele.Frise;

/**
 * Définit une zone scrollable où peut-etre dessinée une frise chronologique.
 * @author      G14
 * @version     1.0
 */
class JZoneFrise extends JPanel {

	/******************** Constantes ****************************************************/
    /** Dimensions préférées. */
    static final Dimension PREFERRED_SIZE = new Dimension(500, 500);

    /** Nombre d'événements séparants deux timelines. */
    static final int NB_EVENEMENTS_ENTRE_2_TIMELINE = 5;

    /** Pas vertical (en pixels) entre deux événements. */
    static final int PAS_ENTRE_EVENEMENTS = 20;

    /** Marge (en pixels) à adopter sur les bords de la frise. */
    static final int MARGE = 30;

    /** Zone (en pixels) à réserver pour le texte descriptif d'un événement. */
    static final int LONGUEUR_ZONE_TEXTE = 150;

    /**
     * Taille de la zone séparant la ligne représentant un événement du
     * texte descriptif.
     */
    static final int SEPARATEUR_LIGNE_TEXTE = 15;

    /** Taille verticale (en pixels) d'un événement. */
    static final int HAUTEUR_EVENEMENT = 10;

    /** Taille verticale (en pixels) d'une timeline. */
    static final int HAUTEUR_TIMELINE = 60;

    /** Durée minimale d'un événement étant une période (en pixels). */
    static final int DUREE_MINIMALE = 5;


	/******************** Variables ****************************************************/
    /** Frise à dessiner. */
    private Frise friseLiee;

    /** Dates minimale et maximale de la frise. */
    private Date dateMin, dateMax;

    /** Echelle d'affichage. */
    private double scale;


	/******************** Constructeurs ************************************************/
    /**
     * Constructeur de la zone.
     * @param friseLiee Le frise à dessiner
     */
    JZoneFrise(Frise friseLiee) {
        // Appel du super constructeur
        super();

        // Vérification du cas d'erreur
        if (friseLiee == null) {
            throw new NullPointerException("Frise non existante !");
        }

        // Enregistrement de la frise liée
        this.friseLiee = friseLiee;

        // Fixation d'une taille minimale
        super.setPreferredSize(JZoneFrise.PREFERRED_SIZE);
    }


	/******************** Getters et setters *******************************************/
    /**
     * Permet de modifier la frise à dessiner.
     * @param friseLiee La nouvelle frise à dessiner
     */
    void setFriseLiee(Frise friseLiee) {
        // Vérification du cas d'erreur
        if (friseLiee == null) {
            throw new NullPointerException("Frise non existante !");
        }

        // Enregistrement
        this.friseLiee = friseLiee;
    }


	/******************** Gestion de l'affichage ***************************************/
    /**
     * Permet de calculer et enregistrer l'échelle de la timeline.
     */
    private void calculerEchelleTimeline() {
        // Récupération de la date minimale
        this.dateMin = this.friseLiee.getPlusAncienneDate();

        // Récupération de la date maximale
        this.dateMax = this.friseLiee.getPlusRecenteDate();

        // Calcul de la taille de la zone réservée à la frise
        double tailleZoneDessinable =
                this.getSize().getWidth()
                - JZoneFrise.MARGE * 2
                - JZoneFrise.LONGUEUR_ZONE_TEXTE;

        // Calcul de l'échelle
        int delta_date = this.dateMax.toInteger() - this.dateMin.toInteger();
        this.scale = tailleZoneDessinable / (delta_date + 1);
    }

    /**
     * Permet de dessiner la frise.
     * @param elementGraphique Elément graphique dans lequel faire le dessin
     */
    private void dessinerFrise(Graphics elementGraphique) {
        // Calcul de l'échelle de la timeline
        this.calculerEchelleTimeline();

        // Parcourt des événements à afficher, avec affichage de la timeline
        // tous les NB_EVENEMENTS_ENTRE_2_TIMELINE éléments
        Iterator<Evenement> iterateurEvenement = this.friseLiee.getIterateur();
        int posX = JZoneFrise.MARGE;
        int posY = JZoneFrise.MARGE;
        int cptEvenements = -1;

        while (iterateurEvenement.hasNext()) {
            // Augmentation du nombre d'événements
            cptEvenements++;

            // Affichage de la timeline si nécessaire
            if (cptEvenements % JZoneFrise.NB_EVENEMENTS_ENTRE_2_TIMELINE == 0) {
                this.dessinerTimeline(
                        elementGraphique,
                        posX,
                        posY + JZoneFrise.PAS_ENTRE_EVENEMENTS);

                posY += JZoneFrise.HAUTEUR_TIMELINE + JZoneFrise.PAS_ENTRE_EVENEMENTS * 2;
            }

            // Affichage de l'événement
            this.dessinerEvenement(
                    iterateurEvenement.next(),
                    elementGraphique,
                    posX,
                    posY
            );

            // Passage à la position suivante
            posY += JZoneFrise.PAS_ENTRE_EVENEMENTS;
        }
    }

    /**
     * Permet de dessiner une timeline.
     * @param elementGraphique      Elément graphique dans lequel faire le dessin
     * @param posX                  Position x de dessin
     * @param posY                  Position y de dessin
     */
    private void dessinerTimeline(Graphics elementGraphique, int posX, int posY) {
        /************************ Calculs divers ***************************************/
        // Enregistrement de la date de début
        double dateDebut = this.dateMin.toInteger();

        // Enregistrement de la date de fin
        double dateFin = this.dateMax.toInteger();

        // Calcul du pas entre les événements

        // Calculer la taille de la timeline
        int longueurTimeline = (int) ((dateFin - dateDebut) * this.scale);

        // Calcul des positions verticales de dessin :
        //      - position de dessin de la barre verticale de placement de date
        int posYbarre = posY + JZoneFrise.HAUTEUR_TIMELINE - JZoneFrise.HAUTEUR_EVENEMENT;
        //      - position de dessin du texte de date
        int posYdate = posYbarre - JZoneFrise.HAUTEUR_EVENEMENT;
        //      - position de dessin de la timeline
        int posYtimeline = posYbarre + JZoneFrise.HAUTEUR_EVENEMENT / 2;


        /************************ Traçage de la timeline *******************************/
        // Récupération de l'élément de graphisme
        Graphics2D zoneDessin = (Graphics2D) elementGraphique;

        // Tracer la timeline
        zoneDessin.drawLine(
                posX,
                posYtimeline,
                posX + longueurTimeline,
                posYtimeline
        );

        /************************ Traçage des dates ************************************/
        // Sauvegarde de la police originale
        Font policeOriginale = zoneDessin.getFont();

        // Calculer une rotation de la police de dessin
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(- Math.PI / 4);
        Font policeTournee = policeOriginale.deriveFont(rotation);

        // Valider cette police
        zoneDessin.setFont(policeTournee);

        // Ecrire les dates
        SortedSet datesTriees = this.friseLiee.getDatesTriees();
        Iterator<Date> iterateur = datesTriees.iterator();
        int posXdatePrecedente = 0;

        while (iterateur.hasNext()) {
            // Récupération de la date
            Date dateActuelle = iterateur.next();

            // Calcul de la position de traçage
            double dateActuelleDble = dateActuelle.toInteger();
            int posXdateActuelle =
                    posX + (int) ((dateActuelleDble - dateDebut) * this.scale);

            // Dessiner la date si elle n'est pas trop proche de la précédente
            if (posXdateActuelle - posXdatePrecedente
                    >= JZoneFrise.PAS_ENTRE_EVENEMENTS) {
                // Dessin de la barre verticale
                zoneDessin.drawLine(
                        posXdateActuelle,
                        posYbarre,
                        posXdateActuelle,
                        posYbarre + JZoneFrise.HAUTEUR_EVENEMENT
                );

                // Dessin du texte
                zoneDessin.drawString(
                        dateActuelle.toString(),
                        posXdateActuelle,
                        posYdate
                );
            }

            // Passage à la date suivante
            posXdatePrecedente = posXdateActuelle;
        }

        // Remettre à zéro la police
        zoneDessin.setFont(policeOriginale);
    }

    /**
     * Permet de dessiner un événement.
     * @param evenementADessiner    Evénement à dessiner
     * @param elementGraphique      Elément graphique dans lequel faire le dessin
     * @param posX                  Position x de dessin
     * @param posY                  Position y de dessin
     */
    private void dessinerEvenement(Evenement evenementADessiner,
                                   Graphics elementGraphique, int posX, int posY) {

        // Enregistrement de la date de début
        double dateDebut = evenementADessiner.getDateDebut().toInteger();

        // Calcul de l'abcisse de départ du trait de l'événement
        double dateMinimale = this.dateMin.toInteger();
        int xDebutEvenement = posX + (int) ((dateDebut - dateMinimale) * this.scale);

        // Calculer la durée de l'événement :
        // s'il n'est pas ponctuel, sauvegarder la durée, sinon l'indiquer nulle
        int dureeEvenement = 0;

        if (evenementADessiner.getEstUnePeriode()) {
            // Enregistrement de la date de fin
            double dateFin = evenementADessiner.getDateFin().toInteger();

            // Calcul de la durée (en pixels) de l'événement
            dureeEvenement = (int) ((dateFin - dateDebut) * this.scale);
        }

        // Calculer la hauteur du trait :
        // si l'événement n'est pas ponctuel, la hauteur est nulle
        int hauteurTrait = 0;
        // s'il est ponctuel ou que sa durée est trop courte, la hauteur est
        // celle constante
        if (!evenementADessiner.getEstUnePeriode()
                || dureeEvenement < JZoneFrise.DUREE_MINIMALE) {

            hauteurTrait = JZoneFrise.HAUTEUR_EVENEMENT;
            dureeEvenement = 0;
        }

        // Calculer l'ordonnée de départ du trait
        int yDebutEvenement = posY;
        if (evenementADessiner.getEstUnePeriode()
                && dureeEvenement >= JZoneFrise.DUREE_MINIMALE) {

            yDebutEvenement -= JZoneFrise.HAUTEUR_EVENEMENT / 2;
        } else {
            yDebutEvenement -= JZoneFrise.HAUTEUR_EVENEMENT;
        }

        // Dessiner la ligne horizontale
        elementGraphique.drawLine(
                xDebutEvenement,
                yDebutEvenement,
                xDebutEvenement + dureeEvenement,
                yDebutEvenement + hauteurTrait
        );

        // Dessiner le nom de l'événement
        elementGraphique.drawString(
                evenementADessiner.getNom(),
                xDebutEvenement + dureeEvenement + JZoneFrise.SEPARATEUR_LIGNE_TEXTE,
                posY
        );
    }

    /**
     * Permet de générer l'affichage.
     * @param g L'élément graphique dans lequel faire le dessin
     */
    @Override
    public void paint(Graphics g) {
        // Dessiner la fenetre de base
        super.paint(g);

        // Dessiner la frise s'il y a des événements à afficher
        if (this.friseLiee.getIterateur().hasNext()) {
            dessinerFrise(g);
        }
    }
}
