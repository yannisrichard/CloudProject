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

import model.Approval;

@Path("/Approval")
public class AppManagerSvc {
		
	/**
	 * Manager Persistence
	 */
	private PersistenceManager pm = PMF.get().getPersistenceManager();
	
	/**
	 * Ajoute un approval
	 * @param nom
	 * @param reponse
	 * @return Response text
	 */
	@POST
	@Path("creerApproval")
	@Produces("text/plain")
	public Response addApproval(@DefaultValue("") @QueryParam("nom") String nom, @DefaultValue("") @QueryParam("reponse") String reponse)
	{
		try {	
			Approval nouveauApproval = new Approval(nom, reponse);
	        Key key = KeyFactory.createKey(Approval.class.getSimpleName(), nouveauApproval.getNom());
			nouveauApproval.setKey(key);
			pm.makePersistent(nouveauApproval);
			return Response.status(200).entity("Le statut de l'approbation de "+nouveauApproval.getNom() +" a été enregistré avec succés ").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AppManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	
	/**
	 * Liste tous les approvals
	 * @return Response text 
	 */
	@GET
	@Produces("text/plain")
	@Path("listerApproval")
	public Response getAllApproval()
	{
		javax.jdo.Query q=null;
		try{
			q = pm.newQuery(Approval.class);
			q.setOrdering("nom asc");
			List<Approval> res = (List<Approval>) q.execute();
			String retour = "";
		    if (!res.isEmpty()) {
				GsonBuilder builder = new GsonBuilder();
			    Gson gson = builder.create();
			    retour = gson.toJson(res);	
	    	}
		    else {
				retour="La liste d'approvals est vide";
			}
		    return Response.status(200).entity(retour).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AppManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}	
	}	
	
	/**
	 * Get l'approval avec l'id (=nom) en paramètre
	 * 
	 * @param idApproval
	 * @return Response text 
	 */
	@GET
	@Produces("text/plain")
	@Path("getApproval/{idApproval}")
	public Response getApproval(@PathParam("idApproval") String idApproval) 
	{
		try {
		    Key k = KeyFactory.createKey(Approval.class.getSimpleName(), idApproval);
		    Approval approval = pm.getObjectById(Approval.class, k);
			
			GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    String retour = gson.toJson(approval);	
			return Response.status(200).entity(retour).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AppManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	/**
	 * Supprime l'approval avec l'id (=nom) en paramétre
	 * 
	 * @param idApproval
	 * @return Response text 
	 */
	@GET
	@Path("supprimerApproval/{idApproval}")
	@Produces("text/plain")
	public Response delApproval(@PathParam("idApproval") String idApproval)
	{
		try {
		    Key k = KeyFactory.createKey(Approval.class.getSimpleName(), idApproval);
		    Approval approval = pm.getObjectById(Approval.class, k);
		    
			pm.deletePersistent(approval);
			return Response.status(200).entity("L'approval a bien été supprimé.").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AppManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	/**
	 * Supprime l'approval avec l'id (=nom) en paramétre à l'aide du verbe HTTP DELETE
	 * 
	 * @param idApproval
	 * @return Response text 
	 */
	@DELETE
	@Path("supprimerApproval/{idApproval}")
	@Produces("text/plain")
	public Response delApprovalBis(@PathParam("idApproval") String idApproval)
	{
		try {
		    Key k = KeyFactory.createKey(Approval.class.getSimpleName(), idApproval);
		    Approval approval = pm.getObjectById(Approval.class, k);

		    pm.deletePersistent(approval);
			return Response.status(200).entity("L'approval a bien été supprimé.").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(500).entity("Le service AppManagerSvc a rencontré un probléme :" + e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
}
