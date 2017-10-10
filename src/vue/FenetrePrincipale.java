package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import assets.DesktopScrollPane;
import controleur.evenement.ActionCreerEvenement;
import controleur.evenement.ActionOuvrirEvenement;
import controleur.frise.ActionCreerFriseVierge;
import controleur.frise.ActionGenererFrise;
import controleur.frise.ActionOuvrirFrise;
import controleur.lieu.ActionCreerLieu;
import controleur.lieu.ActionOuvrirLieu;
import controleur.personnage.ActionCreerPersonnage;
import controleur.personnage.ActionOuvrirPersonnage;
import controleur.projet.ActionEnregistrerProjet;
import controleur.projet.ActionEnregistrerProjetSous;
import controleur.projet.ActionNouveauProjet;
import controleur.projet.ActionOuvrirProjet;
import controleur.projet.ActionQuitter;
import modele.Evenement;
import modele.Frise;
import modele.Lieu;
import modele.Personnage;
import modele.Projet;

/**
 * Permet de gérer la fenetre principale du programme.
 *
 * @author      G14
 * @version     1.0
 */

public class FenetrePrincipale extends JFrame {

	/** Dimensions de la fenetre. */
	public static final Dimension DIMENSONS_FEN_PRINCIPALE = new Dimension(512, 512);

	/** Projet correspondant à la fenetre. */
	private Projet projet;

	/** Les fenetres internes qui composent la fenetre principale. */
	private JDesktopPane lesFenetresInternes;

	/** Nom du programme. */
	public static final String MANUSCRIT_MANAGER = "Manuscrit Manager";

	/** Les positions d'ouverture des différents modules. */
	private int frameCountXEven;
	private int frameCountYEven;
	private int frameCountXLieu;
	private int frameCountYLieu;
	private int frameCountXPers;
	private int frameCountYPers;
	private int frameCountXFrise;
	private int frameCountYFrise;

