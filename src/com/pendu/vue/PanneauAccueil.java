package com.pendu.vue;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanneauAccueil extends JPanel {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Création du panneau d'accueil
	 */
	public PanneauAccueil() {
		this.setBackground(Color.WHITE);
		JLabel titre = new JLabel("Bienvenu dans le jeu du pendu!");
		titre.setFont(new Font("Arial", Font.BOLD, 40));
		this.add(titre);

		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("Images/perdu.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel image = new JLabel(img);
		this.add(image);

		JTextArea texte = new JTextArea(
				"Vous avez sept coups pour trouver le mot caché. Si vous réussissez, on recommence !\n"
						+ "Plus vous trouvez de mots, plus votre score augmente. Alors, à vous de jouer !\n\n"
						+ "Proverbe :\tPas vu, pas pris !\n" + "\tPris ! PENDU !");
		texte.setFont(new Font("Arial", 0, 18));
		texte.setEditable(false);

		this.add(texte);
	}
}
