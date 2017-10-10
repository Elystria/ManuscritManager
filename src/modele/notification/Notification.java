package modele.notification;

import modele.Module;

/**
 * Permet de créer une notification de modification d'un module.
 * @author  G14 - TOB PL - 2017
 * @version 1.0
 */
public class Notification {

    /** Etat précédent du module modifié. */
    private Module etatPrecedent;

    /** Nouvel état du module modifié. */
    private Module nouvelEtat;

    /** Action effectuée sur le module modifié. */
    private TypeNotification action;


    /**
     * Permet de créer une nouvelle notification.
     * @param etatPrecedent Etat précédant la modification du module
     * @param action        Action effectuée sur le module
     */
    public Notification(Module etatPrecedent,
                        TypeNotification action,
                        Module nouvelEtat) {
        this.etatPrecedent = etatPrecedent;
        this.action = action;
        this.nouvelEtat = nouvelEtat;
    }

    /**
     * Permet de récupérer l'action effectuée.
     * @return Action effectuée pour modifier le module
     */
    public TypeNotification getAction() {
        return this.action;
    }

    /**
     * Permet de récupérer un module représentant l'état précédent du module modifié.
     * @return l'état précédent du module modifié
     */
    public Module getEtatPrecedent() {
        return this.etatPrecedent;
    }

    /**
     * Permet de récupérer un module représentant l'état modifié du module.
     * @return nouvel état du module modifié
     */
    public Module getNouvelEtat() {
        return this.nouvelEtat;
    }
}
