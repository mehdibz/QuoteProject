package ca.quotemedia.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ca.quotemedia.dao.QuoteFacade;
import ca.quotemedia.model.Quote;

@Path("/quoteservices")
public class QuoteServices 
{	
	private Quote quote=null;
	private ResponseObject responseCode=null;
	private Gson gson = new Gson();
	int status=0;
	

	@GET
	@Path("/symbols/{SYM}/quotes/latest")		
	@Produces(MediaType.APPLICATION_JSON)
	public Response findQuote(@PathParam("SYM") String id) {
		
	    if(symbolValidate(id,"SYM")) {
	    	
	    	quote = QuoteFacade.getInstance().findQuote(id);

	        if (quote !=null) {
	        	status = 200; //OK
	    		responseCode = new ResponseObject.Builder(quote.getBid(),quote.getAsk()).build();

	        }else {
	        	status = 404; //Unsuccessful: No match found
	        }	
	    }else {
	    	status = 400; //Unsuccessful: Bad Request
	    }
	    
	    
	    String JsonString = gson.toJson(responseCode);
	    return Response.status(status).entity(JsonString).build();
	}
	
	@GET
	@Path("/symbols/{DAY}/asks/highest")		
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSymbolWithHighestAsk(@PathParam("DAY") String id) {

		if(symbolValidate(id,"DAY")) {
	    
			quote = QuoteFacade.getInstance().findSymbolWithHighestAsk(id);
	        if (quote !=null) {
	        	status = 200; //OK
	    		responseCode = new ResponseObject.Builder(quote.getSymbol(),quote.getAsk()).build();

	        }else {
	        	status = 404; //Unsuccessful: No match found
	        }	
	    }else {
	    	status = 400; //Unsuccessful: Bad Request
	    }
	    
	    String JsonString = this.gson.toJson(responseCode);
	    return Response.status(status).entity(JsonString).build();
	}

	
	private boolean symbolValidate(String id,String type) {
		
		if(type.equals("SYM")) {
			if((1<(id.length()))&&((id.length()<7))) {
				return true;
			}
		}
		if(type.equals("DAY")) {
			if(id.length() == 10) {
				return true;
			}
		}
		return false;
	}
}
