package modele.notification;

/**
 * Permet de désigner un type de notification de modification d'un module.
 * @author  G14 - TOB PL - 2017
 * @version 1.0
 */
public enum TypeNotification {

    /**
     * Types de notification disponibles :
     *      - SUPPRESSION,
     *      - AJOUT,
     *      - MODIFICATION.
     */
    SUPPRESSION("suppression"),
    AJOUT("ajout"),
    MODIFICATION("modification");

    /** Type de notification. */
    private String type;


    /**
     * Permet de créer un type de notification.
     * @param type Le type de notification
     */
    private TypeNotification(String type) {
        this.type = type;
    }

    /**
     * Récupère le type de notification.
     * @return le type de notification
     */
    @Override
    public String toString() {
        return this.type;
    }
}
