/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import booksys.application.persistency.* ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.Vector ;

class Restaurant
{
  BookingMapper  bm = BookingMapper.getInstance() ;
  CustomerMapper cm = CustomerMapper.getInstance() ;
  TableMapper    tm = TableMapper.getInstance() ;
  OrderMapper	 om = new OrderMapper();
  
  Vector getBookings(Date date)
  {
    return bm.getBookings(date) ;
  }

  Customer getCustomer(String name, String phone, String password, int isnoshow, int coupon)
  {
    return cm.getCustomer(name, phone, password, isnoshow, coupon) ;
  }
  
  Table getTable(int n)
  {
    return tm.getTable(n) ;
  }

  static Vector getTableNumbers()
  {
    return TableMapper.getInstance().getTableNumbers() ;
  }

  public Booking makeReservation(int covers, Date date,
				     Time time,
				     int tno, String name, String phone, String password, int isnoshow, int coupon, String menu)
  {
    Table t = getTable(tno) ;
    Customer c = getCustomer(name, phone, password, isnoshow, coupon) ;
    return bm.createReservation(covers, date, time, t, c, null, menu) ;
  }

  public Booking makeWalkIn(int covers, Date date,
			   Time time, int tno)
  {
    Table t = getTable(tno) ;
    return bm.createWalkIn(covers, date, time, t) ;
  }

  public void updateBooking(Booking b)
  {
    bm.updateBooking(b) ;
  }
  
  public void removeBooking(Booking b) {
    bm.deleteBooking(b) ;
    om.deleteOrder(b) ;
  }
}
