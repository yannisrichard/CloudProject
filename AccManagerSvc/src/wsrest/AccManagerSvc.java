package wsrest;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.CompteBancaire;

@Path("/CompteBancaire")
public class AccManagerSvc {

	
	/**
	 * Manager Persistence
	 */
	private PersistenceManager pm = PMF.get().getPersistenceManager();
	
	
	/**
	 * Ajoute un compte
	 * @param nom
	 * @param prenom
	 * @param compte
	 * @param montant
	 * @return Response text
	 */
	@POST
	@Path("creerCompte")
	@Produces("text/plain")
	public Response addCompte(@DefaultValue("") @QueryParam("nom") String nom, @DefaultValue("") @QueryParam("prenom") String prenom
			, @DefaultValue("") @QueryParam("compte") String compte, @DefaultValue("0") @QueryParam("montant") double montant)
	{
		try {	
			CompteBancaire nouveauCompte = new CompteBancaire(nom, prenom, compte, montant, "");
	        Key key = KeyFactory.createKey(CompteBancaire.class.getSimpleName(), nouveauCompte.getCompte()+"-"+nouveauCompte.getNom());
			nouveauCompte.setKey(key);
			pm.makePersistent(nouveauCompte);
			return Response.status(200).entity("Le compte de "+nouveauCompte.getNom() +" a été crée avec succés ").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	/**
	 * Get le compte avec l'id en paramétre
	 * 
	 * @param idCompte
	 * @return Response text 
	 */
	@GET
	@Produces("text/plain")
	@Path("getCompte/{idCompte}")
	public Response getCompte(@PathParam("idCompte") String idCompte) 
	{
		try {
		    Key k = KeyFactory.createKey(CompteBancaire.class.getSimpleName(), idCompte);
		    CompteBancaire compte = pm.getObjectById(CompteBancaire.class, k);
			
			GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    String retour = gson.toJson(compte);	
			return Response.status(200).entity(retour).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	/**
	 * Liste tous les comptes bancaires
	 * @return Response text 
	 */
	@GET
	@Produces("text/plain")
	@Path("listerCompte")
	public Response getAllComptes()
	{
		javax.jdo.Query q=null;
		try{
			q = pm.newQuery(CompteBancaire.class);
			q.setOrdering("compte asc");
			List<CompteBancaire> res = (List<CompteBancaire>) q.execute();
			String retour = "";
		    if (!res.isEmpty()) {
				GsonBuilder builder = new GsonBuilder();
			    Gson gson = builder.create();
			    retour = gson.toJson(res);	
	    	}
		    else {
				retour="La liste de compte est vide";
				return Response.status(204).entity(retour).build();
			}
		    return Response.status(200).entity(retour).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}	
	}	
	
	/**
	 * Supprime le compte avec l'id en paramétre
	 * 
	 * @param idCompte
	 * @return Response text 
	 */
	@GET
	@Path("supprimerCompte/{idCompte}")
	@Produces("text/plain")
	public Response delCompte(@PathParam("idCompte") String idCompte)
	{
		try {
		    Key k = KeyFactory.createKey(CompteBancaire.class.getSimpleName(), idCompte);
		    CompteBancaire cpt = pm.getObjectById(CompteBancaire.class, k);
		    
			pm.deletePersistent(cpt);
			return Response.status(200).entity("Le compte a bien été supprimé.").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	/**
	 * Supprime le compte avec l'id en paramétre à l'aide du verbe HTTP DELETE
	 * 
	 * @param idCompte
	 * @return Response text 
	 */
	@DELETE
	@Path("supprimerCompte/{idCompte}")
	@Produces("text/plain")
	public Response delCompteBis(@PathParam("idCompte") String idCompte)
	{
		try {
		    Key k = KeyFactory.createKey(CompteBancaire.class.getSimpleName(), idCompte);
		    CompteBancaire cpt = pm.getObjectById(CompteBancaire.class, k);

		    pm.deletePersistent(cpt);
			return Response.status(200).entity("Le compte a bien été supprimé.").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	


}
