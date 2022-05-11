package booksys.presentation;

import java.awt.* ;
import java.awt.event.* ;

public class AddMenuDialog extends Dialog {
	protected Label nameLabel;
	protected Label priceLabel;
	protected Label categoryLabel;
	protected TextField name;
	protected TextField price;
	protected Choice    category ;
	protected Button    ok ;
	protected Button    cancel ;
	protected boolean 	confirmed;
	
	AddMenuDialog(Frame owner, String title)
	  {
		super(owner, title, true) ;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			  confirmed = false ;
			  AddMenuDialog.this.hide() ;
			}
		      }) ;
		
		nameLabel = new Label("Menu Name:", Label.RIGHT) ;
	    name = new TextField(10) ;
	    priceLabel = new Label("Price:", Label.RIGHT);
	    price = new TextField(10);
	    categoryLabel = new Label("Category:", Label.RIGHT);
	    category = new Choice();
	    category.add("meal");
	    category.add("salad");
	    category.add("coffee");
	    category.add("softdrink");
	    
	    ok = new Button("ok") ;
	    ok.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = true ;
		  AddMenuDialog.this.hide() ;
		}
	      }) ;
	    
	    cancel = new Button("Cancel") ;
	    cancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  confirmed = false ;
		  AddMenuDialog.this.hide() ;
		}
	      }) ;
	    
	    setLayout( new GridLayout(0, 2) ) ;
	    
	    add(nameLabel);
	    add(name);
	    add(priceLabel);
	    add(price);
	    add(categoryLabel);
	    add(category);
	    add(ok);
	    add(cancel);
	    
	    pack();
	  }
	
	String getMenuName()
	{
		return name.getText();
	}
	
	String getPrice()
	{
		return price.getText();
	}
	
	String getCategory()
	{
		return category.getSelectedItem();
	}
	
	boolean isConfirmed()
	  {
	    return confirmed ;
	  }
}
