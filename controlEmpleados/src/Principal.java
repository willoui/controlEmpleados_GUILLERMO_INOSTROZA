
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ventanas.VentanaEmple;
import ventanas.VerdatosEmple;


public class Principal extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
    private JButton b1,b2,b3,b4; 
      
	public Principal() {
	
	 setTitle("Ventana INICIAL.");
     setLayout(null);
	 setSize(450, 250);
	           
	 b1 = new JButton("Mantenimieno EMPLE."); 
	 b1.setBounds(100, 20, 230, 30); 
	 add(b1); 
	        
	 b2 = new JButton("LISTADO EMPLEADOS. "); 
	 b2.setBounds(100, 60, 230, 30); 
	 add(b2); 
	        
	 b3 = new JButton("Ver EMPLE Y DEPART por consola."); 
	 b3.setBounds(100, 100, 230, 30);
	 add(b3); 
	     
	 b4 = new JButton("CERRAR APLICACION."); 
	 b4.setBounds(100, 140, 230, 30);
	 add(b4); 
	     
	 b1.addActionListener(this);
	 b2.addActionListener(this);        
	 b3.addActionListener(this);        
	 b4.addActionListener(this);        
		
	 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	 setVisible(true);
   }

	public void actionPerformed(ActionEvent e) //acción cuando pulsamos botones 
	    {  	  	
	    	     if (e.getSource() == b1) 	
	    	     {	   new VentanaEmple(this);
	    	               	            
	    	     }	              
	    	     if (e.getSource() == b2) 	
	    	     { 
	    	    	  new VerdatosEmple(this);		    	   
	    	     }
	    	     if (e.getSource() == b3) 	
	    	     { 
	    	    	 Listaempledepart.verdepartamentos();
	 				Listaempledepart.verempleados();	    	   
	    	     }
	    	     if (e.getSource() == b4) 	
	    	     { 
	    	    	 System.exit(0);    	   
	    	     }
	    }

	public static void main(String[] args) {
		 new Principal();
		}//fin main
   
}//fin class 


