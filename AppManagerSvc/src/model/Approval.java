package model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Approval {


	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	/**
	 * Nom
	 */
    @Persistent
	private String nom;
    
    
	/**
	 * Réponse
	 */
    @Persistent
	private String reponse;


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


	public String getReponse() {
		return reponse;
	}


	public void setReponse(String reponse) {
		this.reponse = reponse;
	}


	public Approval(String nom, String reponse) {
		super();
		this.nom = nom;
		this.reponse = reponse;
	}
	
	public Approval() {
	}
    
    

	
}
