package com.pendu.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.pendu.observer.Observable;

public class PanneauScores extends JPanel {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Création du panneau de scores
	 * 
	 * @param model
	 */
	public PanneauScores(Observable model) {
		super();
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		JLabel titre = new JLabel("Meilleurs scores");
		titre.setFont(new Font("Arial", Font.BOLD, 40));
		titre.setHorizontalAlignment(JLabel.CENTER);
		this.add(titre, BorderLayout.NORTH);

		JPanel pan = new JPanel();
		pan.setBackground(Color.white);
		pan.setLayout(new GridLayout(11, 3));
		JLabel jL1 = new JLabel("PSEUDO");
		jL1.setHorizontalAlignment(JLabel.CENTER);
		pan.add(jL1);
		JLabel jL2 = new JLabel("SCORE");
		jL2.setHorizontalAlignment(JLabel.CENTER);
		pan.add(jL2);
		JLabel jL3 = new JLabel("NOMBRE DE MOTS TROUVEES");
		jL3.setHorizontalAlignment(JLabel.CENTER);
		pan.add(jL3);
		
		
		String[][] tab = model.getTableauScore();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 3; j++) {
				JLabel jL = new JLabel(tab[j][i]);
				jL.setHorizontalAlignment(JLabel.CENTER);
				pan.add(jL);
			}
		}

		this.add(pan, BorderLayout.CENTER);
	}
}