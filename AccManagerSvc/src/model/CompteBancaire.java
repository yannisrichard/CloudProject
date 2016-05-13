package model;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class CompteBancaire {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	/**
	 * Nom du client
	 */
    @Persistent
	private String nom;
	
	/**
	 * Prénom du client
	 */
    @Persistent
	private String prenom;
	
	/**
	 * Compte du Client
	 */
    @Persistent
	private String compte;
	
	/**
	 * Montant du compte
	 */
    @Persistent
	private double montant;
	
	/**
	 * Risque du transfert montant du compte
	 */
    @Persistent
	private String risque;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getRisque() {
		return risque;
	}

	public void setRisque(String risque) {
		this.risque = risque;
	}

	public CompteBancaire(String nom, String prenom, String compte, double montant, String risque) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.compte = compte;
		this.montant = montant;
		this.risque = risque;
	}
	
	public CompteBancaire() {
	}
}
