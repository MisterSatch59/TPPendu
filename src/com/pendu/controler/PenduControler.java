package com.pendu.controler;

import com.pendu.observer.Observable;

public class PenduControler {

	/**
	 * Modèle associé
	 */
	Observable model;

	/**
	 * Constructeur
	 * 
	 * @param model
	 *            model associé
	 */
	public PenduControler(Observable model) {
		this.model = model;
	}

	/**
	 * proposer un caractére
	 * 
	 * @param c
	 *            caractére proposé
	 */
	public void proposer(char c) {
		char[] charAcceptes = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		for (char cha : charAcceptes) {
			if (cha == c) {
				model.proposerLettre(c);
			}
		}
	}
}
