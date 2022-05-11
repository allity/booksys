/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation ;

import booksys.application.domain.Reservation ;
import java.awt.* ;
import java.awt.event.* ;

class ReservationDialog extends BookingDialog
{
	
  protected TextField name ;
  protected TextField phone ;
  protected Label     nameLabel ;
  protected Label     phoneLabel ;
  protected TextField password;
  protected Label     passwordLabel;
  protected int isnoshow;
  protected int coupon;
  
 
  
  ReservationDialog(Frame owner, String title)
  {
    this(owner, title, null) ;
  }

  // This constructor initializes fields with data from an existing booking.
  // This is useful for completing Exercise 7.6.
  
  ReservationDialog(Frame owner, String title, Reservation r)
  {
    super(owner, title, r) ;

    nameLabel = new Label("Name:", Label.RIGHT) ;
    name = new TextField(30) ;
    if (r != null) {
      name.setText(r.getCustomer().getName()) ;
    }

    phoneLabel = new Label("Phone no:", Label.RIGHT) ;
    phone = new TextField(15) ;
    if (r != null) {
      phone.setText(r.getCustomer().getPhoneNumber()) ;
    }
        
    passwordLabel = new Label("password:", Label.RIGHT) ;
    password = new TextField(15) ;
    if (r != null) {
      password.setText(r.getCustomer().getPassword()) ;
    }
    if (r != null) {
    	isnoshow = r.getCustomer().getIsNoShow();
    	coupon = r.getCustomer().getCoupon();
    }
    // Lay out components in dialog
    
    setLayout( new GridLayout(0, 2) ) ;

    add(timeLabel) ;
    add(time) ;

    add(nameLabel) ;
    add(name) ;

    add(phoneLabel) ;
    add(phone) ;
    
    add(coversLabel) ;
    add(covers) ;
    
    add(passwordLabel);
    add(password);

    add(tableNumberLabel) ;
    add(tableNumber) ;
    
    add(choice);
    add(ok) ;
    add(cancel) ;
    
    pack() ;
  }

  String getCustomerName()
  {
    return name.getText() ;
  }

  String getPhoneNumber()
  {
    return phone.getText() ;
  }
  
  String getPassword() 
  {
	return password.getText();
  }
  
  int getIsNoShow()
  {
	  return isnoshow;
  }
  
  int getCoupon()
  {
	  return coupon;
  }
}
