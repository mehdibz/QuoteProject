package quoteMedia;

import org.junit.Test;

import com.google.gson.Gson;

import ca.quotemedia.dao.DBConnection;
import ca.quotemedia.dao.QuoteFacade;
import ca.quotemedia.services.QuoteServices;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("unused")
public class TestJunitEndpoints {
   
 
   /*
    * Status test
    */
   @Test
   public void testUrls() {
	  
	  assertEquals(new QuoteServices().findQuote("MSFT").getStatus(), 200);
	  assertEquals(new QuoteServices().findSymbolWithHighestAsk("2020-01-03").getStatus(), 200);
	  
   }
}
