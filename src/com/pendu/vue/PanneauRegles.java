package com.pendu.vue;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanneauRegles extends JPanel{
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Création du panneau de règles
	 */
	public PanneauRegles() {
		this.setBackground(Color.WHITE);
		
		JLabel titre = new JLabel("Bienvenu dans le jeu du pendu!");
		titre.setFont(new Font("Arial", Font.BOLD, 40));
		this.add(titre);

		JTextArea texte = new JTextArea("\n\n\nVous avez sept coups pour trouver le mot caché. Si vous réussissez, on recommence !\n" +
				"Plus vous trouvez de mots, plus votre score augmente. Alors, à vous de jouer !\n" +
				"\n\nCOMPTE DES POINTS :\n\n"
				+ "\tMot trouvé sans erreur - 100 pts\n"
				+ "\tMot trouvé avec une erreur - 50 pts\n"
				+ "\tMot trouvé avec deux erreurs - 35 pts\n"
				+ "\tMot trouvé avec trois erreurs - 25 pts\n"
				+ "\tMot trouvé avec quatre erreurs - 15 pts\n"
				+ "\tMot trouvé avec cinq erreurs - 10 pts\n"
				+ "\tMot trouvé avec six erreurs - 5 pts\n\n\n"
				+ "Je vous souhaite bien du plaisir !\n"
				+ "Si vous pensez pouvoir trouver un mot en un seul essai, c'est que vous croyez que le dictionnaire est petit.\n" +
				"Or, pour votre information, il contient plus de 336 000 mots Bonne chance ! ;)");

		texte.setFont(new Font("Arial", 0, 18));
		texte.setEditable(false);;
		this.add(texte);
	}
}
