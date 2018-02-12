package com.pendu.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.pendu.controler.PenduControler;
import com.pendu.observer.Observable;
import com.pendu.observer.Observateur;

public class PanneauJeu extends JPanel implements Observateur {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Modèle associé
	 */
	Observable model;
	/**
	 * Controleur associé
	 */
	PenduControler controler;

	/**
	 * JLabel contenant le mot à chercher les lettres manquantes sont remplacées par
	 * #
	 */
	JLabel motLabel;
	/**
	 * JPanel contenant l'image du pendu
	 */
	JPanel imagePane;
	/**
	 * tableau contenant les boutons avec les lettres
	 */
	JButton bouton[];

	/**
	 * Constructeur
	 * 
	 * @param model
	 *            modèle associé
	 */
	public PanneauJeu(Observable model) {
		super();
		this.model = model;
		controler = new PenduControler(model);
		model.delObservateur();
		model.addObservateur(this);

		this.initPanneau();
	}

	/**
	 * Initialisation du panneau de jeu
	 */
	private void initPanneau() {
		this.removeAll();
		this.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(1, 2));

		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setLayout(new GridLayout(3, 1));

		JTextArea texte = new JTextArea("Nombre de mots trouvés : " + model.getNbMotsTrouves() + "\n\n"
				+ "Votre score actuel est de : " + model.getPoints());
		texte.setFont(new Font("Arial", Font.BOLD, 20));
		texte.setEditable(false);
		JPanel pan1 = new JPanel();
		pan1.add(texte);
		pan1.setBackground(Color.WHITE);
		pan.add(pan1);

		this.motLabel = new JLabel(model.getMotTrouve());
		this.motLabel.setFont(new Font("Arial", Font.BOLD, 40));
		this.motLabel.setForeground(Color.BLUE);
		this.motLabel.setHorizontalAlignment(JLabel.CENTER);
		pan.add(this.motLabel);

		JPanel boutonsPan = new JPanel();
		boutonsPan.setBackground(Color.WHITE);
		char[] listBouton = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		ActionListener boutonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controler.proposer(((JComponent) (e.getSource())).getName().charAt(0));
			}
		};

		bouton = new JButton[26];
		int i = 0;
		for (char c : listBouton) {
			bouton[i] = new JButton(String.valueOf(c));
			bouton[i].setName(String.valueOf(c));
			bouton[i].addActionListener(boutonListener);
			bouton[i].setPreferredSize(new Dimension(50, 30));
			boutonsPan.add(bouton[i]);
			i++;
		}
		pan.add(boutonsPan);
		this.add(pan);

		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("Images/erreur0.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel image = new JLabel(img);

		this.imagePane = new JPanel();
		imagePane.setBackground(Color.WHITE);
		imagePane.setLayout(new BorderLayout());
		this.imagePane.add(image, BorderLayout.CENTER);
		this.add(imagePane);

		this.revalidate();
	}

	/**
	 * Mise à jour de l'Observateur
	 * 
	 * @param motTrouve
	 *            Etat du mot à chercher les lettre manquante sont remplacée par #
	 * @param nbErreur
	 *            Nombre d'erreur
	 * @param lettrePropose
	 *            tableau contenant les lettres déjà proposées
	 */
	public void update(String motTrouve, byte nbErreur, char[] lettrePropose) {
		this.motLabel.setText(motTrouve);
		ImageIcon img = null;
		String nomImage = "Images/erreur" + nbErreur + ".jpg";
		try {
			img = new ImageIcon(ImageIO.read(new File(nomImage)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel image = new JLabel(img);

		imagePane.removeAll();
		imagePane.setBackground(Color.WHITE);
		imagePane.setLayout(new BorderLayout());
		this.imagePane.add(image, BorderLayout.CENTER);
		imagePane.revalidate();

		for (char c : lettrePropose) {
			for (JButton b : bouton) {
				if (b.getName().equalsIgnoreCase(String.valueOf(c))) {
					b.setEnabled(false);
				}
			}
		}
	}

	/**
	 * Mise à jour lorsque la manche est gagné
	 * 
	 * @param mot
	 *            mot qui était à trouver
	 */
	public void updateFinManche(String mot) {

		JOptionPane.showMessageDialog(null, "VICTOIRE! Bravo vous avez trouvé le mot : " + mot, "VICTOIRE",
				JOptionPane.INFORMATION_MESSAGE);

		this.initPanneau();
	}

	/**
	 * Mise à jour lorque la manche est perdu : fin de la partie
	 * 
	 * @param mot
	 *            mot qui était à trouver
	 */
	public String updateFinPartie(boolean estDansLesMeilleursScores, String mot, int points, int nbMotsTrouves) {
		String pseudo = "";
		if (!estDansLesMeilleursScores) {
			JOptionPane.showMessageDialog(null,
					"PERDU! Désolé vous n'avez pas trouvé le mot : " + mot
							+ ". Et vous n'êtes pas dans les meilleurs scores",
					"PERDU", JOptionPane.INFORMATION_MESSAGE);
		} else {
			pseudo = JOptionPane.showInputDialog(null,
					"PERDU! Désolé vous n'avez pas trouvé le mot : " + mot
							+ ".\nMais vous êtes dans les meilleurs scores! Merci d'indiquer votre Peudo!",
					"PERDU", JOptionPane.QUESTION_MESSAGE);
		}

		this.initPanneau();
		return pseudo;
	}

}