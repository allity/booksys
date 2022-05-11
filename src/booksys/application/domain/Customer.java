/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

public class Customer
{
  private String name ;
  private String phoneNumber ;
  private String password;
  private int isNoShow;
  private int coupon;
  
  public Customer(String n, String p, String pw, int ins, int cp)
  {
    name = n ;
    phoneNumber = p ;
    password = pw;
    isNoShow = ins;
    coupon = cp;
  }

  public String getName()
  {
    return name ;
  }

  public String getPhoneNumber()
  {
    return phoneNumber ;
  }
  
  public String getPassword()
  {
	  return password;
  }
  
  public int getIsNoShow()
  {
	  return isNoShow;
  }
  
  public int getCoupon()
  {
	  return coupon;
  }
}
