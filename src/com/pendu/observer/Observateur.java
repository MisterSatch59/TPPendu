package com.pendu.observer;

/**
 * <b>Interface du Patern Observer pour attribuer le comportement
 * Observateur</b>
 * 
 * @author Oltenos
 * @version 1
 */
public interface Observateur {

	/**
	 * Mise à jour de l'Observateur
	 * 
	 * @param dessin
	 *            liste des points constituant le dessin
	 */
	public void update();
}
