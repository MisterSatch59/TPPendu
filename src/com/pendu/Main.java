package com.pendu;

import com.pendu.model.PenduModel;
import com.pendu.observer.Observable;
import com.pendu.vue.Fenetre;

/**
 * <b>Main est la classe permettant de lancer l'application</b>
 * 
 * @author Oltenos
 *
 */
public class Main {

	public static void main(String[] args) {
		Observable model = new PenduModel();
		@SuppressWarnings("unused")
		Fenetre fen =new Fenetre(model);

	}

}
