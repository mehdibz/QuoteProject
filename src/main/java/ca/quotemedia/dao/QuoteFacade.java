package ca.quotemedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.quotemedia.model.Quote;


public class QuoteFacade {

	private static final String QUERY1 = "SELECT COUNT(*), MAX(BID), MIN(ASK) FROM QUOTE WHERE SYMBOL = ? LIMIT 1";
	private static final String QUERY2 = "SELECT COUNT(*), SYMBOL, ASK FROM QUOTE WHERE DAY= ? GROUP BY SYMBOL ORDER BY ASK DESC LIMIT 1 ;";
	private static QuoteFacade quoteFacadeinstance = null;
	private Quote quote = null;
	private static Connection conn;
	private PreparedStatement ps;
	private ResultSet rs=null;
	
    static {
    	conn = DBConnection.getDBConnection();
    	try {
			createTable();
		} catch (SQLException e) {

			e.printStackTrace();
		}
    	quoteFacadeinstance = new QuoteFacade();
    }

    public static QuoteFacade getInstance() {
        return quoteFacadeinstance;
    }

	public Quote findQuote(String sym)
	{
 	 try {
			rs = prepDAO(sym,QUERY1);
			if(rs.next()) {
				
		        if(rs.getInt(1)>0) {
		        	quote = new Quote();
		        	quote.setBid(String.valueOf(rs.getFloat(2)));
		        	quote.setAsk(String.valueOf(rs.getFloat(3)));
		        	return quote;
		        }
			}
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	public Quote findSymbolWithHighestAsk(String day) {
 	 try {
			rs = prepDAO(day,QUERY2);
			if(rs.next()) {

				if(rs.getInt(1)>0) {
		        	quote = new Quote();
		        	quote.setSymbol(rs.getString(2));
		        	quote.setDay(day);
		        	quote.setAsk(String.valueOf(rs.getFloat(3)));
		        	return quote;
		        }
			}
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	public ResultSet prepDAO(String element,String query) {
		ResultSet result=null;
		try {

			ps = conn.prepareStatement(query);
			ps.setString(1,element);
			result = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void createTable() throws SQLException {

        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

        String CreateQuery = "CREATE TABLE QUOTE(\r\n"
			        		+ "  SYMBOL VARCHAR COMMENT 'The symbol the quote is for.',\r\n"
			        		+ "  DAY DATE COMMENT 'The date of the quote.',\r\n"
			        		+ "  BID DECIMAL COMMENT 'The bid of the quote.',\r\n"
			        		+ "  ASK DECIMAL COMMENT 'The ask of the quote.',\r\n"
			        		+ "  PRIMARY KEY(SYMBOL, DAY)\r\n"
			        		+ ");";
        String InsertQuery = "INSERT INTO QUOTE(SYMBOL, DAY, BID, ASK) VALUES\r\n"
			        		+ "  -- Microsoft\r\n"
			        		+ "  ('MSFT', '2020-01-1', 2.03, 2.60),\r\n"
			        		+ "  ('MSFT', '2020-01-2', 2.91, 3.33),\r\n"
			        		+ "  ('MSFT', '2020-01-3', 3.74, 4.33),\r\n"
			        		+ "  ('MSFT', '2020-01-4', 3.87, 4.10),\r\n"
			        		+ "  ('MSFT', '2020-01-5', 2.51, 2.96),\r\n"
			        		+ "\r\n"
			        		+ "  -- Google\r\n"
			        		+ "  ('GOOG', '2020-01-1', 3.11, 3.34),\r\n"
			        		+ "  ('GOOG', '2020-01-2', 3.97, 4.56),\r\n"
			        		+ "  ('GOOG', '2020-01-3', 5.59, 5.85),\r\n"
			        		+ "  ('GOOG', '2020-01-4', 4.94, 5.28),\r\n"
			        		+ "  ('GOOG', '2020-01-5', 0.67, 0.81),\r\n"
			        		+ "\r\n"
			        		+ "  -- Facebook\r\n"
			        		+ "  ('FB', '2020-01-1', 5.54, 5.66),\r\n"
			        		+ "  ('FB', '2020-01-2', 4.89, 5.40),\r\n"
			        		+ "  ('FB', '2020-01-3', 2.90, 3.31),\r\n"
			        		+ "  ('FB', '2020-01-4', 6.12, 6.33),\r\n"
			        		+ "  ('FB', '2020-01-5', 3.68, 3.93);";
        String SelectQuery = "select * from QUOTE";
        
        try {
        	
            createPreparedStatement = conn.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            insertPreparedStatement = conn.prepareStatement(InsertQuery);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            selectPreparedStatement = conn.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();

            System.out.println("SYMBOL" +"|"+ "DAY" +"   | "+ "BID" +" | "+"ASK");
            while (rs.next()) {
                System.out.println(rs.getString("SYMBOL") +"  |"+ rs.getString("DAY") +"|"+ rs.getString("BID") +"|"+rs.getString("ASK"));
            }
            selectPreparedStatement.close();
            conn.commit();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
