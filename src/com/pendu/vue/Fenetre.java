package com.pendu.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.pendu.observer.Observable;

public class Fenetre extends JFrame {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ContentPane de notre fenêtre
	 */
	private JPanel contentPane = new JPanel();
	/**
	 * Modèle associé
	 */
	private Observable model;

	/**
	 * Constructeur de Fenetre.
	 */
	public Fenetre(Observable model) {
		super();
		this.setTitle("Jeu du pendu");
		this.setSize(900, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.model = model;

		contentPane.setLayout(new BorderLayout());

		// Création du menu
		this.initMenu();

		// création du panneau d'affichage
		contentPane = new PanneauAccueil();

		this.setContentPane(contentPane);
		this.setVisible(true);
	}

	/**
	 * Crée le Menu
	 */
	private void initMenu() {
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = ((JComponent) (e.getSource())).getName();
				switch (name) {
				case "Regles":
					contentPane = new PanneauRegles();
					setContentPane(contentPane);
					contentPane.revalidate();
					break;
				case "Nouveau":
					model.initpartie();
					contentPane = new PanneauJeu(model);
					setContentPane(contentPane);
					contentPane.revalidate();
					break;
				case "Scores":
					contentPane = new PanneauScores(model);
					setContentPane(contentPane);
					contentPane.revalidate();
					break;
				case "Quitter":
					System.exit(0);
					break;
				case "?":
					JOptionPane.showMessageDialog(null, "Auteur : OLTENOS\nVersion : 1.0", "INFORMATIONS",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				default:
					System.out.println("Erreur: bouton non reconnue : CLIC de " + name);
					break;
				}
			}

		};
		JMenuBar barreMenu = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		barreMenu.add(fichier);
		JMenuItem nouveau = new JMenuItem("Nouveau");
		nouveau.setName("Nouveau");
		nouveau.addActionListener(menuListener);
		fichier.add(nouveau);
		JMenuItem score = new JMenuItem("Scores");
		score.setName("Scores");
		score.addActionListener(menuListener);
		fichier.add(score);
		fichier.addSeparator();
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.setName("Quitter");
		quitter.addActionListener(menuListener);
		fichier.add(quitter);
		JMenu aPropos = new JMenu("A propos");
		barreMenu.add(aPropos);
		JMenuItem regles = new JMenuItem("Règles");
		regles.setName("Regles");
		regles.addActionListener(menuListener);
		aPropos.add(regles);
		JMenuItem infos = new JMenuItem("?");
		infos.setName("?");
		infos.addActionListener(menuListener);
		aPropos.add(infos);

		this.setJMenuBar(barreMenu);
	}

}
