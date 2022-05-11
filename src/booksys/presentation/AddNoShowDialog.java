package booksys.presentation;

import java.awt.* ;
import java.awt.event.* ;

public class AddNoShowDialog extends Dialog {
	protected Label nameLabel;
	protected Label phoneLabel;
	protected TextField name;
	protected TextField phone;
	protected Button    ok ;
	protected Button    cancel ;
	protected boolean 	confirmed;
	
	AddNoShowDialog(Frame owner, String title)
	  {
		super(owner, title, true) ;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			  confirmed = false ;
			  AddNoShowDialog.this.hide() ;
			}
		      }) ;
		
		nameLabel = new Label("Name:", Label.RIGHT) ;
	    name = new TextField(20) ;
	    phoneLabel = new Label("Phone no:", Label.RIGHT);
	    phone = new TextField(20);
	    
	    ok = new Button("ok") ;
	    ok.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = true ;
		  AddNoShowDialog.this.hide() ;
		}
	      }) ;
	    
	    cancel = new Button("Cancel") ;
	    cancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = false ;
		  AddNoShowDialog.this.hide() ;
		}
	      }) ;
	    
	    setLayout( new GridLayout(0, 2) ) ;
	    
	    add(nameLabel);
	    add(name);
	    add(phoneLabel);
	    add(phone);
	    add(ok);
	    add(cancel);
	    
	    pack();
	  }
	
	  String getCustomerName()
	  {
	    return name.getText() ;
	  }

	  String getPhoneNumber()
	  {
	    return phone.getText() ;
	  }
	  
	  boolean isConfirmed()
	  {
	    return confirmed ;
	  }
	
}
