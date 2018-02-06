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
	 * @param motTrouve
	 *            Etat du mot à chercher les lettre manquante sont remplacée par #
	 * @param nbErreur
	 *            Nombre d'erreur
	 * @param lettrePropose
	 *            tableau contenant les lettres déjà proposées
	 */
	public void update(String motTrouve, byte nbErreur, char lettrePropose[]);

	/**
	 * Mise à jour lorsque la manche est gagné
	 * 
	 * @param mot
	 *            mot qui était à trouver
	 */
	public void updateFinManche(String mot);

	/**
	 * Mise à jour lorque la manche est perdu : fin de la partie
	 * 
	 * @param mot
	 *            mot qui était à trouver
	 */
	public String updateFinPartie(boolean estDansLesMeilleursScores, String mot, int points, int nbMotsTrouves);

}