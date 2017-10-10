package modele;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import modele.notification.Notification;
import modele.notification.TypeNotification;
import vue.FicheModule;

/**
 * Classe permettant de gérer un projet, ie un ensemble de modules.
 *
 * @author      G14
 * @version     1.0
 */
public class Projet extends Observable implements Observer {

	/** Modules du projet. */
	private List<Module> modules;

	/** Fichier d'enregistrement du projet. */
	private String fichierEnregistrement;

	/**
	 * Création d'un nouveau projet.
	 */
	public Projet() {
		// Création d'une liste de module vide
		this.modules = new ArrayList<Module>();

		// Indication qu'il s'agit d'un nouveau fichier
		this.fichierEnregistrement = null;
	}

	/**
	 * Création d'un projet à partir d'un fichier de sauvegarde.
	 *
	 * @param cheminFichierSauvegarde Chemin du fichier de sauvegarde
	 * @throws FileNotFoundException si le chemin du fichier de sauvegarde
	 *                               pointe sur un fichier inexistant
	 */
	public Projet(String cheminFichierSauvegarde) throws FileNotFoundException {
		this.charger(cheminFichierSauvegarde);
	}

	/**
	 * Permet de charger un projet depuis un fichier de sauvegarde.
	 *
	 * @param cheminFichierSauvegarde Chemin du fichier de sauvegarde
	 * @throws FileNotFoundException si le chemin du fichier de sauvegarde est inexistant
	 */
	public void charger(String cheminFichierSauvegarde) throws FileNotFoundException {
		// Sauvegarde du chemin d'enregistrement
		this.fichierEnregistrement = cheminFichierSauvegarde;

		// Ouverture du fichier et décodage
		XMLDecoder decoder = new XMLDecoder(
				new BufferedInputStream(
						new FileInputStream(cheminFichierSauvegarde)));

		// Lecture des enregistrements
		this.modules = (ArrayList<Module>) decoder.readObject();

		// Parcourir les modules et indiquer le projet
		for (Module module : this.modules) {
			// Enregistrer le projet
			module.setProjetParent(this);

			// Les modules observent le projet
			this.addObserver(module);
		}

		// Fermeture du fichier
		decoder.close();
	}

	/**
	 * Permet d'enregistrer l'état actuel du projet dans le fichier
	 * dont le chemin d'accès est passé en paramètre.
	 *
	 * @param fichierEnregistrement Nouveau chemin d'enregistrement du projet
	 */
	public void enregistrerSous(String fichierEnregistrement)
			throws FileNotFoundException {

		// Enregistrement du nouveau chemin d'accès
		this.fichierEnregistrement = fichierEnregistrement;

		// Sauvegarde
		this.enregistrer();
	}

	/**
	 * Permet d'enregistrer l'état actuel du projet dans le fichier
	 * dont le chemin est indiqué dans fichierEnregistrement.
	 *
	 * Pré-condition :  on a déjà au moins une fois enregistré sous le fichier,
	 *                  sinon, NullPointerException est levée.
	 *
	 * @throws FileNotFoundException si le fichier n'existe pas
	 */
	public void enregistrer() throws FileNotFoundException {
		// Ouverture du fichier de sauvegarde
		XMLEncoder encoder = new XMLEncoder(
				new BufferedOutputStream(
						new FileOutputStream(this.fichierEnregistrement)));

		// Indiquer de ne pas sauvegarder :
		//  - le projet associé aux modules (qui est le projet courant)
		//  - la vue associée (qui peut etre générée facilement)
		try {
			//      1. Récupèrer le BeanInfo de la classe Module
			BeanInfo info = Introspector.getBeanInfo(Module.class);

			//      2. Récupèrer les PropertyDescriptors de la classe
			//         Module via le BeanInfo
			PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();

			//      3. Les parcourir jusqu'à trouver les attributs pour
			//         lesquels indiquer le non enregistrement
			for (PropertyDescriptor descriptor : propertyDescriptors) {

				//  4. Mettre la propriété "transient" à vrai pour le
				//     PropertyDescriptor de l'attribut "ficheModuleLiee"
				if (descriptor.getName().equals("ficheModuleLiee")) {
					descriptor.setValue("transient", Boolean.TRUE);

					//  4. Mettre la propriété "transient" à vrai pour le
					//     PropertyDescriptor de l'attribut "projetParent"
				} else if (descriptor.getName().equals("projetParent")) {
					descriptor.setValue("transient", Boolean.TRUE);
				}
			}

			// Sauvegarde des informations
			encoder.writeObject(this.modules);
		} catch (IntrospectionException e) {
			System.out.println("Erreur lors de l'enregistrement : " + e.getMessage());
		}

		// Fermeture du fichier
		encoder.close();
	}

