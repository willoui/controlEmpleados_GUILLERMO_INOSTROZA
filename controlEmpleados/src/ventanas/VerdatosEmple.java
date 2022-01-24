package ventanas;

import java.awt.*;

import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import java.lang.Object;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
public class VerdatosEmple extends JDialog implements ActionListener  {
	
private static final long serialVersionUID = 1L;


JLabel mensaje=new JLabel(" ----------------------------- ");
JLabel titulo=new JLabel ("Listado de empleados.");


JButton imprimir=new JButton("IMPRIMIR LISTADO EN PDF.");
JButton fin=new JButton("CERRAR.");
JButton ver=new JButton("VER DATOS DE EMPLEADOS.");
Color c; 

JTextArea ta;
JScrollPane scrolltextarea; 

public VerdatosEmple(JFrame f )
{  
	//NUMfact = Short.parseShort(num.getText()); // el número de factura
	setTitle("DATOS DE EMPLEADOS.");
	setModal(true);
    JPanel p0 = new JPanel();c = Color.CYAN;
	p0.add(titulo);	p0.setBackground(c);
	
	
	JPanel p31 = new JPanel(); p31.setLayout (new GridLayout(1,8,8,10));
	p31.add(ver);
		
	JPanel p41 = new JPanel(); p41.setLayout (new FlowLayout());
  	//area de texto
	ta = new JTextArea("", 14, 85);
	ta.setLineWrap(false); // AJUSTAR LAS LÍNEASS
	scrolltextarea = new JScrollPane(ta);
	scrolltextarea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
	JPanel p4 = new JPanel(); p4.setLayout (new FlowLayout());
	p4.add(scrolltextarea);
	
		
	// BOTONES 
	JPanel p9 = new JPanel();p9.setLayout (new FlowLayout());
	c = Color.PINK; p9.add(imprimir);
	p9.add(fin);p9.setBackground(c);
	
    //Mensaje para ver operaciones 
	JPanel p10 = new JPanel();p10.setLayout (new FlowLayout());
	p10.add(mensaje);
	// para ver la ventana y colocar los controles verticalmente
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
	
    add(p0);	add(p31);	
	add(p41);add(p4);	add(p9);add(p10);
	
	pack(); //hace que se coloquen alineados los elementos de cada JPanel
	
	imprimir.addActionListener(this);
	fin.addActionListener(this);
	ver.addActionListener(this);
	setVisible(true);   
}



public void actionPerformed(ActionEvent e) 
{  
      
   if (e.getSource() == ver) {  
	   verempleados();
    }
 
	if (e.getSource() == fin) {
		 dispose();   	
	}
	
	if (e.getSource() == imprimir) { 
		imprimirempleados();
		
	}
}

public void imprimirempleados() {

	FileOutputStream archivo = null;
	String nombrepdf="listaemple.pdf";
	try {
		archivo = new FileOutputStream(nombrepdf);
	} catch (FileNotFoundException e1) {
		mensaje.setText("ERROR AL CREAR EL FICHERO DE SALIDA.....");   
		//e1.printStackTrace();
	}
	  Document documento = new Document();
	  try {
		PdfWriter.getInstance(documento, archivo);
	    documento.open();  
		documento.add(new Paragraph("DATOS DE LA TABLA EMPLEADOS."));
		documento.add(new Paragraph("---------------------------------------------------------"));
		// ahora el text área completo
		documento.add(new Paragraph(ta.getText(), FontFactory.getFont("Courier",  9, Font.ITALIC,                   // estilo
				BaseColor.BLACK)));
		mensaje.setText("......LISTADO GENERADO.....");   
	} catch (DocumentException e1) {
		mensaje.setText("ERROR AL CREAR EL DOCUMENTO PDF .....");   
		e1.printStackTrace();
	}
	  documento.close();
	  
} // fin imprimirempleados

private void verempleados() {
	
	   // por si no se ve la factura
	 
		// cabeceras		
		Font f = new Font( "Courier",Font.BOLD,12 );
	    ta.setFont( f );
	    ta.setText("");
		f = new Font( "Courier",Font.TRUETYPE_FONT,12 );
	    ta.setFont( f );
		 try
		 	{  Class.forName("org.sqlite.JDBC");
			  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");
		       Statement sentencia = conexion.createStatement();
		       ResultSet resul = sentencia.executeQuery ("SELECT * FROM EMPLEADOS ");
		       ta.append("--------------------------------------------------------------------------------------------- \n");
		       ta.append("                            VISUALIZANDO EMPLEADOS               \n");
		       ta.append("---------------------------------------------------------------------------------------------- \n");
		       ta.append ("EMP_NO\tAPELLIDO\tOFICIO\t\tDIRECTOR " +
		       		"\tSALARIO\tCOMISION\tDEPT_NO \n");
		       while (resul.next())
		       {
		    	   ta.append(resul.getInt(1) + "\t" + resul.getString(2) + "\t\t" +
		        	resul.getString("OFICIO") + "\t"+resul.getInt("DIR") + "\t\t"+
		        	resul.getDouble(6) + "\t"+ resul.getDouble(7) + "\t\t" +resul.getInt(8)+ "\n");
		       }
		       ta.append("-------------------------------------------------------------------------------------------- \n");
		       resul.close();// Cerrar ResultSet
		       sentencia.close();// Cerrar Statement
		       conexion.close();//Cerrar conexion 
		   } 
		   catch (ClassNotFoundException cn) {
		     System.out.println ("ERRROOORRRRR ClassNotFoundException NO CARGA EL DRIVER");
		     cn.printStackTrace();} 
		   catch (SQLException e) {
		     System.out.println ("ERRROOORRRRR SQLException");
		     System.out.println ("Codigo:" + e.getErrorCode());
		     System.out.println ("Mensaje:" + e.getMessage());
		     System.out.println ("Estado:" + e.getSQLState());
		     //e.printStackTrace();
		 	 if (e.getErrorCode()==17002)
		        mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   
		     } 	 		 
}//fin ver empleados
 
}//fin clase
