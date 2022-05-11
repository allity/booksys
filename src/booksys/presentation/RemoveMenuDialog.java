package booksys.presentation;

import java.awt.* ;
import java.awt.event.* ;

public class RemoveMenuDialog extends Dialog {
	protected Label nameLabel;
	protected TextField name;
	protected Button    ok ;
	protected Button    cancel ;
	protected boolean 	confirmed;
	
	RemoveMenuDialog(Frame owner, String title)
	  {
		super(owner, title, true) ;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			  confirmed = false ;
			  RemoveMenuDialog.this.hide() ;
			}
		      }) ;
		
		nameLabel = new Label("Menu Name:", Label.RIGHT) ;
	    name = new TextField(10) ;
	    
	    ok = new Button("ok") ;
	    ok.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = true ;
		  RemoveMenuDialog.this.hide() ;
		}
	      }) ;
	    
	    cancel = new Button("Cancel") ;
	    cancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = false ;
		  RemoveMenuDialog.this.hide() ;
		}
	      }) ;
	    
	    setLayout( new GridLayout(0, 2) ) ;
	    
	    add(nameLabel);
	    add(name);
	    add(ok);
	    add(cancel);
	    
	    pack();
	  }
	
	String getMenuName()
	{
		return name.getText();
	}
	
	boolean isConfirmed()
	  {
	    return confirmed ;
	  }
}