	public FenetrePrincipale(String fichierAOuvrir) {
		// Création de la fenetre
		this();

		// Ouverture du projet associé au fichier
		new ActionOuvrirProjet(this, projet).ouvrir(fichierAOuvrir);
	}
	/**
	 * Créer une fenetre principale sans projet associé.
	 */
	public FenetrePrincipale() {
		/* Appel du super-constructeur */
		super(FenetrePrincipale.MANUSCRIT_MANAGER);

		/* Enregistrement d'un layout */
		this.setLayout(new BorderLayout());

		/* Création d'un nouveau projet */
		this.projet = new Projet();

		/* Minimisation de la fenetre */
		super.setMinimumSize(FenetrePrincipale.DIMENSONS_FEN_PRINCIPALE);

		/* Dimension de la fenetre */
		this.setExtendedState(this.MAXIMIZED_BOTH);

		/* Initialisation des fenetres internes avec contenu scrollable */
		this.lesFenetresInternes = new JDesktopPane();
		this.setPreferredSize(FenetrePrincipale.DIMENSONS_FEN_PRINCIPALE);
		DesktopScrollPane scrollInterne = new DesktopScrollPane(this.lesFenetresInternes);
		this.setContentPane(scrollInterne);

		/* Initialisation des positions pour la première ouveture de chaque modules */
		frameCountXEven = 0;
		frameCountYEven = 0;
		frameCountXLieu = 10;
		frameCountYLieu = 0;
		frameCountXPers = 17;
		frameCountYPers = 0;
		frameCountXFrise = 13;
		frameCountYFrise = 3;

		// Ajouter les menus à la fenetre
		/* Insertion des menus */
		JMenuBar lesMenus = new JMenuBar();

		/* Le premier menu : Fichier */
		JMenu menuFichier = new JMenu("Fichier");

		JMenuItem itemNouveau = new JMenuItem("Nouveau");
		itemNouveau.addActionListener(new ActionNouveauProjet());
		itemNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));

		JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
		itemOuvrir.addActionListener(new ActionOuvrirProjet(this, this.projet));
		itemOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));

		JMenuItem itemEnregistrer = new JMenuItem("Enregistrer");
		itemEnregistrer.addActionListener(new ActionEnregistrerProjet(this.projet));
		itemEnregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));

		JMenuItem itemEnregistrerSous = new JMenuItem("Enregistrer sous");
		itemEnregistrerSous.addActionListener(new ActionEnregistrerProjetSous(this.projet));
		itemEnregistrerSous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.ALT_MASK));

		JMenuItem itemQuitter = new JMenuItem("Quitter");
		itemQuitter.addActionListener(new ActionQuitter(this));
		itemQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

		menuFichier.add(itemNouveau);
		menuFichier.add(itemOuvrir);
		menuFichier.add(itemEnregistrer);
		menuFichier.add(itemEnregistrerSous);
		menuFichier.add(itemQuitter);

		lesMenus.add(menuFichier);

		/* Le deuxième menu : Créer un module vierge */
		JMenu menuCreerModule = new JMenu("Créer un module");

		JMenuItem itemEvenement = new JMenuItem("Evénement");
		itemEvenement.addActionListener(new ActionCreerEvenement(this, this.projet));

		JMenuItem itemLieu = new JMenuItem("Lieu");
		itemLieu.addActionListener(new ActionCreerLieu(this, this.projet));

		JMenuItem itemPersonnage = new JMenuItem("Personnage");
		itemPersonnage.addActionListener(new ActionCreerPersonnage(this, this.projet));

		menuCreerModule.add(itemEvenement);
		menuCreerModule.add(itemLieu);
		menuCreerModule.add(itemPersonnage);

		lesMenus.add(menuCreerModule);

		/* Le troisième menu : Ouvrir un module déjà existant */

		JMenu menuOuvrirModule = new JMenu("Ouvrir un module");
		JMenuItem itemEvenement2 = new JMenuItem("Evénement");
		itemEvenement2.addActionListener(new ActionOuvrirEvenement(this.projet, this));
		JMenuItem itemLieu2 = new JMenuItem("Lieu");
		itemLieu2.addActionListener(new ActionOuvrirLieu(this.projet, this));
		JMenuItem itemPersonnage2 = new JMenuItem("Personnage");
		itemPersonnage2.addActionListener(new ActionOuvrirPersonnage(this.projet, this));

		menuOuvrirModule.add(itemEvenement2);
		menuOuvrirModule.add(itemLieu2);
		menuOuvrirModule.add(itemPersonnage2);

		lesMenus.add(menuOuvrirModule);

		/* Le quatrième menu : Générer/créer une frise */
		JMenuItem itemFriseVierge  = new JMenuItem("Créer une frise vierge");
		itemFriseVierge.addActionListener(new ActionCreerFriseVierge(this, this.projet));

		JMenuItem itemFriseGenerer = new JMenuItem(
				"Générer une frise à partir de tous les événements disponibles"
				);
		itemFriseGenerer.addActionListener(new ActionGenererFrise(this, this.projet));

		JMenuItem itemFriseOuvrir = new JMenuItem(
				"Ouvrir une frise générée"
				);
		itemFriseOuvrir.addActionListener(new ActionOuvrirFrise(this.projet, this));

		JMenu menuFrise = new JMenu("Frise chronologique");
		menuFrise.add(itemFriseVierge);
		menuFrise.add(itemFriseGenerer);
		menuFrise.add(itemFriseOuvrir);

		lesMenus.add(menuFrise);

		/* Enregistrement des menus */
		this.setJMenuBar(lesMenus);

		/* Affichage et gestion de la fermeture de la fenetre */
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Permet d'ajouter une vue de module à la fenetre principale.
	 * @param fenetre La vue à ajouter
	 */
	public void addVueModule(FicheModule fenetre) {
		this.lesFenetresInternes.add(fenetre);

		String type = fenetre.getModule().getType();

		fenetre.toFront();
		fenetre.setVisible(true);

		// si un événement est ajouté à la fenêtre
		if (type.equals(Evenement.TYPE)) {
			// on affiche l'évènement à la position définie
			fenetre.setLocation(new Point(30*frameCountYEven+50*frameCountXEven, 30*frameCountYEven));
			// puis on décale la position du prochain évènement
			if(frameCountYEven++>12){
				frameCountYEven=1;
				frameCountXEven++;
			}

		} else if (type.equals(Lieu.TYPE)) {
			// on affiche le lieu à la position définie
			fenetre.setLocation(new Point(30*frameCountYLieu+50*frameCountXLieu, 30*frameCountYLieu));
			// puis on décale la position du prochain lieu
			if(frameCountYLieu++>12){
				frameCountYLieu=1;
				frameCountXLieu++;
			}

		} else if (type.equals(Personnage.TYPE)) {
			// on affiche le personnage à la position définie
			fenetre.setLocation(new Point(30*frameCountYPers+50*frameCountXPers, 30*frameCountYPers));
			// puis on décale la position du prochain personnage
			if(frameCountYPers++>12){
				frameCountYPers=1;
				frameCountXPers++;
			}

		} else if (type.equals(Frise.TYPE)) {
			// on affiche le personnage à la position définie
			fenetre.setLocation(new Point(30*frameCountYFrise+50*frameCountXFrise, 30*frameCountYFrise));
			// puis on décale la position de la prochaine frise
			if(frameCountYFrise++>12){
				frameCountYFrise=1;
				frameCountXFrise++;
			}
		}
	}

	/**
	 * Renvoie le projet associé à la fenêtre principale
	 * @return le projet associé
	 */
	public Projet getProjet(){
		return this.projet;
	}

	/**
	 * Renvoie les Fenetres internes à la fenetre Principale
	 * @return les fenetres internes
	 */
	public JDesktopPane getFenetresInternes() {
		return this.lesFenetresInternes;
	}

	/**
	 * Fonction de lancement du Manuscrit Manager.
	 * @param args  Arguments de lancement du programme
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// S'il y a un argument, tenter d'ouvrir le fichier
				if (args.length == 1) {
					new FenetrePrincipale(args[0]);

					// Sinon, générer une nouvelle fenetre
				} else {
					new FenetrePrincipale();
				}
			}
		});
	}


}
