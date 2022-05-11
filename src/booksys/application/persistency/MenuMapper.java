
package booksys.application.persistency;

import booksys.storage.* ;
import booksys.application.domain.BookingSystem;
import java.sql.* ;

public class MenuMapper {
	
	public String getMeal() {
		String meal;
		
		meal = getMenu("meal");
		
		return meal;
	}
	
	public String getSalad() {
		String salad;
		
		salad = getMenu("salad");
		
		return salad;
	}
	
	public String getcoffee() {
		String coffee;
		
		coffee = getMenu("coffee");
		
		return coffee;
	}
	
	public String getsoftdrink() {
		String softdrink;
		
		softdrink = getMenu("softdrink");
		
		return softdrink;
	}
	
	public String getMenu(String category) {
		String menu = "";
		
		try {
		      Statement stmt
			= Database.getInstance().getConnection().createStatement() ;
		      ResultSet rset
			= stmt.executeQuery("select oid, name, price from menu where category = '" + category + "'") ;
		      while (rset.next()) {
		    	  int    oid   = rset.getInt(1) ;
		    	  String name  = rset.getString(2) ;
		    	  String price = rset.getString(3);
		    	  
		    	  menu = menu + "  "+ oid + ". " + name + " " + price;
		      }
		      rset.close() ;
		      stmt.close() ;
		    }
		    catch (SQLException e) {
		      e.printStackTrace() ;
		    }
		return menu;
	}
	
	private void performUpdate(String sql)
	  {
	    try {
	      Statement stmt
		= Database.getInstance().getConnection().createStatement() ;
	      int updateCount
		= stmt.executeUpdate(sql) ;
	      stmt.close() ;
	    }
	    catch (SQLException e) {
	      e.printStackTrace() ;
	    }
	  }
	
	public void addmenu(String name, String price, String category)
	{
		int oid = Database.getInstance().getId() ;
		performUpdate("INSERT INTO menu (name, price, category) VALUES ('" + name + "'," + Integer.parseInt(price) + ",'" + category + "')");
	}
	
	public void removemenu(String name)
	{
		performUpdate("DELETE from menu WHERE name = '" + name + "'");
	}
}
