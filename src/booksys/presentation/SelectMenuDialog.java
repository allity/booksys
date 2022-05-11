package booksys.presentation ;

import booksys.application.domain.Reservation ;
import booksys.application.persistency.MenuMapper;

import java.awt.* ;
import java.awt.event.* ;


class SelectMenuDialog extends BookingDialog
{
	MenuMapper mm = new MenuMapper();
	
	protected Label mealLabel ;
	protected Label saladLabel ;
	protected Label coffeeLabel ;
	protected Label softdrinkLabel ;
	protected Label meal ;
	protected Label salad ;
	protected Label coffee ;
	protected Label softdrink ;
	protected Label cLabel;
	protected TextField menu;
	protected Label exLabel;
	protected Label ex;
	
	protected Panel blank1;
	protected Panel blank2;
	protected Panel blank3;
	protected Panel blank4;
	protected Panel blank5;
	protected Panel blank6;
	protected Panel blank7;
	protected Panel blank8;
	protected Panel blank9;
	protected Panel blank10;
	
	protected Button ok;
	
	protected boolean confirm;
  
  SelectMenuDialog(Frame owner, String title)
  {
    this(owner, title, null) ;
  }

  
  SelectMenuDialog(Frame owner, String title, Reservation r)
  {
    super(owner, title, r) ;
    
    addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    	  confirmed = false ;
    	  SelectMenuDialog.this.hide();
    	}
          }) ;
    
    mealLabel = new Label("MEAL", Label.LEFT) ;
    meal = new Label(mm.getMeal());
    saladLabel = new Label("SALAD", Label.LEFT);
    salad = new Label(mm.getSalad());
    coffeeLabel = new Label("COFFEE", Label.LEFT);
    coffee = new Label(mm.getcoffee());
    softdrinkLabel = new Label("SOFTDRINK", Label.LEFT);
    softdrink = new Label(mm.getsoftdrink());
    cLabel = new Label("Choice number :", Label.RIGHT);
    menu = new TextField(15) ;
    if (r != null) {
      menu.setText(r.getMenu().getMenu()) ;
    }
    exLabel = new Label("If you choice 2 pasta, 1 coffe :", Label.RIGHT);
    ex = new Label("1,1,5");
    
    this.ok = new Button("ok");
    this.ok.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  confirm = true ;
	  SelectMenuDialog.this.hide() ;
	}
      }) ;
    
    this.cancel = new Button("Cancel") ;
    this.cancel.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  confirm = false ;
	  SelectMenuDialog.this.hide() ;
	}
      }) ;
    
    
    blank1 = new Panel();
    blank2 = new Panel();
    blank3 = new Panel();
    blank4 = new Panel();
    blank5 = new Panel();
    blank6 = new Panel();
    blank7 = new Panel();
    blank8 = new Panel();
    blank9 = new Panel();
    blank10 = new Panel();
    
    setLayout( new GridLayout(0, 2) ) ;
    
    add(mealLabel);	add(blank1);
    add(meal);		add(blank2);
    add(saladLabel);add(blank3);
    add(salad);		add(blank4);
    add(coffeeLabel);add(blank5);
    add(coffee);	add(blank6);
    add(softdrinkLabel);add(blank7);
    add(softdrink);	add(blank8);
    add(blank9);	add(blank10);
    add(cLabel);	add(menu);
    add(exLabel);	add(ex);
    
    add(this.ok) ;
    add(this.cancel) ;
    
    pack() ;
  }
  
  String giveMenu()
  {
	  return menu.getText();
  }
  
  boolean isConfirm()
  {
    return confirm ;
  }
  
}
