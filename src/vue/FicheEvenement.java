package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import assets.SpringUtilities;
import controleur.evenement.ActionAjouterLieuTouche;
import controleur.evenement.ActionAjouterPersonnagePresent;
import controleur.evenement.ActionAnnulerEvenement;
import controleur.evenement.ActionPonctualiserEvenement;
import controleur.evenement.ActionSupprimerEvenement;
import controleur.evenement.ActionSupprimerLieuTouche;
import controleur.evenement.ActionSupprimerPersonnagePresent;
import controleur.evenement.ActionValiderEvenement;
import modele.Date;
import modele.Evenement;
import modele.Lieu;
import modele.Module;
import modele.Personnage;
import modele.Projet;
import modele.notification.Notification;
import modele.notification.TypeNotification;


/**
 * Permet de gérer la vue d'un événement de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class FicheEvenement extends FicheModule {

    /** Champ d'édition du nom. */
    private JTextField nom;

    /** Champs d'édition de la date de début. */
    private JComboBox jourDebut, moisDebut, anneeDebut;

    /** Case à cocher indiquant la necessité d'une date de fin. */
    private JCheckBox dateFinLabel;

    /** Champs d'édition de la date de fin. */
    private JComboBox jourFin, moisFin, anneeFin;

    /** Champ d'édition de la description. */
    private JTextArea description;

    /** Champ d'édition des personnages absents. */
    private JComboBox selectPersoAbsents;

    /** Zone d'affichage des personnages présents. */
    private JPanel panelPersoPresents;

    /** Panels d'affichage des personnages présents. */
    private List<JPanel> panelsPersoPresents;

    /** Noms des personnages présents à l'événement. */
    private List<String> nomsPersoPresents;

    /** Champ de sélection des lieux non touchés. */
    private JComboBox selectLieuxNonTouches;

    /** Zone d'affichage des lieux touchés. */
    private JPanel panelLieuxTouches;

    /** Panels d'affichage des lieux touchés. */
    private List<JPanel> panelsLieuxTouches;

    /** Noms des lieux touchés par l'événement. */
    private List<String> nomsLieuxTouches;



    /**
     * Création d'une nouvelle fiche évènement.
     * @param projetAssocie Projet auquel on va associer la fiche
     */
    public FicheEvenement(Projet projetAssocie) {
        // Appel du super-constructeur
        super("Création d'un événement");

        // Création d'un module vierge et du projet parent
        this.moduleLie = new Evenement(this, projetAssocie);

        // Création de l'interface :
        JPanel panel = new JPanel(new SpringLayout());

        //      - Ajout du nom
        JLabel nomLabel = new JLabel("Nom", JLabel.TRAILING);
        nom = new JTextField(10);
        nomLabel.setLabelFor(nom);
        panel.add(nomLabel);
        panel.add(nom);

        //      - Création des modèles de liste de date
        List<String> listeJours = new ArrayList<String>();
        listeJours.add((""));
        for (int i = 1; i <= 31; i++) {
            listeJours.add(Integer.toString(i));
        }
        List<String> listeMois = new ArrayList<String>();
        listeMois.add((""));
        for (int i = 1; i <= 12; i++) {
            listeMois.add(Integer.toString(i));
        }
        List<String> listeAnnees = new ArrayList<String>();
        for (int i = 1; i <= 10000; i++) {
            listeAnnees.add(Integer.toString(i));
        }

        //      - Ajout de la date de début
        JLabel dateDebutLabel = new JLabel("Date de début", JLabel.TRAILING);
        JPanel dateDebut = new JPanel(new FlowLayout());

        jourDebut = new JComboBox(listeJours.toArray());
        dateDebut.add(jourDebut);
        moisDebut = new JComboBox(listeMois.toArray());
        dateDebut.add(moisDebut);
        anneeDebut = new JComboBox(listeAnnees.toArray());
        anneeDebut.setEditable(true);
        dateDebut.add(anneeDebut);

        dateDebutLabel.setLabelFor(dateDebut);
        panel.add(dateDebutLabel);
        panel.add(dateDebut);

        //      - Ajout de la date de fin
        dateFinLabel = new JCheckBox("Date de fin");
        dateFinLabel.setToolTipText("Indique si l'événement est ponctuel ou non.");
        dateFinLabel.setSelected(false);
        dateFinLabel.addActionListener(
                new ActionPonctualiserEvenement(this, dateFinLabel)
        );

        JPanel dateFin = new JPanel(new FlowLayout());
        jourFin = new JComboBox(listeJours.toArray());
        jourFin.setEnabled(false);
        dateFin.add(jourFin);
        moisFin = new JComboBox(listeMois.toArray());
        moisFin.setEnabled(false);
        dateFin.add(moisFin);
        anneeFin = new JComboBox(listeAnnees.toArray());
        anneeFin.setEnabled(false);
        anneeFin.setEditable(true);
        dateFin.add(anneeFin);

        panel.add(dateFinLabel);
        panel.add(dateFin);

        //      - Ajout de la description
        JLabel descriptionLabel = new JLabel("Description", JLabel.TRAILING);
        description = new JTextArea(10, 10);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        descriptionLabel.setLabelFor(description);
        panel.add(descriptionLabel);
        panel.add(new JScrollPane(description));

        //      - Ajout de la liste des personnages présents
        panelPersoPresents = new JPanel();
        panelPersoPresents.setLayout(new BoxLayout(
                panelPersoPresents, BoxLayout.PAGE_AXIS
        ));
        panelPersoPresents.setPreferredSize(new Dimension(20, 50));

        panelsPersoPresents = new ArrayList<JPanel>();
        nomsPersoPresents = new ArrayList<String>();

        panel.add(new JLabel("Personnages présents"));
        panel.add(new JScrollPane(panelPersoPresents));

        //		- Ajout des personnages absents
        List<String> nomsPersosAbsents = new ArrayList<String>();
        for (Module p : projetAssocie.getListeFiltree(Personnage.TYPE)) {
            nomsPersosAbsents.add(p.getNom());
        }
        this.selectPersoAbsents = new JComboBox(nomsPersosAbsents.toArray());
        panel.add(this.selectPersoAbsents);

        JButton btnAjouterPersoPresent = new JButton("Ajouter");
        btnAjouterPersoPresent.addActionListener(
                new ActionAjouterPersonnagePresent(this)
        );
        panel.add(btnAjouterPersoPresent);

        //      - Ajout de la liste des lieux touchés
        panelLieuxTouches = new JPanel();
        panelLieuxTouches.setLayout(new BoxLayout(
                panelLieuxTouches, BoxLayout.PAGE_AXIS
        ));
        panelLieuxTouches.setPreferredSize(new Dimension(20, 50));

        panelsLieuxTouches = new ArrayList<JPanel>();
        nomsLieuxTouches = new ArrayList<String>();

        panel.add(new JLabel("Lieux touchés"));
        panel.add(new JScrollPane(panelLieuxTouches));

        //		- Ajout des lieux non touchés
        List<String> nomsLieuxNonTouches = new ArrayList<String>();
        for (Module p : projetAssocie.getListeFiltree(Lieu.TYPE)) {
            nomsLieuxNonTouches.add(p.getNom());
        }
        this.selectLieuxNonTouches = new JComboBox(nomsLieuxNonTouches.toArray());
        panel.add(this.selectLieuxNonTouches);

        JButton btnAjouterLieuTouche = new JButton("Ajouter");
        btnAjouterLieuTouche.addActionListener(
                new ActionAjouterLieuTouche(this)
        );
        panel.add(btnAjouterLieuTouche);

        //      - Gestion du layout
        SpringUtilities.makeCompactGrid(panel,
                8, 2, 6, 6, 6, 6);

        //      - Ajout à la fenetre
        super.addComposantSaisie(panel);

        //      - Gestion des boutons de validation
        super.addBoutons(
                new ActionValiderEvenement(this),
                new ActionAnnulerEvenement(this),
                new ActionSupprimerEvenement(this)
        );

		// Définir une taille minimale
        super.setMinimumSize(new Dimension(450, 400));

        // Afficher
        super.afficher();
    }

    /**
     * Permet de générer une vue à partir d'un module existant
     *
     * @param eve Module existant, source
     */
    public FicheEvenement(Evenement eve) {
        // Création de la vue
        this(eve.getProjetParent());

        // Enregistrement du module
        this.moduleLie = eve;

        // Invalidation des modifications (pour remplissage des champs)
        this.invaliderModifications();

        // Changer le titre de la fenetre
        this.setTitle("Evénement - " + eve.getNom());
    }


    /**
     * Permet de sauvegarder les saisies de la vue dans l'instance du modèle de module.
     */
    @Override
    public boolean validerModifications() {
        Evenement eve = (Evenement) moduleLie;

        // Enregistrement du nom si non vide
        if (this.nom.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Impossible de valider les modifications :\nvous devez saisir un nom !",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );

        } else {
            eve.setNom(this.nom.getText());

            // Enregistrement de la date de début
            try {
                eve.setDateDebut(FicheEvenement.getDate(
                        this.jourDebut, this.moisDebut, this.anneeDebut
                ));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "La date de début d'événement que vous avez renseignée est"
                                + " invalide :\n" + e.getMessage(),
                        "Date invalide !",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            // Enregistrement de l'utilité de la date de fin et de la date de fin
            eve.setEstUnePeriode(this.dateFinLabel.isSelected());

            if (this.dateFinLabel.isSelected()) {
                // Enregistrement de la date de fin
                try {
                    // Création de la date de fin
                    Date dateFin = FicheEvenement.getDate(
                            this.jourFin, this.moisFin, this.anneeFin
                    );

                    // Enregistrement seulement si elle est supérieure à la date de début
                    if (dateFin.compareTo(eve.getDateDebut()) >= 0) {
                        eve.setDateFin(dateFin);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "La date de fin doit etre supérieure à la date de début !",
                                "Erreur : date invalide !",
                                JOptionPane.ERROR_MESSAGE
                        );
                        eve.setEstUnePeriode(false);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "La date de fin d'événement que vous avez renseignée est"
                                    + " invalide :\n\t" + e.getMessage(),
                            "Erreur : date invalide !",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            // Enregistrement de la description
            eve.setDescription(this.description.getText());

            // Enregistrement des personnages présents
            for (String nom : this.nomsPersoPresents) {
                eve.setPersoPresent(nom);
            }

            // Enregistrement des personnages absents
            for (int i = 0; i < this.selectPersoAbsents.getItemCount(); i++) {
                String nom = (String) this.selectPersoAbsents.getItemAt(i);
                eve.setPersoAbsent(nom);
            }

            // Enregistrement des lieux touchés par l'événement
            for (String nom : this.nomsLieuxTouches) {
                eve.setLieuTouche(nom);
            }

            // Enregistrement des lieux non touchés par l'événement
            for (int i = 0; i < this.selectLieuxNonTouches.getItemCount(); i++) {
                String nom = (String) this.selectLieuxNonTouches.getItemAt(i);
                eve.setLieuNonTouche(nom);
            }

            // Changer le titre de la fenetre
            this.setTitle("Evénement - " + eve.getNom());
        }
        return true;
    }

    /**
     * Permet d'invalider les saisies de la vue depuis l'instance du modèle de module.
     */
    @Override
    public void invaliderModifications() {
        // Récupération de l'événement lié
        Evenement eve = (Evenement) moduleLie;

        // Remise à zéro du nom
        this.nom.setText(eve.getNom());

        // Remise à zéro de la date de début
        FicheEvenement.setSelectedElementForDate(eve.getDateDebut().getJour(), this.jourDebut);
        FicheEvenement.setSelectedElementForDate(eve.getDateDebut().getMois(), this.moisDebut);
        this.anneeDebut.setSelectedItem(String.valueOf(eve.getDateDebut().getAnnee()));

        // Remise à zéro de la date de fin
        this.activerDateFin(eve.getEstUnePeriode());
        if (eve.getEstUnePeriode()) {
            FicheEvenement.setSelectedElementForDate(eve.getDateFin().getJour(), this.jourFin);
            FicheEvenement.setSelectedElementForDate(eve.getDateFin().getMois(), this.moisFin);
            this.anneeFin.setSelectedItem(String.valueOf(eve.getDateFin().getAnnee()));
        }

        // Remise à zéro de la description
        this.description.setText(eve.getDescription());

        // Remise à zéro des personnages absents
        this.selectPersoAbsents.removeAllItems();
        for (String nom : eve.getListePersoAbsents()) {
            this.selectPersoAbsents.addItem(nom);
        }

        // Remise à zéro des personnages présents
        this.panelPersoPresents.removeAll();
        this.nomsPersoPresents.clear();
        this.panelsPersoPresents.clear();
        for (String nom : eve.getListePersoPresents()) {
            this.ajouterPersoPresent(nom);
        }

        // Remise à zéro des lieux non touchés par l'événement
        this.selectLieuxNonTouches.removeAllItems();
        for (String nom : eve.getListeLieuxNonTouches()) {
            this.selectLieuxNonTouches.addItem(nom);
        }

        // Remise à zéro des lieux touchés par l'événement
        this.panelLieuxTouches.removeAll();
        this.nomsLieuxTouches.clear();
        this.panelsLieuxTouches.clear();
        for (String nom : eve.getListeLieuxTouches()) {
            this.ajouterLieuTouche(nom);
        }

        // Mise à jour de l'affichage
        this.refresh();
    }

    /**
     * Permet de mettre à jour la saisie d'un jour ou d'un mois.
     *
     * @param valeur     Valeur à renseigner dans la zone de saisie
     * @param zoneSaisie Zone de saisie à remplir
     */
    private static void setSelectedElementForDate(int valeur, JComboBox zoneSaisie) {
        // Si la valeur n'est pas 0, alors elle est valide
        if (valeur != 0) {
            zoneSaisie.setSelectedItem(String.valueOf(valeur));

            // Sinon, alors renseigner la chaine vide
        } else {
            zoneSaisie.setSelectedItem("");
        }
    }


    /**
     * Permet de générer une date à partir des 3 champs de saisie.
     *
     * @param jour  Champ de saisie du jour
     * @param mois  Champ de saisie du mois
     * @param annee Champ de saisie de l'année
     * @return date générée.
     */
    private static Date getDate(JComboBox jour, JComboBox mois, JComboBox annee) {
        Date dateARetourner = new Date();

        // Récupération de l'année
        Object saisieAnnee = annee.getSelectedItem();
        if (saisieAnnee instanceof String) {
            try {
                dateARetourner.setAnnee(Integer.valueOf((String) saisieAnnee));

            } catch (NumberFormatException e) {
                throw new NumberFormatException("L'année n'est pas un nombre valide.");
            }
        } else {
            dateARetourner.setAnnee((Integer) saisieAnnee);
        }

        // Récupération du mois si la saisie n'est pas vide
        if (!mois.getSelectedItem().equals("")) {
            dateARetourner.setMois(Integer.valueOf((String) mois.getSelectedItem()));

            // Si le jour n'est pas vide, l'enregistrer
            if (!jour.getSelectedItem().equals("")) {
                dateARetourner.setJour(Integer.valueOf((String) jour.getSelectedItem()));
            }

            // Sinon, si le jour est renseigné, indiquer qu'on ne peut pas choisir de jour
            // sans choisir de mois
        } else if (!((String) jour.getSelectedItem()).equals("")) {
            throw new IllegalStateException(
                    "Vous ne pouvez pas sélectionner un jour sans sélectionner de mois."
            );
        }

        // Renvoyer la date générée
        return dateARetourner;
    }

    /**
     * Permet d'activer la saisie de la date de fin.
     * @param dateFinActivee TRUE si la date de fin est utile (événement non ponctuel)
     */
    public void activerDateFin(boolean dateFinActivee) {
        this.jourFin.setEnabled(dateFinActivee);
        this.moisFin.setEnabled(dateFinActivee);
        this.anneeFin.setEnabled(dateFinActivee);

        this.dateFinLabel.setSelected(dateFinActivee);
    }

    /**
     * Mettre à jour l'affichage si nécessaire.
     * @param observable Objet observé (Module)
     * @param arguments  Objet de notification (Notification)
     */
    public void update(Observable observable, Object arguments) {
        // Si la notification est valide, la prendre en compte
        if (observable instanceof Evenement && arguments instanceof Notification) {

            // Récupérer la notification
            Notification notif = (Notification) arguments;

            /************************** Gestion des personnages ************************/
            // S'il s'agit d'un ajout de personnage, le compter dans les
            // personnages absents
            if (notif.getAction() == TypeNotification.AJOUT
                    && notif.getNouvelEtat() instanceof Personnage) {

                this.selectPersoAbsents.addItem(notif.getNouvelEtat().getNom());
            }

            // S'il s'agit d'une suppression de personnage, le supprimer des
            // personnages absents et des personnages présents
            if (notif.getAction() == TypeNotification.SUPPRESSION
                    && notif.getEtatPrecedent() instanceof Personnage) {

                // Récupération du nom
                String nomSuppr = notif.getEtatPrecedent().getNom();

                // Suppression des personnages absents
                this.selectPersoAbsents.removeItem(nomSuppr);

                // Suppression des personnages présents
                if (this.nomsPersoPresents.contains(nomSuppr)) {
                    int posSuppr = this.nomsPersoPresents.indexOf(nomSuppr);

                    // ... dans le panel conteneur
                    this.panelPersoPresents.remove(
                            this.panelsPersoPresents.get(posSuppr)
                    );
                    // ... dans la sauvegarde des panels
                    this.panelsPersoPresents.remove(posSuppr);
                    // ... dans les noms des persos présents
                    this.nomsPersoPresents.remove(posSuppr);
                }
            }

            // S'il s'agit d'une modification de personnage
            if (notif.getAction() == TypeNotification.MODIFICATION
                    && notif.getEtatPrecedent() instanceof Personnage) {

                // Récupération de l'ancien et du nouveau nom
                String ancienNom = notif.getEtatPrecedent().getNom();
                String nouveauNom = notif.getNouvelEtat().getNom();

                // Correction dans les personnages présents
                if (this.nomsPersoPresents.contains(ancienNom)) {
                    int posCorrection = this.nomsPersoPresents.indexOf(ancienNom);

                    // ... correction du label d'affichage du nom
                    JLabel labelCorrection = (JLabel) this.panelsPersoPresents
                            .get(posCorrection).getComponent(0);
                    labelCorrection.setText(nouveauNom);

                    // ... correction dans la structure enregistrant les noms des
                    //     personnages présents
                    this.nomsPersoPresents.set(posCorrection, nouveauNom);

                // Correction dans les personnages absents
                } else {
                    this.selectPersoAbsents.removeItem(ancienNom);
                    this.selectPersoAbsents.addItem(nouveauNom);
                }

            }


            /************************** Gestion des lieux ******************************/
            // S'il s'agit d'un ajout de lieu, le compter dans les lieux non touchés
            if (notif.getAction() == TypeNotification.AJOUT
                    && notif.getNouvelEtat() instanceof Lieu) {

                this.selectLieuxNonTouches.addItem(notif.getNouvelEtat().getNom());
            }

            // S'il s'agit d'une suppression de lieu, le supprimer des
            // lieux non touchés et des lieux touchés
            if (notif.getAction() == TypeNotification.SUPPRESSION
                    && notif.getEtatPrecedent() instanceof Lieu) {

                // Récupération du nom
                String nomSuppr = notif.getEtatPrecedent().getNom();

                // Suppression des lieux non touchés
                this.selectLieuxNonTouches.removeItem(nomSuppr);

                // Suppression des lieux touchés
                if (this.nomsLieuxTouches.contains(nomSuppr)) {
                    int posSuppr = this.nomsLieuxTouches.indexOf(nomSuppr);

                    // ... dans le panel conteneur
                    this.panelLieuxTouches.remove(
                            this.panelsLieuxTouches.get(posSuppr)
                    );
                    // ... dans la sauvegarde des panels
                    this.panelsLieuxTouches.remove(posSuppr);
                    // ... dans les noms des lieux touchés
                    this.nomsLieuxTouches.remove(posSuppr);
                }
            }

            // S'il s'agit d'une modification de lieu
            if (notif.getAction() == TypeNotification.MODIFICATION
                    && notif.getEtatPrecedent() instanceof Lieu) {

                // Récupération de l'ancien et du nouveau nom
                String ancienNom = notif.getEtatPrecedent().getNom();
                String nouveauNom = notif.getNouvelEtat().getNom();

                // Correction dans les lieux touchés
                if (this.nomsLieuxTouches.contains(ancienNom)) {
                    int posCorrection = this.nomsLieuxTouches.indexOf(ancienNom);

                    // ... correction du label d'affichage du nom
                    JLabel labelCorrection = (JLabel) this.panelsLieuxTouches
                            .get(posCorrection).getComponent(0);
                    labelCorrection.setText(nouveauNom);

                    // ... correction dans la structure enregistrant les noms des
                    //     lieux touchés
                    this.nomsLieuxTouches.set(posCorrection, nouveauNom);

                // Correction dans les lieux non touchés
                } else {
                    this.selectLieuxNonTouches.removeItem(ancienNom);
                    this.selectLieuxNonTouches.addItem(nouveauNom);
                }

            }


            /************************* Rafraichissement de l'affichage *****************/
            this.refresh();
        }
    }

    /**
     * Permet de valider l'ajout du personnage sélectionné en tant que personnage
     * présent à l'événement.
     */
    public void ajouterPersoPresent() {
        // Récupérer la sélection à ajouter
        String saisie = (String) this.selectPersoAbsents.getSelectedItem();

        // Si la saisie est bien le nom d'un personnage à ajouter, l'ajouter
        if (saisie != null && !saisie.equals("")) {
            // Ajout du personnage présent
            this.ajouterPersoPresent(saisie);

            // Retirer la saisie de la liste des possibles
            this.selectPersoAbsents.removeItem(saisie);

            // Rafraichissement de l'affichage
            this.refresh();
        }
    }

    /**
     * Permet d'ajouter un personnage présent au panel.
     * @param nom le nom du personnage à ajouter
     */
    private void ajouterPersoPresent(String nom) {
        // Création du champ
        JPanel nouveauPerso = new JPanel(new FlowLayout());
        JLabel nomNouveauPerso = new JLabel(nom);

        nouveauPerso.add(nomNouveauPerso);

        JButton supprPerso = new JButton("x");
        supprPerso.addActionListener(
                new ActionSupprimerPersonnagePresent(this, nomNouveauPerso)
        );
        nouveauPerso.add(supprPerso);

        // Ajout du champ dans les personnages présents
        this.panelsPersoPresents.add(nouveauPerso);
        this.nomsPersoPresents.add(nom);

        // Ajout du champ à la fenetre
        this.panelPersoPresents.add(nouveauPerso);
    }

    /**
     * Permet de valider la suppression d'un personnage présent.
     * @param nomPerso le personnage à mettre en tant qu'absent
     */
    public void supprPersoPresent(String nomPerso) {
        // Récupérer l'ID du personnage
        int id = this.nomsPersoPresents.indexOf(nomPerso);

        if (id != -1) {
            // Supprimer l'affichage
            this.panelPersoPresents.remove(
                    this.panelsPersoPresents.get(id)
            );

            // Ajouter le nom dans la liste des personnages absents
            this.selectPersoAbsents.addItem(nomPerso);

            // Supprimer des personnages présents
            this.nomsPersoPresents.remove(id);
            this.panelsPersoPresents.remove(id);

            // Rafraichir l'affichage
            this.refresh();
        }
    }


    /**
     * Permet de valider l'ajout du lieu sélectionné en tant que lieu touché
     * par l'événement.
     */
    public void ajouterLieuTouche() {
        // Récupérer la sélection à ajouter
        String saisie = (String) this.selectLieuxNonTouches.getSelectedItem();

        // Si la saisie est bien le nom d'un lieu à ajouter, l'ajouter
        if (saisie != null && !saisie.equals("")) {
            // Ajout du lieu
            this.ajouterLieuTouche(saisie);

            // Retirer la saisie de la liste des possibles
            this.selectLieuxNonTouches.removeItem(saisie);

            // Rafraichissement de l'affichage
            this.refresh();
        }
    }

    /**
     * Permet d'ajouter un lieu touché au panel.
     * @param nom le nom du lieu à ajouter
     */
    private void ajouterLieuTouche(String nom) {
        // Création du champ
        JPanel nouveauLieu = new JPanel(new FlowLayout());
        JLabel nomNouveauLieu = new JLabel(nom);

        nouveauLieu.add(nomNouveauLieu);

        JButton supprLieu = new JButton("x");
        supprLieu.addActionListener(
                new ActionSupprimerLieuTouche(this, nomNouveauLieu)
        );
        nouveauLieu.add(supprLieu);

        // Ajout du champ dans les lieux touchés
        this.panelsLieuxTouches.add(nouveauLieu);
        this.nomsLieuxTouches.add(nom);

        // Ajout du champ à la fenetre
        this.panelLieuxTouches.add(nouveauLieu);
    }

    /**
     * Permet de valider la suppression d'un lieu touché.
     * @param nomLieu le lieu à indiquer comme non touché par l'événement
     */
    public void supprLieuTouche(String nomLieu) {
        // Récupérer l'ID du lieu
        int id = this.nomsLieuxTouches.indexOf(nomLieu);

        if (id != -1) {
            // Supprimer l'affichage
            this.panelLieuxTouches.remove(
                    this.panelsLieuxTouches.get(id)
            );

            // Ajouter le nom dans la liste des lieux non touchés
            this.selectLieuxNonTouches.addItem(nomLieu);

            // Supprimer des lieux touchés
            this.nomsLieuxTouches.remove(id);
            this.panelsLieuxTouches.remove(id);

            // Rafraichir l'affichage
            this.refresh();
        }
    }

    /**
     * Permet de rafraichir l'affichage de la fenetre.
     */
    private void refresh() {
        // Calcul des nouvelles dimensions de la zone d'affichage
        // des personnages présents
        Dimension tailleZonePersos = new Dimension(
                this.panelPersoPresents.getWidth(),
                this.nomsPersoPresents.size() * 40
        );
        this.panelPersoPresents.setPreferredSize(tailleZonePersos);

        // Calcul des nouvelles dimensions de la zone d'affichage
        // des lieux touchés par l'événement
        Dimension tailleZoneLieux = new Dimension(
                this.panelLieuxTouches.getWidth(),
                this.nomsLieuxTouches.size() * 40
        );
        this.panelLieuxTouches.setPreferredSize(tailleZoneLieux);

        // Rafraichissement de l'affichage
        this.panelPersoPresents.revalidate();
        this.panelLieuxTouches.revalidate();
        this.repaint();
    }
}
