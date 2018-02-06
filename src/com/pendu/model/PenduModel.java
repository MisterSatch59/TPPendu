package com.pendu.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.pendu.observer.Observable;
import com.pendu.observer.Observateur;

/**
 * <b>Modèle de l'application pendu</b>
 * <p>
 * Implemente Observable du Pattern Observer
 * </p>
 * 
 * @author Oltenos
 *
 */
public class PenduModel implements Observable {

	/**
	 * liste des observateur du modèle
	 */
	private List<Observateur> listObs = new ArrayList<Observateur>();

	/**
	 * nombre d'erreur maximum autorisé
	 */
	private byte nbErreurMax = 8;
	/**
	 * tableau du score en fonction de nombre d'erreur
	 */
	private int score[] = { 100, 50, 35, 25, 15, 10, 5, 0 };

	/**
	 * Tableau des meilleurs scores
	 */
	private String tableauScore[][] = new String[3][10];

	/**
	 * Nombre d'erreur de la manche en cours
	 */
	private byte nbErreur;
	/**
	 * mot à trouver
	 */
	private String mot;
	/**
	 * mot déjà trouvé (les lettre manquante sont remplacé #)
	 */
	private String motTrouve;
	/**
	 * lettres déjà proposées
	 */
	private char lettrePropose[];

	/**
	 * points de la partie
	 */
	private int points;
	/**
	 * Nombre de mot trouvé de la partie
	 */
	private int nbMotsTrouves;

	private String pseudo = "";

	/**
	 * Constructeur
	 */
	public PenduModel() {
		initTableauScore();
		initpartie();
	}

	/**
	 * Charge le tableau de score enregistré ou crée le tableau vide si aucun
	 * enregistrement
	 */
	private void initTableauScore() {
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br = null;
		try {
			ips = new FileInputStream("meilleursScore.txt");
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);

			for (int i = 0; i < 10; i++) {
				tableauScore[0][i] = br.readLine();
				tableauScore[1][i] = br.readLine();
				tableauScore[2][i] = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			for (int i = 0; i < 10; i++) {
				tableauScore[0][i] = "";
				tableauScore[1][i] = "0";
				tableauScore[2][i] = "0";
			}
		}

		System.out.println("tableau de score :");
		for (int i = 0; i < 10; i++) {
			System.out.println(tableauScore[0][i] + " - " + tableauScore[1][i] + " - " + tableauScore[2][i]);
		}

	}

	/**
	 * Initialise une partie
	 */
	private void initpartie() {
		this.points = 0;
		this.nbMotsTrouves = 0;
		initmanche();
	}

	/**
	 * Initialise une manche (sans remise à zéro du score et du nombre de mots
	 * trouvés)
	 */
	private void initmanche() {
		this.nbErreur = 0;
		this.lettrePropose = new char[0];
		choisirMot();
		this.motTrouve = "";
		for (int i = 0; i < this.mot.length(); i++) {
			this.motTrouve = new String(this.motTrouve + "#");
		}
	}

