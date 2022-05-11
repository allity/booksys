/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency ;

import booksys.storage.* ;
import booksys.application.domain.BookingSystem;
import java.sql.* ;
import java.util.Enumeration ;
import java.util.Hashtable ;

public class CustomerMapper
{
  // Implementation of hidden cache
  
  private Hashtable cache ;

  private PersistentCustomer getFromCache(int oid)
  {
    Integer key = new Integer(oid) ;
    return (PersistentCustomer) cache.get(key) ;
  }

  private PersistentCustomer getFromCacheByDetails(String name, String phone, String password, int isnoshow, int coupon)
  {
    PersistentCustomer c = null ;
    Enumeration enum1 = cache.elements();
    while (c == null && enum1.hasMoreElements()) {
      PersistentCustomer tmp = (PersistentCustomer) enum1.nextElement() ;
      if (name.equals(tmp.getName()) && phone.equals(tmp.getPhoneNumber()) && password.equals(tmp.getPassword()) && isnoshow == tmp.getIsNoShow() && coupon == tmp.getCoupon()) {
	c = tmp ;
      }
    }
    
    //if(c.getIsNoShow() >= 1) {
		//  BookingSystem.getInstance().observerMessage("You have NoShow!", false);
	  //}
    return c ;
  }

  private void addToCache(PersistentCustomer c)
  {
    Integer key = new Integer(c.getId()) ;
    cache.put(key, c) ;
  }
  
  // Constructor:
  
  private CustomerMapper()
  {
    cache = new Hashtable() ;
  }

  // Singleton:
  
  private static CustomerMapper uniqueInstance ;

  public static CustomerMapper getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new CustomerMapper() ;
    }
    return uniqueInstance ;
  }

  public PersistentCustomer getCustomer(String n, String p, String pw, int ins, int cp)
  {
    PersistentCustomer c = getFromCacheByDetails(n, p, pw, ins, cp) ;
    if (c == null) {
      c = getCustomer("SELECT * FROM Customer WHERE name = '" + n
		      + "' AND phoneNumber = '" + p + "'");
      if (c == null) {
    	c = createCustomer(n, p, pw, ins, cp) ;
      }
      addToCache(c) ;
    }
    return c ;
  }
  
  PersistentCustomer getCustomerForOid(int oid)
  {
    PersistentCustomer c = getFromCache(oid) ;
    if (c == null) {
      c = getCustomer("SELECT * FROM Customer WHERE oid ='" + oid + "'") ;
      if (c != null) {
	addToCache(c) ;
      }
    }
    return c ;
  }

  // Add a customer to the database
  
  PersistentCustomer createCustomer(String name, String phone, String password, int isnoshow, int coupon)
  {
    PersistentCustomer c = getFromCacheByDetails(name, phone, password, isnoshow, coupon) ;
    if (c == null) {
      performUpdate("INSERT INTO Customer (name, phoneNumber, password, isnoshow, coupon)" +
			       "VALUES ('" + name + "', '" + phone + "','" + password + "','" + 0 + "','" + 0 + "')");
      
      c = getCustomer(name, phone, password, isnoshow, coupon) ;
    }
    return c ;
  }

  private PersistentCustomer getCustomer(String sql)
  {
    PersistentCustomer c = null ;
    try {
      Statement stmt
	= Database.getInstance().getConnection().createStatement() ;
      ResultSet rset
	= stmt.executeQuery(sql) ;
      while (rset.next()) {
	int    oid   = rset.getInt(1) ;
	String name  = rset.getString(2) ;
	String phone = rset.getString(3) ;
	String password = rset.getString(4);
	int isnoshow = rset.getInt(5);
	int coupon = rset.getInt(6);
	c = new PersistentCustomer(oid, name, phone, password, isnoshow, coupon) ;
      }
      rset.close() ;
      stmt.close() ;
    }
    catch (SQLException e) {
      e.printStackTrace() ;
    }
    return c ;
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
  
  public void addnoshow(String name, String phone)
  {
	  performUpdate("UPDATE Customer SET isnoshow = 1 WHERE name = '" + name + "' AND phonenumber = '" + phone + "'");
  }
  
  public void removenoshow(String name, String phone)
  {
	  performUpdate("UPDATE Customer SET isnoshow = 0 WHERE name = '" + name + "' AND phonenumber = '" + phone + "'");
  }
}
