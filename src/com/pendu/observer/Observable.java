package com.pendu.observer;

/**
 * <b>Interface du Patern Observer pour attribuer le comportement Observable</b>
 * 
 * @author Oltenos
 * @version 1
 */
public interface Observable {

	/**
	 * Remise à zéro de la partie
	 */
	public void initpartie();

	/**
	 * Proposer une lettre
	 * 
	 * @param lettre
	 *            lettre proposé
	 */
	public void proposerLettre(char lettre);
	
	/**
	 * Retourne l'état du mot trouvé (les lettres non trouvé sont remplacé par #
	 * @return état du mot trouvé
	 */
	public String getMotTrouve();
	
	/**
	 * Retourne le score de la partie en cours
	 * @return score de la partie en cours
	 */
	public int getPoints();
	
	/**
	 * Retourne le nombre de mot trouvé de la partie en cours
	 * @return nombre de mot trouvé de la partie en cours
	 */
	public int getNbMotsTrouves();
	
	/**
	 * Retourne le tableau des meilleurs scores
	 * @return tableau des meilleurs scores
	 */
	public String[][] getTableauScore();

	/**
	 * Ajoute un observateur
	 * 
	 * @param obs
	 *            Observateur
	 * @see Observateur
	 */
	public void addObservateur(Observateur obs);

	/**
	 * Mise à jour des Observateur
	 * 
	 * @see Observateur
	 */
	public void updateObservateur();

	/**
	 * Supprimer les Observateur
	 * 
	 * @see Observateur
	 */
	public void delObservateur();
}
