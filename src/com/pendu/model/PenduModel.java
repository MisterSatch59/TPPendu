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
	private byte nbErreurMax = 7;
	/**
	 * tableau des score en fonction de nombre d'erreur
	 */
	private int score[] = { 100, 50, 35, 25, 15, 10, 5 };

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

	/**
	 * Constructeur
	 */
	public PenduModel() {
		initTableauScore();
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
	}

	/**
	 * Initialise une partie
	 */
	public void initpartie() {
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
			if (this.mot.charAt(i) == '-') {
				this.motTrouve = new String(this.motTrouve + "-");
			} else {
				this.motTrouve = new String(this.motTrouve + "#");
			}
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
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(mot);
	}

	/**
	 * Proposer une lettre
	 * 
	 * @param lettre
	 *            lettre proposé
	 */
	public void proposerLettre(char lettre) {

		switch (lettre) {
		case 'A':
			CharSequence l1[] = { "A", "à".toUpperCase(), "Ä", "Â" };
			this.testLettre(l1);
			break;
		case 'E':
			CharSequence l2[] = { "E", "é".toUpperCase(), "è".toUpperCase(), "Ë", "Ê" };
			this.testLettre(l2);
			break;
		case 'I':
			CharSequence l3[] = { "I", "Ï", "Î" };
			this.testLettre(l3);
			break;
		case 'O':
			CharSequence l4[] = { "O", "Ö", "Ô" };
			this.testLettre(l4);
			break;
		case 'U':
			CharSequence l5[] = { "U", "ù".toUpperCase(), "Ü", "Û" };
			this.testLettre(l5);
			break;
		case 'C':
			CharSequence l6[] = { "C", "ç".toUpperCase() };
			this.testLettre(l6);
			break;
		default:
			CharSequence l7[] = { "" + lettre };
			this.testLettre(l7);
			break;
		}

		if (mot.equalsIgnoreCase(motTrouve)) {
			finManche();
		} else {
			if (nbErreur == nbErreurMax) {
				finPartie();
			} else {
				char lettrePropose2[] = new char[lettrePropose.length + 1];
				for (int i = 0; i < lettrePropose.length; i++) {
					lettrePropose2[i] = lettrePropose[i];
				}
				lettrePropose2[lettrePropose2.length - 1] = lettre;
				lettrePropose = lettrePropose2;

				this.updateObservateur();
			}

		}
	}

	private void testLettre(CharSequence l[]) {
		boolean contien = false;
		for (CharSequence c : l) {
			if (mot.contains(c)) {
				contien = true;
				String nouveauMotTrouve = "";
				for (int i = 0; i < mot.length(); i++) {
					if (mot.charAt(i) == c.charAt(0)) {
						nouveauMotTrouve += c.charAt(0);
					} else {
						nouveauMotTrouve += motTrouve.charAt(i);
					}
				}
				motTrouve = nouveauMotTrouve;
			}
		}

		if (!contien) {
			nbErreur++;
		}

	}

	/**
	 * Actions lorsque le mot a été trouvé
	 */
	private void finManche() {
		this.nbMotsTrouves++;
		points += score[this.nbErreur];

		String m = mot;
		this.updateObservateur();

		this.initmanche();

		for (Observateur obs : this.listObs)
			obs.updateFinManche(m);
	}

	/**
	 * Actions lorsque le mot n'a pas été trouvé
	 */
	private void finPartie() {
		String m = this.mot;
		int p = this.points;
		int n = this.nbMotsTrouves;
		this.updateObservateur();

		this.initpartie();

		if (p > Integer.parseInt(this.tableauScore[1][9])) {
			String pseudo = "";
			for (Observateur obs : this.listObs) {
				pseudo = obs.updateFinPartie(true, m, p, n);
			}

			String newTableauScore[][] = new String[3][10];
			boolean in = false;
			int i = 0;
			while (!in) {
				if (p > Integer.parseInt(this.tableauScore[1][i])) {
					newTableauScore[0][i] = pseudo;
					newTableauScore[1][i] = "" + p;
					newTableauScore[2][i] = "" + n;
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
			}
			tableauScore = newTableauScore;

			enregistrerScore();
		} else {
			for (Observateur obs : this.listObs) {
				obs.updateFinPartie(false, m, p, n);
			}
		}
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

	/**
	 * Retourne le tableau des meilleurs scores
	 * 
	 * @return tableau des meilleurs scores
	 */
	public String[][] getTableauScore() {
		return tableauScore;
	}

	/**
	 * Retourne le nombre de mot trouvé de la partie en cours
	 * 
	 * @return nombre de mot trouvé de la partie en cours
	 */
	public String getMotTrouve() {
		return motTrouve;
	}

	/**
	 * Retourne le score de la partie en cours
	 * 
	 * @return score de la partie en cours
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Retourne le nombre de mot trouvé de la partie en cours
	 * 
	 * @return nombre de mot trouvé de la partie en cours
	 */
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
			obs.update(motTrouve, nbErreur, lettrePropose);
	}

}