	/**
	 * Choix aléatoire du mot
	 */
	private void choisirMot() {
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br = null;
		int nbre = (int) (Math.random() * 336531);
		try {
			ips = new FileInputStream("dictionnaire.txt");
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			for (int i = 0; i < nbre; i++) {
				br.readLine();
			}
			mot = (br.readLine()).toUpperCase();
			System.out.println("Mot : " + mot);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Proposer une lettre
	 * 
	 * @param lettre
	 *            lettre proposé
	 */
	public void proposerLettre(char lettre) {
		CharSequence l = "" + lettre;
		if (mot.contains(l)) {
			String nouveauMotTrouve = "";
			for (int i = 0; i < mot.length(); i++) {
				if (mot.charAt(i) == lettre) {
					nouveauMotTrouve += lettre;
				} else if (motTrouve.charAt(i) != '#') {
					nouveauMotTrouve += motTrouve.charAt(i);
				} else {
					nouveauMotTrouve += "#";
				}
			}
			motTrouve = nouveauMotTrouve;
			System.out.println(lettre + " est une bonne lettre, le mot est : " + motTrouve);
		} else {
			nbErreur++;
			if (nbErreur == nbErreurMax) {
				finPartie();
			}
			System.out.println(lettre + " est une mauvaise lettre, le mot est : " + motTrouve);
		}

		if (mot.equalsIgnoreCase(motTrouve)) {
			finManche();
		}

		this.updateObservateur();
	}

	/**
	 * Actions lorsque le mot a été trouvé
	 */
	private void finManche() {
		System.out.println("fin de manche");
		this.nbMotsTrouves++;
		points += score[this.nbErreur];

		System.out.println("Points : " + this.points);
		this.initmanche();
	}

	/**
	 * Actions lorsque le mot n'a pas été trouvé
	 */
	private void finPartie() {
		System.out.println("fin de la partie");
		if (this.points > Integer.parseInt(this.tableauScore[1][9])) {
			System.out.println("Ajouter le joueur dans le tableau");
			String newTableauScore[][] = new String[3][10];
			boolean in = false;
			int i = 0;
			while (!in) {
				System.out.println("in = " + in);

				if (this.points > Integer.parseInt(this.tableauScore[1][i])) {
					newTableauScore[0][i] = this.pseudo;
					newTableauScore[1][i] = "" + this.points;
					newTableauScore[2][i] = "" + this.nbMotsTrouves;
					in = true;
				} else {
					newTableauScore[0][i] = tableauScore[0][i];
					newTableauScore[1][i] = tableauScore[1][i];
					newTableauScore[2][i] = tableauScore[2][i];
				}
				i++;
			}
			for (int j = i; j < 10; j++) {
				newTableauScore[0][j] = tableauScore[0][j - 1];
				newTableauScore[1][j] = tableauScore[1][j - 1];
				newTableauScore[2][j] = tableauScore[2][j - 1];
				System.out.println("2e boucle");
			}
			tableauScore = newTableauScore;
			System.out.println("Nouveau tableau de score :");
			for (int k = 0; k < 10; k++) {
				System.out.println(tableauScore[0][k] + " - " + tableauScore[1][k] + " - " + tableauScore[2][k]);
			}
			enregistrerScore();
		}
		this.initpartie();
	}

	/**
	 * Enregistre le tableau de score dans meilleursScore.txt
	 */
	private void enregistrerScore() {
		String enter = System.getProperty("line.separator");
		String enr = "";
		for (int i = 0; i < 10; i++) {
			enr += tableauScore[0][i];
			enr += enter;
			enr += tableauScore[1][i];
			enr += enter;
			enr += tableauScore[2][i];
			enr += enter;
		}
		FileWriter fw;
		BufferedWriter output = null;
		try {
			fw = new FileWriter("meilleursScore.txt", false);
			output = new BufferedWriter(fw);

			output.write(enr);
		} catch (IOException ioe) {
			System.out.println("Error while saving data");
		} finally {
			try {
				output.flush();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String[][] getTableauScore() {
		return tableauScore;
	}

	public byte getNbErreur() {
		return nbErreur;
	}

	public String getMotTrouve() {
		return motTrouve;
	}

	public char[] getLettrePropose() {
		return lettrePropose;
	}

	public int getPoints() {
		return points;
	}

	public int getNbMotsTrouves() {
		return nbMotsTrouves;
	}

	/**
	 * Ajout d'un observateur
	 */
	public void addObservateur(Observateur obs) {
		this.listObs.add(obs);
	}

	/**
	 * supprimer un observateur
	 */
	public void delObservateur() {
		this.listObs = new ArrayList<Observateur>();
	}

	/**
	 * Mise à jour des observateurs
	 */
	public void updateObservateur() {
		for (Observateur obs : this.listObs)
			obs.update();
	}
}
