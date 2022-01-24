package ventanas;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class VentanaEmple extends JDialog implements ActionListener, ItemListener  {
	
private static final long serialVersionUID = 1L;

JTextField num=new JTextField(4);
JTextField nombre=new JTextField(10);
JTextField ofi=new JTextField(10);
JTextField dir=new JTextField(4);
//JTextField fec=new JTextField(10);
JTextField sal=new JTextField(10);
JTextField com=new JTextField(10);
// una lista desplegable para el departamento
Choice listadepar = new Choice();
JLabel nomdep=new JLabel("----");
String [] nombredepart=new String[50];
String nombredep;

JLabel mensaje=new JLabel(" ----------------------------- ");
JLabel titulo=new JLabel ("GESTIÓN DE EMPLEADOS. BD QLITE");

JLabel lnum = new JLabel ("Numero empleado:");
JLabel lnom = new JLabel ("Nombre:");
JLabel lofi = new JLabel ("Oficio:");

JLabel ldir = new JLabel ("Director:");
//una lista desplegable para los directores
Choice listadirec = new Choice();
JLabel nomdir=new JLabel("----");
String [] nombredirec=new String[100]; //array para guardar los nombres
String nombredir;

//JLabel lfec = new JLabel ("Fecha Alta (yyyy-mm-dd):");
JLabel lsal = new JLabel ("Salario:");
JLabel lcom = new JLabel ("Comision:");
JLabel ldep = new JLabel ("Numero de departamento:");


JButton balta= new JButton("Insertar Empleado");
JButton consu= new JButton("Consultar.");
JButton borra= new JButton("Borrar Empleado.");
JButton breset=new JButton("Limpiar datos.");
JButton modif=new JButton("Modificar Empleado.");
JButton ver=new JButton("Ver por consola.");
JButton fin=new JButton("CERRAR");
Color c; //para poner colores
 
public VentanaEmple(JFrame f )
{ 	
    setModal(true);
    setTitle("GESTIÓN DE EMPLEADOS. BD QLITE ejemplo");
    JPanel p0 = new JPanel();c = Color.CYAN;
	p0.add(titulo);	p0.setBackground(c);	
	JPanel p1 = new JPanel(); p1.setLayout (new FlowLayout());
	p1.add(lnum);p1.add(num);p1.add(consu);
	JPanel p2 = new JPanel();	p2.setLayout (new FlowLayout());
	p2.add(lnom);p2.add(nombre);
	JPanel p3 = new JPanel(); p3.setLayout (new FlowLayout());
	p3.add(lofi); p3.add(ofi);
	JPanel p4 = new JPanel(); p4.setLayout (new FlowLayout());
	cargalistaBDdirec(listadirec,nomdir,nombredirec);
	p4.add(ldir);
	p4.add(listadirec);
	p4.add(nomdir);

	//JPanel p5 = new JPanel(); p5.setLayout (new FlowLayout());
	//p5.add(lfec); p5.add(fec);
	JPanel p6 = new JPanel(); p6.setLayout (new FlowLayout());
	p6.add(lsal); p6.add(sal);
	JPanel p7 = new JPanel(); p7.setLayout (new FlowLayout());
	p7.add(lcom); p7.add(com);
	JPanel p32 = new JPanel();p32.setLayout (new FlowLayout());
	
	cargalistaBD(listadepar,nomdep,nombredepart);
	p32.add(ldep);
	p32.add(listadepar);
	p32.add(nomdep);
	// BOTONES ALTA-BORRAR-MODIFICAR
	JPanel p8 = new JPanel();p8.setLayout (new FlowLayout());
	c = Color.YELLOW; p8.add(balta); p8.add(borra);p8.add(modif);
	p8.setBackground(c);
	// BOTONES RESET y FIN
	JPanel p9 = new JPanel();p9.setLayout (new FlowLayout());
	c = Color.PINK; p9.add(breset);p9.add(ver);
	p9.add(fin);p9.setBackground(c);
    //Mensaje para ver operaciones 
	JPanel p10 = new JPanel();p10.setLayout (new FlowLayout());
	p10.add(mensaje);
	// para ver la ventana y colocar los controles verticalmente
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
	// añadir los panel al frame
	add(p0);add(p1);add(p2);add(p3);add(p4);
	//add(p5);
	add(p6);add(p7);add(p32);
	add(p8);add(p9);add(p10);
	
	pack(); 
	
	balta.addActionListener(this);
	breset.addActionListener(this);
	fin.addActionListener(this);
	consu.addActionListener(this);
	borra.addActionListener(this);
	modif.addActionListener(this);
	ver.addActionListener(this);
	listadepar.addItemListener(this);
	listadirec.addItemListener(this);
	
	setVisible(true);  
}

public void actionPerformed(ActionEvent e) 
{   int dep,emple, confirm;
	if (e.getSource() == balta) { //SE PULSA EL BOTON alta   	
		mensaje.setText(" has pulsado el boton alta");  
		try{
			emple=Integer.parseInt(num.getText());
			grabar();
			}catch ( NumberFormatException n) {
				mensaje.setText("Número de empleado incorrecto: " +num.getText() ); 
			}
		
	    }
		   
	if (e.getSource() == consu) { //SE PULSA EL BOTON  consultar  	
		mensaje.setText(" has pulsado el boton consultar");   
		try{
			emple=Integer.parseInt(num.getText());
			consultar(emple);
			}catch ( NumberFormatException n) {
				mensaje.setText("Número de empleado incorrecto: " +num.getText() ); 
			} catch (IOException ec) {
				mensaje.setText("ERROR AL IR A CONSULTAR.." ); 
				//ec.printStackTrace();
			}
	    }
		  
	if (e.getSource() == borra) { //SE PULSA EL BOTON  borrar  	
		mensaje.setText(" has pulsado el boton Borrar"); 
		try{
		emple=Integer.parseInt(num.getText());
		borrar(emple);
		}catch ( NumberFormatException n) {
			mensaje.setText("Número de empleado incorrecto: " +num.getText() ); 
		}
	}
	if (e.getSource() == modif) { //SE PULSA EL BOTON  modificar  	
		mensaje.setText(" has pulsado el boton Modificar.");   
		try{
			emple=Integer.parseInt(num.getText());
			modificar(emple);
			}catch ( NumberFormatException n) {
				mensaje.setText("Número de empleado incorrecto: " +num.getText() ); 
			}

	 }
	if (e.getSource() == fin) { //SE PULSA EL BOTON salir 	
		 // System.exit(0);	
		 dispose();   	
	}
	if (e.getSource() == ver) { //SE PULSA EL BOTON  ver por consola  	
		mensaje.setText("Visualizando el fichero por la consolaa.....");    
		try {
			verporconsola();
			} catch (IOException e1) {
			System.out.println (" ERROR AL VISUALIZAR POR CONSOLA. ");
				
			}
	}
	if (e.getSource() == breset) { //SE PULSA EL BOTON  limpiar  	
		mensaje.setText(" has pulsado el boton limpiar..");    
        num.setText(" ");nombre.setText(" ");
        sal.setText(" ");ofi.setText(" ");com.setText(" ");
        //fec.setText(" ");
	}
}

public  void verporconsola() throws IOException {     
  try
 	{  Class.forName("org.sqlite.JDBC");
	  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");
       Statement sentencia = conexion.createStatement();
       ResultSet resul = sentencia.executeQuery ("SELECT * FROM EMPLEADOS ");
       System.out.println ("-------------------------------------");
       System.out.println ("  VISUALIZANDO EMPLEADOS ");
       System.out.println ("-------------------------------------");
       System.out.println ("EMP_NO\tAPELLIDO\tOFICIO\tDIR" +
       		"\tSALARIO\tCOMISION\tDEPT_NO ");
       while (resul.next())
       {
        System.out.println (resul.getInt(1) + "\t" + resul.getString(2) + "\t" +
        	resul.getString("OFICIO") + "\t"+resul.getInt("DIR") + "\t"+
        	resul.getDouble(6) + "\t"+ resul.getDouble(7) + "\t" +resul.getInt(8));
       }
       System.out.println ("---------------------------------------------------------------");
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
}// fin verporconsola

void consultar(int emple) throws IOException 
{	int dep;
	System.out.println(" ---------consultar-------------------");
	try
	{
		 Class.forName("org.sqlite.JDBC");
		  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");
	Statement sentencia = conexion.createStatement();	
	ResultSet resul = sentencia.executeQuery ("SELECT * from empleados where EMP_NO="+emple); 
	System.out.println ("----------------------------------------------------");
	   
	 if (!resul.next())
			{ mensaje.setText("No EXISTE EL EMPLEADO: " + emple);
		      nombre.setText(" ");sal.setText(" ");ofi.setText(" ");
		      com.setText(" "); //fec.setText(" ");
			} 
	 else
	  { System.out.println ("DATOS DEL EMPLEADO: "+emple);
	    do{	   			  
	    	 System.out.println (resul.getInt(1) + "\t" + resul.getString(2) + "\t" +
	    	        	resul.getString("OFICIO") + "\t"+resul.getInt("DIR") + "\t"+
	    	        	resul.getDate(5) + "\t"+
	    	        	resul.getDouble(6) + "\t"+ resul.getDouble(7) + "\t" +resul.getInt(8));
	    	 nombre.setText(resul.getString(2));
	    	 ofi.setText(resul.getString(3));
	    	 dir.setText(Integer.toString(resul.getInt(4)));
	    	
	    	 // localizar lista directores
	    	 for (int i=0;i<listadirec.getItemCount();i++)
	    	 {	 if (Integer.parseInt(listadirec.getItem(i))==resul.getInt(4))
	    		 {
	    			 listadirec.select(i);
	    			 dep=listadirec.getSelectedIndex(); // posicion del elemento
	    			 //System.out.println (nombredirec[dep]);
	    			 nomdir.setText(nombredirec[dep]); 
	    			 break;
	    		 }
	    	 }
	    	 // localizar lista departamento
	    	 for (int i=0;i<listadepar.getItemCount();i++)
	    	 {
	    		 if (Integer.parseInt(listadepar.getItem(i))==resul.getInt(8))
	    		 {
	    			 listadepar.select(i);
	    			 dep=listadepar.getSelectedIndex(); // posicion del elemento
	    			 System.out.println (nombredepart[dep]);
	    			 nomdep.setText(nombredepart[dep]); 
	    			 break;
	    		 }
	    	 }
	    	 sal.setText(Double.toString(resul.getDouble(6)));
	    	 com.setText(Double.toString(resul.getDouble(7)));	 
	    	
	      }
	     while (resul.next());
	     resul.close();// Cerrar ResultSet
	     sentencia.close();// Cerrar Statement
	     conexion.close();//Cerrar conexion 
	   } 
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
	
} // fin consultar

void visualiza(int emp) 
{	
	System.out.println(" ---------visualizar--------");
	
	
} // fin visualiza
void borrar(int emp) 
{ mensaje.setText(" Borrando empleado.................");   
try
{	 Class.forName("org.sqlite.JDBC");
    Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");
	String sql= "delete from empleados where emp_no = " + emp ; 
	System.out.println(" Sentencia borrado: " + sql);
	Statement sentencia = conexion.createStatement();
	int resul = sentencia.executeUpdate(sql);
	System.out.println(" Borrados: " + resul);
	if  (resul!=0) 
	    {System.out.println(" REGISTRO BORRADO. ");
	     mensaje.setText(" EMPLEADO : " +emp+" BORRADOOOO.");   	
	    }
	else
	  {System.out.println(" NOOO BORRADO, no existe el empleado: " + emp);
	   mensaje.setText("REGISTRO NO BORRADO, no existe el empleado: " + emp);   	
	  }    		
	sentencia.close();		 
	conexion.close();   	  	   
    } catch (ClassNotFoundException cn) {cn.printStackTrace();
            System.out.println(" ERROR EN EL DRIVER. ");} 
   catch (SQLException e) {  System.out.println(" ERROR EN SENTENCIA SQL. Borrado ");
   System.out.println(" Mensaje: " + e.getMessage());
   System.out.println(" Estado: " + e.getSQLState());
   System.out.println(" Código: " + e.getErrorCode());
   if (e.getErrorCode()==1400)
   	   mensaje.setText(" Se Intenta insertar NULL en columna NOT NULL.");   
	if (e.getErrorCode()==17002)
        mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   

   }
    
} // fin borrar
void modificar(int emp) 
{	int error=0;
	mensaje.setText(" Modificando empleado.................");   
	
	//System.out.println("Nombre=  " + nombre.getText() + ", longitud:" + nombre.getText().length());
		String APELLIDO=null ;
	String OFICIO=null;
	if (nombre.getText().length()>10)
		  APELLIDO = nombre.getText().substring(0, 10); else APELLIDO=nombre.getText();
	if (ofi.getText().length()>10)	
	        OFICIO  = ofi.getText().substring(0, 10);  else OFICIO= ofi.getText();
	int dire=0; //               NUMBER(4)    
	//String FECHA = fec.getText();  
		
	Double SALARIO=null; //         sal NUMBER(6,2)  
	Double COMISION=null; //       com    NUMBER(6,2)  
	int DEPT_NO=0;  //  NOT NULL NUMBER(2)   
	// Hacemos la conversión
	try{
		dire=Integer.parseInt(listadirec.getSelectedItem());
		}catch ( NumberFormatException n) {
			mensaje.setText("Número de director incorrecto: " + listadirec.getSelectedItem() ); 
			error=1;}
	try{
		SALARIO=Double.parseDouble(sal.getText());
		if (SALARIO >9999.99)
		{		mensaje.setText("Salario sobrepasa formato 9999.99: " + sal.getText());
		error=1;}
		}catch ( NumberFormatException n) {
				mensaje.setText("Salario incorrecto: " + sal.getText() ); 
				error=1;}
	try{
		COMISION=Double.parseDouble(com.getText());
		if (COMISION >9999.99)
		    {	mensaje.setText("Comision sobrepasa formato 9999.99: " + com.getText());
		        error=1;}
		}catch ( NumberFormatException n) {
					mensaje.setText("COMISION incorrecto: " + sal.getText() );
					error=1;}
		try{
			DEPT_NO=Integer.parseInt(listadepar.getSelectedItem());
			}catch ( NumberFormatException n) {
				mensaje.setText("Número de departamento incorrecto: " + listadepar.getSelectedItem() ); 
				error=1;}
	if (error!=1)
	{ // Actualizamos si los datos son correctos	
	try
	{	 Class.forName("org.sqlite.JDBC");
	  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");   
		String sql= "update empleados set apellido= '" +APELLIDO + "'," +
				" oficio= '" +OFICIO+"',"+
				" dir=" +dire +
				" ,salario=" + SALARIO +
				", comision=" + COMISION + 
				" ,dept_no=" + DEPT_NO +
				" where emp_no = " + emp ; 
		System.out.println("SENTENCIA=  " + sql);
		Statement sentencia = conexion.createStatement();
		int resul = sentencia.executeUpdate(sql);
		System.out.println(" Actualizados: " + resul);
		if  (resul!=0) 
		    {System.out.println(" REGISTRO ACTUALIZADOO. ");
		     mensaje.setText(" EMPLEADO : " +emp+" ACTUALIZADOO.");   	
		    }
		else
		  {System.out.println(" NOOO ACTUALIZADO, no existe el empleado: " + emp);
		   mensaje.setText("REGISTRO NO ACTUALIZADO, no existe el empleado: " + emp);   	
		  }    		
		sentencia.close();		 
		conexion.close();  
		 	  	   
	    } catch (ClassNotFoundException cn) {cn.printStackTrace();
	            System.out.println(" ERROR EN EL DRIVER. ");} 
	   catch (SQLException e) {  System.out.println("** ERROR EN SENTENCIA SQL. **");
	   System.out.println(" Mensaje: " + e.getMessage());
	   System.out.println(" Estado: " + e.getSQLState());
	   System.out.println(" Código: " + e.getErrorCode());
	   if (e.getErrorCode()==1400)
	   	   mensaje.setText("Se Intenta insertar NULL en columna NOT NULL.");  
	   if (e.getErrorCode()==1861)
	   	   mensaje.setText("Error en el formato de la fecha. Reg No Modificado.");   
		if (e.getErrorCode()==17002)
	         mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   

	   }
	} // fin if error!=1
	
} // fin modificar


void grabar() 
{	 
	int error=0;
	int emple=Integer.parseInt(num.getText());

	mensaje.setText(" insertar empleado.................");   
	
	//System.out.println("Nombre=  " + nombre.getText() + ", longitud:" + nombre.getText().length());
	String APELLIDO=null ;
	String OFICIO=null;
	if (nombre.getText().length()>10)
		  APELLIDO = nombre.getText().substring(0, 10); else APELLIDO=nombre.getText();
	if (ofi.getText().length()>10)	
	        OFICIO  = ofi.getText().substring(0, 10);  else OFICIO= ofi.getText();
	int dire=0;             
	Double SALARIO=null;   
	Double COMISION=null; 
	int DEPT_NO=0;   
	Date FECHAAL = null;
	// Hacemos la conversión
	try{
		dire=Integer.parseInt(listadirec.getSelectedItem());
		}catch ( NumberFormatException n) {
			mensaje.setText("Número de director incorrecto: " + listadirec.getSelectedItem() ); 
			error=1;}
	try{
		SALARIO=Double.parseDouble(sal.getText());
		if (SALARIO >9999.99)
		{		mensaje.setText("Salario sobrepasa formato 9999.99: " + sal.getText());
		error=1;}
		}catch ( NumberFormatException n) {
				mensaje.setText("Salario incorrecto: " + sal.getText() ); 
				error=1;}
	try{
		COMISION=Double.parseDouble(com.getText());
		if (COMISION >9999.99)
		    {	mensaje.setText("Comision sobrepasa formato 9999.99: " + com.getText());
		        error=1;}
		}catch ( NumberFormatException n) {
					mensaje.setText("COMISION incorrecto: " + sal.getText() );
					error=1;}
		try{
			DEPT_NO=Integer.parseInt(listadepar.getSelectedItem());
			}catch ( NumberFormatException n) {
				mensaje.setText("Número de departamento incorrecto: " + listadepar.getSelectedItem() ); 
				error=1;}
					
	if (error!=1)
	{ // Insertamos si los datos son correctos	
	try
	{	 Class.forName("org.sqlite.JDBC");
	  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");   
		String sql= "insert into empleados(emp_no, apellido, oficio,dir,fecha_alt,salario,dept_no, comision) values " +
		  "("+ emple + ",'" +APELLIDO + "','"+OFICIO+"'," + dire + "," + 
		  "', '%yyyy/%mm/%dd') " + "," +SALARIO + " ," + DEPT_NO +
		  "," + COMISION +")";
			  
		System.out.println("SENTENCIA=  " + sql);
		Statement sentencia = conexion.createStatement();
		int resul = sentencia.executeUpdate(sql);
		System.out.println(" Insertado: " + resul);
		if  (resul!=0) 
		    {System.out.println(" REGISTRO INSERTADO. ");
		     mensaje.setText(" EMPLEADO : " +emple+" INSERTADO.");   	
		    }
		else
		  {System.out.println(" NOOO INSERTADO: " + emple);
		   mensaje.setText("REGISTRO NO INSERTADO. ERROR: " + emple);   	
		  }    		
		sentencia.close();		 
		conexion.close();  
		 	  	   
	    } catch (ClassNotFoundException cn) {cn.printStackTrace();
	            System.out.println(" ERROR EN EL DRIVER. ");} 
	   catch (SQLException e) {  System.out.println(" ERROR EN SENTENCIA SQL. GRABAR ");
	   System.out.println(" Mensaje: " + e.getMessage());
	   System.out.println(" Estado: " + e.getSQLState());
	   System.out.println(" Código: " + e.getErrorCode());
	   if (e.getErrorCode()==1400)
	   	   mensaje.setText("Se Intenta insertar NULL en columna NOT NULL.");  
	   if (e.getErrorCode()==1861)
	   	   mensaje.setText("Error en el formato de la fecha. Reg No Modificado.");   
		if (e.getErrorCode()==17002)
	         mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   

	   }
	} // fin if error!=1 
} // fin grabar
public void cargalistaBDdirec(Choice listadirec,JLabel nombredir, String[] nombredirec)
{
	try
	{
		 Class.forName("org.sqlite.JDBC");
		  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");  
	Statement sentencia = conexion.createStatement();
	ResultSet resul = sentencia.executeQuery ("SELECT Emp_no, APELLIDO FROM EMPLEADOS ");
	System.out.println ("-------------------------------------");
	System.out.println ("  CARGANDO EMPLEADOS PARA DIRECTORES ");
	System.out.println ("-------------------------------------");
	int emp=0;
	while (resul.next())
	{
	  listadirec.add(Integer.toString(resul.getInt(1))); // guardo núm de empleado
	  // guardo el nombre en un array según la posición del elemento, la posición la dice emp 
	  System.out.println ("indice emp:" +  emp + " director leido:" + resul.getInt(1));
	  nombredirec[emp] = resul.getString(2).toString();
	 // si el dato es nulo sale error java.lang.NullPointerException, por eso decode
	  emp=emp+1;
	}
	nombredir.setText( nombredirec[0]);	// para que se vea el primer reg
	resul.close();// Cerrar ResultSet
	sentencia.close();// Cerrar Statement
	conexion.close();//Cerrar conexion 
	} 
	catch (ClassNotFoundException cn) {
		   System.out.println ("ERRROOORRRRR ClassNotFoundException NO CARGA EL DRIVER");
		   cn.printStackTrace();} 
	catch (SQLException e) {
		   //e.printStackTrace();
		    System.out.println(" ERROR EN SENTENCIA SQL. ");
			System.out.println(" Mensaje: " + e.getMessage());
			System.out.println(" Estado: " + e.getSQLState());
			System.out.println(" Código: " + e.getErrorCode());
			if (e.getErrorCode()==17002)
		         mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   


		   } 
		 
}//fin de carga lista de directores	

public void cargalistaBD(Choice lista, JLabel nomd, String[] nombredepart2) {
	try
		{
		 Class.forName("org.sqlite.JDBC");
		  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");
		Statement sentencia = conexion.createStatement();
		ResultSet resul = sentencia.executeQuery ("SELECT * FROM departamentos");  
		System.out.println ("---------------------------------");
		System.out.println ("     CARGANDO DEPARTAMENTOS  ");
		System.out.println ("---------------------------------");
		int dep=0;
		while (resul.next())
		{
		   lista.add(Integer.toString(resul.getInt(1))); // guardo núm de depar en la lista
		   // guardo el nombre en un array según la posición del elemento, la posición la dice dep 
		  nombredepart2[dep] = resul.getString(2).toString(); 
		  dep=dep+1;
		}
	    nomd.setText( nombredepart2[0] );	// para que se vea el primer departamento
		resul.close();// Cerrar ResultSet
		sentencia.close();// Cerrar Statement
		conexion.close();//Cerrar conexion 
		} 
		catch (ClassNotFoundException cn) {
			   System.out.println ("ERRROOORRRRR ClassNotFoundException NO CARGA EL DRIVER");
			   cn.printStackTrace();} 
		catch (SQLException e) {
			   System.out.println ("ERRROOORRRRR SQLException");
		   //e.printStackTrace();
	      if (e.getErrorCode()==17002)
		        mensaje.setText("Error NO SE PUEDE ESTABLECER CONEXIÓN CON LA BD. Revisa la conexión.");   
} 
			 
	}// fin carga lista de departamentos


public void itemStateChanged(ItemEvent e) {
	int dep,emp;
	if (e.getSource() == listadepar)
		{ 
		 mensaje.setText(" has cambiado la lista de departamentos..");    
		 System.out.println (listadepar.getSelectedItem());
		 dep=listadepar.getSelectedIndex(); // posicion del elemento
		 System.out.println (nombredepart[dep]);
		 nomdep.setText(nombredepart[dep]); 
		}
	if (e.getSource() == listadirec)
	{ 
	 mensaje.setText(" has cambiado la lista de directores..");    
	 System.out.println (listadirec.getSelectedItem());
	 emp=listadirec.getSelectedIndex(); // posicion del elemento
	 System.out.println (nombredirec[emp]);
	 nomdir.setText(nombredirec[emp]);	
	}
}

}//fin clase
