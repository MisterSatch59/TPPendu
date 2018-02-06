package com.pendu;

import java.util.Scanner;

import com.pendu.model.PenduModel;

/**
 * <b>Main est la classe permettant de lancer l'application</b>
 * 
 * @author Oltenos
 *
 */
public class Main {

	public static void main(String[] args) {
		PenduModel model = new PenduModel();

		Scanner sc = new Scanner(System.in);
		char lettre = 'A';

		while (lettre!='é') {

			lettre = (sc.nextLine()).charAt(0);
			model.proposerLettre(lettre);
		}
		
		sc.close();

	}

}
