package vue;

import java.awt.Dimension;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import assets.SpringUtilities;
import controleur.lieu.ActionAnnulerLieu;
import controleur.lieu.ActionSupprimerLieu;
import controleur.lieu.ActionValiderLieu;
import modele.Lieu;
import modele.Projet;
import modele.notification.Notification;


/**
 * Permet de gérer la vue d'un lieu de l'histoire.
 *
 * @author      G14
 * @version     1.0
 */
public class FicheLieu extends FicheModule {

	/** Label de nom. */
	private JLabel nomLabel;

	/** Champ d'édition du nom. */
	private JTextField nom;

	/** Label de description. */
	private JLabel descriptionLabel;

	/** Champ d'édition de la description. */
	private JTextArea description;


	/**
	 * Création d'un nouveau lieu.
	 * @param projetAssocie Projet auquel on va associer la fiche
	 */
	public FicheLieu(Projet projetAssocie) {
		// Appel du super-constructeur
		super("Création d'un lieu");

		// Création d'un module vierge associé au projet
		this.moduleLie = new Lieu(this, projetAssocie);

		// Création de l'interface :
		JPanel panel = new JPanel(new SpringLayout());

		//      - Ajout du nom
		nomLabel = new JLabel("Nom", JLabel.TRAILING);
		nom = new JTextField(10);
		nomLabel.setLabelFor(nom);
		panel.add(nomLabel);
		panel.add(nom);

		//      - Ajout de la description
		descriptionLabel = new JLabel("Description", JLabel.TRAILING);
		description = new JTextArea(10, 10);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		descriptionLabel.setLabelFor(description);
		panel.add(descriptionLabel);
		panel.add(new JScrollPane(description));

		//      - Gestion du layout
		SpringUtilities.makeCompactGrid(panel,
				2, 2, 6, 6, 6, 6);

		//      - Ajout à la fenetre
		super.addComposantSaisie(panel);

		//      - Gestion des boutons de validation
		super.addBoutons(
				new ActionValiderLieu(this),
				new ActionAnnulerLieu(this),
				new ActionSupprimerLieu(this)
				);

		// Définir une taille minimale
		super.setMinimumSize(new Dimension(325, 250));

		// Afficher
		super.afficher();
	}

	/**
	 * Permet de générer une vue à partir d'un module existant
	 * @param lieu              Module existant, source
	 */
	public FicheLieu(Lieu lieu) {
		// Création de la vue
		this(lieu.getProjetParent());

		// Enregistrement du module
		this.moduleLie = lieu;

		// Invalidation des modifications (pour remplissage des champs)
		this.invaliderModifications();

		// Changer le titre de la fenetre
		this.setTitle("Lieu - " + lieu.getNom());
	}


	/**
	 * Permet de sauvegarder les saisies de la vue dans l'instance du modèle de module.
	 */
	@Override
	public boolean validerModifications() {
		// Récupération du lieu
		Lieu lieu = (Lieu) this.moduleLie;

		// On met à jour le lieu si le nom n'est pas vide
		if (this.nom.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Impossible de valider les modifications :\nvous devez saisir un nom !",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            lieu.setNom(this.nom.getText());
            lieu.setDescription(this.description.getText());

            // Changer le titre de la fenetre
            this.setTitle("Lieu - " + lieu.getNom());
        }
		return true;
	}

	/**
	 * Permet d'invalider les saisies de la vue depuis l'instance du modèle de module.
	 */
	@Override
	public void invaliderModifications() {
		// Récupération du lieu
		Lieu lieu = (Lieu) this.moduleLie;

		// On remplit les zones de texte avec les informations du lieu.
		this.nom.setText(lieu.getNom());
		this.description.setText(lieu.getDescription());
	}

	/**
	 * Mettre à jour l'affichage si nécessaire.
	 * @param observable    Objet observé (Module)
	 * @param arguments     Objet de notification (Notification)
	 */
	public void update(Observable observable, Object arguments) {
		// Si la notification est valide, la prendre en compte
		if (observable instanceof Lieu && arguments instanceof Notification) {
		}
	}
}
