/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import booksys.application.persistency.CustomerMapper;
import booksys.application.persistency.MenuMapper;
import booksys.application.persistency.BookingMapper;
import booksys.storage.Database;

public class BookingSystem
{
	CustomerMapper cm = CustomerMapper.getInstance() ;
	MenuMapper mm = new MenuMapper();
	BookingMapper bm = BookingMapper.getInstance();
  // Attributes:

  Date currentDate ;
  Date today ;
  
  // Associations:

  Restaurant restaurant = null ;
  Vector currentBookings ;
  Booking selectedBooking ;

  // Singleton:
  
  private static BookingSystem uniqueInstance ;

  public static BookingSystem getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingSystem() ;
    }
    return uniqueInstance ;
  }

  private BookingSystem()
  {
    today = new Date(Calendar.getInstance().getTimeInMillis()) ;
    restaurant = new Restaurant() ;
  }

  // Observer: this is `Subject/ConcreteSubject'

  Vector observers = new Vector() ;

  public void addObserver(BookingObserver o)
  {
    observers.addElement(o) ;
  }
  
  public void notifyObservers()
  {
    Enumeration enum1 = observers.elements() ;
    while (enum1.hasMoreElements()) {
      BookingObserver bo = (BookingObserver) enum1.nextElement() ;
      bo.update() ;
    }
  }

  public boolean observerMessage(String message, boolean confirm)
  {
    BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
    return bo.message(message, confirm) ;
  }
  
  // System messages:

  public void display(Date date)
  {
    currentDate = date ;
    currentBookings = restaurant.getBookings(currentDate) ;
    selectedBooking = null ;
    notifyObservers() ;
  }
  
  public void makeReservation(int covers, Date date, Time time, int tno,
			      String name, String phone, String password, int isnoshow, int coupon, String menu)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers) && !isnoshow(name, phone, password, isnoshow, coupon) && pwCheck(name, phone, password, isnoshow, coupon)) {
      Booking b
	= restaurant.makeReservation(covers, date, time, tno, name, phone, password, isnoshow, coupon, menu) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
      
      cpCheck(name, phone, password, isnoshow, coupon);
      
    }
  }
 
  private void cpCheck(String name, String phone, String password, int isnoshow, int coupon) {
	  Customer c=cm.getCustomer(name,  phone,  password, isnoshow, coupon);
		  observerMessage("you have "+ c.getCoupon()+ " coupons. (you can use more than 10 coupons.)", false);
	  
  }
  
  public void makeWalkIn(int covers, Date date, Time time, int tno)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {
      Booking b = restaurant.makeWalkIn(covers, date, time, tno) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }
  
  public void selectBooking(int tno, Time time)
  {
    selectedBooking = null ;
    Enumeration enum1 = currentBookings.elements() ;
    while (enum1.hasMoreElements()) {
      Booking b = (Booking) enum1.nextElement() ;
      if (b.getTableNumber() == tno) {
	if (b.getTime().before(time)
	    && b.getEndTime().after(time)) {
	  selectedBooking = b ;
	}
      }
    }
    notifyObservers() ;
  }

  public void cancel()
  {
    if (selectedBooking != null) {
      if (observerMessage("Are you sure?", true)) {
	currentBookings.remove(selectedBooking) ;
	restaurant.removeBooking(selectedBooking) ;
	selectedBooking = null ;
	notifyObservers() ;
      }
    }
  }
   
  public void recordArrival(Time time)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getArrivalTime() != null) {
	observerMessage("Arrival already recorded", false) ;
      }
      else {
	selectedBooking.setArrivalTime(time) ;
	restaurant.updateBooking(selectedBooking) ;
	raiseCoupon();
	notifyObservers() ;
      }
    }
  }
  
public void raiseCoupon() {	
	  Date date = selectedBooking.getDate();
	  Time time = selectedBooking.getTime();
	  int table = selectedBooking.getTable().getNumber();
	  
	  try
	    {
	      Statement stmt
		= Database.getInstance().getConnection().createStatement() ;
	      ResultSet rset
		= stmt.executeQuery("SELECT * FROM Reservation WHERE date='"
				    + date + "'") ;
	      while (rset.next()) {
	    		Date bdate = rset.getDate(3) ;
	    		Time btime = rset.getTime(4) ;
	    		int tablenum = rset.getInt(5) ;
	    		int cust = rset.getInt(6) ;
	    		if(bdate.toString().equals(date.toString()) && btime.toString().equals(time.toString()) && table == tablenum) {
	    			bm.performUpdate("UPDATE Customer SET coupon = coupon+1 where oid= " + cust );
	    		}
	      }
	      rset.close() ;
	      stmt.close() ;
	     }
	     catch (SQLException e) {
	       e.printStackTrace() ;
	     }
}


  public void transfer(Time time, int tno)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getTableNumber() != tno) {
	if (!doubleBooked(selectedBooking.getTime(), tno, selectedBooking)
	    && !overflow(tno, selectedBooking.getCovers())) {
	  selectedBooking.setTable(restaurant.getTable(tno)) ;
	  restaurant.updateBooking(selectedBooking) ;
	}
      }
      notifyObservers() ;
    }
  }
  
  private boolean doubleBooked(Time startTime, int tno, Booking ignore)
  {
    boolean doubleBooked = false ;

    Time endTime = (Time) startTime.clone() ;
    endTime.setHours(endTime.getHours() + 2) ;
    
    Enumeration enum1 = currentBookings.elements() ;
    while (!doubleBooked && enum1.hasMoreElements()) {
      Booking b = (Booking) enum1.nextElement() ;
      if (b != ignore && b.getTableNumber() == tno
	  && startTime.before(b.getEndTime())
	  && endTime.after(b.getTime())) {
	doubleBooked = true ;
	observerMessage("Double booking!", false) ;
      }
    }
    return doubleBooked ;
  }
  
  private boolean overflow(int tno, int covers)
  {
    boolean overflow = false ;
    Table t = restaurant.getTable(tno) ;
      
    if (t.getPlaces() < covers) {
      overflow = !observerMessage("Ok to overfill table?", true) ;
    }
    
    return overflow ;
  }
  
  private boolean isnoshow(String name, String phone, String password, int isnoshow, int coupon)
  {
	  Customer c = cm.getCustomer(name, phone, password, isnoshow, coupon);
	  if(c.getIsNoShow() >= 1) {
		  System.out.println();
		  observerMessage("You have NoShow!", false);
		  return true;
	  }
	  return false;
  }
  
  private boolean pwCheck(String name, String phone, String password, int isnoshow, int coupon)
  {
	  Customer c=cm.getCustomer(name,  phone,  password, isnoshow, coupon);
	  if(c.getPassword().equals(password)) {
		  return true;
	  }
	  observerMessage("not your password", false);
	  return false;
  }
  
  public void addnoshow(String name, String phone)
  {
	  cm.addnoshow(name, phone);
  }
  
  public void removenoshow(String name, String phone)
  {
	  cm.removenoshow(name, phone);
  }
  
  public void addmenu(String mname, String price, String category)
  {
	  mm.addmenu(mname, price, category);
  }
  
  public void removemenu(String mname)
  {
	  mm.removemenu(mname);
  }
  // Other Operations:

  public Date getCurrentDate()
  {
    return currentDate ;
  }
  
  public Enumeration getBookings()
  {
    return currentBookings.elements() ;
  }

  public Booking getSelectedBooking()
  {
    return selectedBooking ;
  }

  public static Vector getTableNumbers()
  {
    return Restaurant.getTableNumbers() ;
  }

}
