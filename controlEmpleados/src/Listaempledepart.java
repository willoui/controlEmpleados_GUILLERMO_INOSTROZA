

import java.sql.*;

import javax.swing.JFrame;
public class Listaempledepart {


public static void verdepartamentos() {

	   System.out.println ("------------------------------------------------");
	   System.out.println ("   DATOS BASE DE DATOS SQLITE. Ver departamentos");
	   System.out.println ("------------------------------------------------");
	
	 try
		 {
		  System.out.println ("NUM  NOMBRE        ESPECIALIDAD ");
	      System.out.println ("-------------------------------------");
		 Class.forName("org.sqlite.JDBC");
		  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");   
				   
		 // Preparamos la consulta
		   Statement sentencia = conexion.createStatement();
		   ResultSet resul = sentencia.executeQuery ("SELECT * FROM departamentos");  
				  
		 while (resul.next())
		   { 
		     System.out.println (resul.getInt(1) + "     " + resul.getString(2)+ "      " + 
				    		 resul.getString(3) );
		   }
		   resul.close();
		   sentencia.close();
		   conexion.close(); 
		  } 
		   catch (ClassNotFoundException cn) {
			   System.out.println ("ERRROOORRRRR ClassNotFoundException NO CARGA EL DRIVER");
			   cn.printStackTrace();} 
		   catch (SQLException e) {
			   System.out.println ("ERRROOORRRRR SQLException");
			   e.printStackTrace();} 
		 
	}//fin de mainSQLite
public static void verempleados() {

	   System.out.println ("------------------------------------------------");
	   System.out.println ("   DATOS BASE DE DATOS SQLITE. Ver empledos");
	   System.out.println ("------------------------------------------------");
	
	 try
	 {
		 Class.forName("org.sqlite.JDBC");
		  Connection conexion = DriverManager.getConnection ("jdbc:sqlite:ejemplo.db");   
		   Statement sentencia = conexion.createStatement();
		   ResultSet resul = sentencia.executeQuery (" SELECT emp_no, apellido, oficio, salario FROM empleados");  
		   System.out.println ("NUM      APELLIDO    OFICIO    SALARIO ");
		   System.out.println ("-----------------------------------------");
		   
		 while (resul.next())
		   {   System.out.println (resul.getInt(1) + " " + resul.getString(2)+ " " + 
				    		 resul.getString(3)+ "   " +  resul.getFloat(4));
		   }
		   resul.close();
		   sentencia.close();
		   conexion.close(); 
		  } 
		   catch (ClassNotFoundException cn) {
			   System.out.println ("ERRROOORRRRR ClassNotFoundException NO CARGA EL DRIVER");
			   cn.printStackTrace();} 
		   catch (SQLException e) {
			   System.out.println ("ERRROOORRRRR SQLException");
			   e.printStackTrace();} 
		 
	}//fin ver empleados
}//fin de la clase

