package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import assets.SpringUtilities;
import controleur.personnage.ActionAjouterRelation;
import controleur.personnage.ActionAnnulerPersonnage;
import controleur.personnage.ActionSupprimerPersonnage;
import controleur.personnage.ActionSupprimerRelation;
import controleur.personnage.ActionValiderPersonnage;
import modele.Module;
import modele.Personnage;
import modele.Projet;
import modele.Relation;
import modele.notification.Notification;
import modele.notification.TypeNotification;

/**
 * Permet de gérer la vue d'un personnage de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class FichePersonnage extends FicheModule {

	/** Champ d'édition du nom. */
	private JTextField nom;

	/** Champ d'édition du prénom. */
	private JTextField prenom;

	/** Champ d'édition de la description. */
	private JTextArea description;

	/** Menu de choix du personnage. */
	private JComboBox selectRelationPotentielles;
	
	/** Champ d'édition de la relation. */
	private JTextField nomNouvelleRelation;

	/** Panel contenant les relations existantes. */
	private JPanel panelRelationsExistantes;

	/** Panels des relations existantes. */
	private List<JPanel> panelsRelationsExistantes;

	/** Relations existantes. */
	private List<Relation> relationsExistantes;


	/**
	 * Création d'un nouveau personnage.
	 * @param projetAssocie Projet auquel on va associer la fiche
	 */
	public FichePersonnage(Projet projetAssocie) {
		// Appel du super-constructeur
		super("Création d'un personnage");

		// Création d'un module vierge
		this.moduleLie = new Personnage(this, projetAssocie);

		// Création de l'interface :
		JPanel panel = new JPanel(new SpringLayout());
		JPanel panelRelation = new JPanel(new FlowLayout());

		//      - Ajout du prénom
		JLabel prenomLabel = new JLabel("Prénom", JLabel.TRAILING);
		prenom = new JTextField(10);
		prenomLabel.setLabelFor(prenom);
		panel.add(prenomLabel);
		panel.add(prenom);

		//      - Ajout du nom
		JLabel nomLabel = new JLabel("Nom", JLabel.TRAILING);
		nom = new JTextField(10);
		nomLabel.setLabelFor(nom);
		panel.add(nomLabel);
		panel.add(nom);

		//      - Ajout de la description
		JLabel descriptionLabel = new JLabel("Description", JLabel.TRAILING);
		description = new JTextArea(10, 10);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		descriptionLabel.setLabelFor(description);
		panel.add(descriptionLabel);
		panel.add(new JScrollPane(description));

        //      - Ajout de la liste des relations existantes
        panelRelationsExistantes = new JPanel();
        panelRelationsExistantes.setLayout(new BoxLayout(
                panelRelationsExistantes, BoxLayout.PAGE_AXIS
        ));
        panelRelationsExistantes.setPreferredSize(new Dimension(20, 50));

        panelsRelationsExistantes = new ArrayList<JPanel>();
        relationsExistantes = new ArrayList<Relation>();

        panel.add(new JLabel("Relations"));
        panel.add(new JScrollPane(panelRelationsExistantes));

		// PARTIE RELATIONS - CHOIX -
		//		- Ajout du choix de personnage lié
        List<String> nomsRelationsPotentielles = new ArrayList<String>();
        for (Module p : projetAssocie.getListeFiltree(Personnage.TYPE)) {
            nomsRelationsPotentielles.add(p.getNom());
        }
		selectRelationPotentielles = new JComboBox(nomsRelationsPotentielles.toArray());
		panel.add(selectRelationPotentielles);

		//		- Ajout du Séparateur
		panelRelation.add(new JLabel(":"));

		//		- Ajout du choix de relation
		nomNouvelleRelation = new JTextField(6);
		panelRelation.add(nomNouvelleRelation);

		//      - Ajout du bouton d'ajout de relation
		JButton boutonAjouterRelation = new JButton ("Ajouter");
		boutonAjouterRelation.addActionListener(
		        new ActionAjouterRelation(this)
        );
		panelRelation.add(boutonAjouterRelation);

		panel.add(panelRelation);

		//      - Gestion du layout
		SpringUtilities.makeCompactGrid(panel,
				5, 2, 6, 6, 6, 6);

		//      - Ajout à la fenetre
        super.addComposantSaisie(panel);

		//      - Gestion des boutons de validation
		super.addBoutons(
				new ActionValiderPersonnage(this),
				new ActionAnnulerPersonnage(this),
				new ActionSupprimerPersonnage(this)
        );

		// Définir une taille minimale
        super.setMinimumSize(new Dimension(350,350));

		// Afficher
		super.afficher();
	}

	/**
	 * Permet de générer une vue à partir d'un module existant
	 * @param perso             Module existant, source
	 */
	public FichePersonnage(Personnage perso) {
		// Création de la vue
		this(perso.getProjetParent());

		// Enregistrement du module
		this.moduleLie = perso;

		// Invalidation des modifications (pour remplissage des champs)
		this.invaliderModifications();

		// Changer le titre de la fenetre
		this.setTitle("Personnage - " + perso.getNom());
	}


	/**
	 * Permet de sauvegarder les saisies de la vue dans l'instance du modèle de module.
	 */
	@Override
	public boolean validerModifications() {
		// Récupération du personnage
		Personnage perso = (Personnage) this.moduleLie;

		// Validation des changements si le nom ou le prénom n'est pas vide
        if (this.prenom.getText().equals("") && this.nom.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Impossible de valider les modifications :\nvous devez saisir un nom !",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );
            
            //Modifications non validées
            return false;

        } else {
            perso.setPrenom(this.prenom.getText());
            perso.setNomFamille(this.nom.getText());
            perso.setDescription(this.description.getText());

            // Validation des relations
            perso.getRelations().clear();
            for (Relation rel : this.relationsExistantes) {
                perso.ajouterRelation(rel.clone());
            }

            // Changement du titre de la fenetre
            this.setTitle("Personnage - " + perso.getNom());
            
            //Modifications validées
            return true;
        }
	}

	/**
	 * Permet d'invalider les saisies de la vue depuis l'instance du modèle de module.
	 */
	@Override
	public void invaliderModifications() {
		// Récupération du personnage
		Personnage perso = (Personnage) this.moduleLie;

		// Invalidation des changements
		this.prenom.setText(perso.getPrenom());
		this.nom.setText(perso.getNomFamille());
		this.description.setText(perso.getDescription());

		// Regénération des relations possibles
        this.selectRelationPotentielles.removeAllItems();
        for (String nomPerso : perso.getRelationsPotentielles()) {
            this.selectRelationPotentielles.addItem(nomPerso);
        }

        // Regénération des relations existantes
        this.panelRelationsExistantes.removeAll();
        this.relationsExistantes.clear();
        this.panelsRelationsExistantes.clear();
        for (Relation relation : perso.getRelations()) {
            this.ajouterRelation(relation.getNomPersoImplique(), relation.getType());
        }

        // Rafraichissement de l'affichage
        this.refresh();
	}

    /**
     * Mettre à jour l'affichage si nécessaire.
     * @param observable    Objet observé (Module)
     * @param arguments     Objet de notification (Notification)
     */
	public void update(Observable observable, Object arguments) {
	    // Si la notification est valide, la prendre en compte
	    if (observable instanceof Personnage && arguments instanceof Notification) {
            // Récupération de la notification
            Notification notif = (Notification) arguments;

            // Ajout d'un personnage => l'ajouter dans la liste des relations possibles
            if (notif.getAction() == TypeNotification.AJOUT
                    && notif.getNouvelEtat() instanceof Personnage) {

                this.selectRelationPotentielles.addItem(
                        notif.getNouvelEtat().getNom()
                );
            }

            // Modification d'un personnage
            // => traiter les relations déjà existantes
            // => modifier la liste des relations potentielles
            if (notif.getAction() == TypeNotification.MODIFICATION
                    && notif.getNouvelEtat() instanceof Personnage) {

                String ancienNom = notif.getEtatPrecedent().getNom();
                String nouveauNom = notif.getNouvelEtat().getNom();

                // Relations potentielles
                this.selectRelationPotentielles.removeItem(ancienNom);
                this.selectRelationPotentielles.addItem(nouveauNom);

                // Relations existantes
                Iterator<Relation> it = this.relationsExistantes.iterator();
                while (it.hasNext()) {
                    Relation rel = it.next();

                    if (rel.getNomPersoImplique().equals(ancienNom)) {
                        // Si les noms sont identiques
                        // Récupérer l'index
                        int index = this.relationsExistantes.indexOf(rel);

                        // Corriger l'affichage
                        JLabel nomPerso = (JLabel) this.panelsRelationsExistantes
                                .get(index).getComponent(0);
                        nomPerso.setText(nouveauNom);

                        // Corriger la relation
                        rel.setNomPersoImplique(nouveauNom);
                    }
                }
            }

            // Suppression d'un personnage
            // => traiter les relations déjà existantes
            // => modifier la liste des relations potentielles
            if (notif.getAction() == TypeNotification.SUPPRESSION
                    && notif.getEtatPrecedent() instanceof Personnage) {

                String nom = notif.getEtatPrecedent().getNom();

                // Relations potentielles
                this.selectRelationPotentielles.removeItem(nom);

                // Relations existantes
                Iterator<Relation> it = this.relationsExistantes.iterator();
                while (it.hasNext()) {
                    Relation rel = it.next();

                    if (rel.getNomPersoImplique().equals(nom)) {
                        // Si les noms sont identiques
                        // Récupérer l'index
                        int index = this.relationsExistantes.indexOf(rel);

                        // Supprimer l'affichage
                        this.panelRelationsExistantes.remove(
                                this.panelsRelationsExistantes.get(index)
                        );

                        // Supprimer des mémoires
                        this.panelsRelationsExistantes.remove(index);
                        it.remove();
                    }
                }
            }

            // Rafraichissement de l'affichage
            this.refresh();
        }
	}

    /**
     * Permet au controleur de valider l'ajout d'une relation.
     */
	public void ajouterRelation() {
        // Récupérer la sélection à ajouter
        String nom = (String) this.selectRelationPotentielles.getSelectedItem();
        String typeRelation = this.nomNouvelleRelation.getText();

        // Si la saisie est bien le nom d'un personnage à ajouter, l'ajouter
        if (nom != null && !nom.equals("") && !typeRelation.equals("")) {
            // Ajout du personnage présent
            this.ajouterRelation(nom, typeRelation);

            // Vider le champ de saisie
            this.nomNouvelleRelation.setText("");

            // Rafraichissement de l'affichage
            this.refresh();
        }
    }

    /**
     *
     * Permet d'ajouter une nouvelle relation  au panel.
     * @param nom          le nom du personnage à ajouter
     * @param typeRelation le type de la nouvelle relation
     */
    private void ajouterRelation(String nom, String typeRelation) {
        // Création de la relation
        Relation relation = new Relation(nom, typeRelation);

        // Création du champ
        JPanel nouveauPerso = new JPanel(new FlowLayout());

        nouveauPerso.add(new JLabel(nom));
        nouveauPerso.add(new JLabel(":"));
        nouveauPerso.add(new JLabel(typeRelation));

        JButton supprPerso = new JButton("x");
        supprPerso.addActionListener(
                new ActionSupprimerRelation(this, relation)
        );
        nouveauPerso.add(supprPerso);

        // Ajout du champ dans les structures de sauvegarde
        this.panelsRelationsExistantes.add(nouveauPerso);
        this.relationsExistantes.add(relation);

        // Ajout du champ à la fenetre
        this.panelRelationsExistantes.add(nouveauPerso);
    }

    /**
     * Permet de rafraichir l'affichage de la fenetre.
     */
    private void refresh() {
        // Calcul des nouvelles dimensions
        Dimension tailleZone = new Dimension(
                this.panelRelationsExistantes.getWidth(),
                this.relationsExistantes.size() * 40
        );
        this.panelRelationsExistantes.setPreferredSize(tailleZone);

        // Rafraichissement de l'affichage
        this.selectRelationPotentielles.revalidate();
        this.repaint();
    }

    /**
     * Permet de supprimer la relation.
     * @param relation Relation à supprimer
     */
    public void supprRelation(Relation relation) {
        // Récupérer l'ID de la relation
        int id = this.relationsExistantes.indexOf(relation);

        if (id != -1) {
            // Supprimer l'affichage
            this.panelRelationsExistantes.remove(
                    this.panelsRelationsExistantes.get(id)
            );

            // Supprimer des relations existantes
            this.relationsExistantes.remove(id);
            this.panelsRelationsExistantes.remove(id);

            // Rafraichir l'affichage
            this.refresh();
        }
    }
}