	/**
	 * Permet de récupérer la liste des fiches associées à tous les modules du projet
	 * @return la liste de toutes les fiches des modules du projet
	 */

	public List<FicheModule> getFichesModules(){

		List<FicheModule> listeFiches = new ArrayList<FicheModule>();
		for (Module mod : this.modules){
			listeFiches.add(mod.getFiche());
		}
		return listeFiches;
	}

	/**
	 * Ajouter un module à la liste des modules.
	 * @param nouveauModule module à ajouter à la liste
	 */
	public void ajouter(Module nouveauModule) {
		// Ajouter le module
		this.modules.add(nouveauModule);

		// Lancer l'écoute du module
		this.addObserver(nouveauModule);

		// Indiquer que le projet est modifié
		this.setChanged();

		// Notifier de la modification
		this.notifyObservers(
				new Notification(null, TypeNotification.AJOUT, nouveauModule)
				);
	
	}

	/**
	 * Supprimer un module de la liste des modules.
	 * @param module module à supprimer
	 */
	public void supprimer(Module module) {
		// Suppression du module
		this.modules.remove(module);

		// Arret de l'écoute du module
		this.deleteObserver(module);

		// Indiquer que le projet est modifié
		this.setChanged();

		// Notifier de la modification
		this.notifyObservers(
				new Notification(module, TypeNotification.SUPPRESSION, null)
				);
	}

	/**
	 * Renvoie un booléen indiquant la présence du module de la liste.
	 * @param module le module recherché
	 * @return booléen
	 */
	public boolean contient(Module module) {
		return this.modules.contains(module);
	}

	/**
	 * Renvoie un module à partir de son ID.
	 * @param id identifiant du module
	 * @return Le module associé 
	 */
	public Module recupererModule(int id) {
		return this.modules.get(id);
	}

	/**
	 * Renvoie une restriction de la liste par type.
	 * @param type Type de module
	 * @return La liste des modules de ce type
	 */
	public List<Module> getListeFiltree(String type) {
		List<Module> liste = new ArrayList<Module>();		
		for (Module mod : this.modules) {
			if (mod.getType().equals(type)) {
				liste.add(mod);
			}
		}
		return liste;
	}

	/** 
	 * Permet de récupérer la liste des noms des modules d'un type du projet.
	 * @param type Type de module souhaité
	 * @return La liste de tous les noms des modules du projet
	 */
	public List<String> getListeNom(String type) {
		List<String> listeNom = new ArrayList<String>();
		for (Module mod : this.getListeFiltree(type)) {
			listeNom.add(mod.getNom());
		}
		return listeNom;
	}

	/**
	 * Permet de récupérer la liste des modules d'un certain type.
	 * @param typeAttendu Type recherché, à lister.
	 * @return La liste des modules du type recherché.
	 */
	public Map<Module, Integer> getListeModules(String typeAttendu) {
		Map<Module, Integer> liste = new HashMap<Module, Integer>();

		int cpt = 0;
		for (Module mod : modules) {
			if (typeAttendu.equals(mod.getType())) {
				if (! mod.getFiche().isVisible()){
					liste.put(mod, cpt);					
				}
			}
			cpt++;
		}

		return liste;
	}

	/**
	 * Mise à jour du projet.
	 * @param observable    Elément modifié (Module)
	 * @param arguments     Notification associée à la modification (Notification)
	 */
	@Override
	public void update(Observable observable, Object arguments) {
		// Si la notification est valide, la transmettre
		if (observable instanceof Module && arguments instanceof Notification) {
			this.setChanged();

			// Lorsqu'un module est modifié, signaler les observateurs du projet
			this.notifyObservers(arguments);
		}
	}
}
