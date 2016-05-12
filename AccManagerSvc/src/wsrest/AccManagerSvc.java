package wsrest;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	 * 
	 * @param compte
	 * @return Response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("creerCompte")
	public Response addCompte(CompteBancaire compte) 
	{
		try {		
			pm.makePersistent(compte);
			return Response.status(200).entity("Le compte de "+compte.getNom() +" a été crée avec succés ").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).build();
		}
	}
	
	/**
	 * Get le compte avec l'id en paramétre
	 * 
	 * @param idCompte
	 * @return Response Json 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCompte/{idCompte}")
	public Response getCompte(@PathParam("idCompte") String idCompte) 
	{
		try {
			pm = PMF.get().getPersistenceManager();
			CompteBancaire compte = pm.getObjectById(CompteBancaire.class, idCompte);
			
			GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    String retour = gson.toJson(compte);	
			return Response.status(200).entity(retour).build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).build();
		}
	}
	
	/**
	 * Liste tous les comptes bancaires
	 * @return Response Json 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listerCompte")
	public Response getAllComptes()
	{
		javax.jdo.Query q=null;
		try{
			q = pm.newQuery(CompteBancaire.class);
			q.setOrdering("compte asc");
			List<CompteBancaire> res = (List<CompteBancaire>) q.execute();
			GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    String retour = gson.toJson(res);	
		    return Response.status(200).entity(retour).build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).build();
		}	
	}	
	
	/**
	 * Supprime le compte avec l'id en paramétre
	 * 
	 * @param idCompte
	 * @return Response Json 
	 */
	@GET
	@Path("supprimerCompte/{idCompte}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delCompte(@PathParam("idCompte") String idCompte)
	{
		try {
			CompteBancaire cpt = pm.getObjectById(CompteBancaire.class, idCompte);
			pm.deletePersistent(cpt);
			return Response.status(200).entity("Le compte a bien été supprimé.").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).build();
		}
	}
	
	/**
	 * Supprime le compte avec l'id en paramétre à l'aide du verbe HTTP DELETE
	 * 
	 * @param idCompte
	 * @return Response Json 
	 */
	@DELETE
	@Path("supprimerCompte/{idCompte}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delCompteBis(@PathParam("idCompte") String idCompte)
	{
		try {
			CompteBancaire cpt = pm.getObjectById(CompteBancaire.class, idCompte);
			pm.deletePersistent(cpt);
			return Response.status(200).entity("Le compte a bien été supprimé.").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AccManagerSvc a rencontré un probléme :" + e.getMessage()).build();
		}
	}
	
	
	/**
	 * Liste tous les comptes bancaires
	 * @return Response Json 
	 */
	@GET
	@Produces("text/html")	
	@Path("testCreerCompte")
	public Response testCreerCompte()
	{
		try{
			CompteBancaire compte1 = new CompteBancaire("Richard", "Yannis", "CompteAYannis", 2000, "low risk");
			CompteBancaire compte2 = new CompteBancaire("Thuaire", "Clement", "CompteAClement", 2000, "low risk");
			pm.makePersistent(compte1);
			pm.makePersistent(compte2);	
			return Response.status(200).entity("Succés, les comptes de tests ont bien été crée").build();	
		} catch (Exception e) {
			return Response.status(500).entity("Erreur " + e.getMessage()).build();
		}	
	}

}
