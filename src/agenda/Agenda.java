/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;
import java.sql.*;
import java.util.Scanner;


/**
 *
 * @author Valmore
 */
public class Agenda {

    /**
     * @param args the command line arguments
     */
    
    ////////////CREAMOS LA CONEXION CON LA BASE DE DATOS////////
    Connection con;
    public Agenda(){
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_agenda?serverTimezone=UTC","root","valmyBOCA2011");
        System.out.println("Se conecto a la base de datos");
    }
    catch(Exception e)
    {
        System.err.println("Error " + e);
    }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        Statement st;
        ResultSet rs;
        PreparedStatement ps;
        //Agenda mt = new Agenda();
        Scanner enter = new Scanner(System.in);
        String contacto,nombre,apellido,fechaNacimiento,email;
        int m,telefono,con;
        m = menu();
         while(m != 0){
             switch(m){
                 case 1:
                     mostrar();
                     mensaje();
                     pressAnyKeyToContinue();
                     m = menu();
                     break;
                 case 2:
                     System.out.println("Escriba el nombre o apellido del contacto :");
                     contacto = enter.next();
                     buscar(contacto);
                     pressAnyKeyToContinue();
                     m = menu();                     
                     break;
                 case 3:
                     System.out.println("Escriba nombre: ");
                     nombre = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba Apellido: ");
                     apellido = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba Fecha de Nacimiento en formato (yyyy-mm-dd): ");
                     fechaNacimiento = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba email: ");
                     email = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba numero de Telefono: ");
                     telefono = enter.nextInt();
                     pressAnyKeyToContinue();
                     
                     insertar(nombre,apellido,fechaNacimiento,email,telefono);
                     pressAnyKeyToContinue();
                     m = menu();
                     break;
                 case 4:
                     System.out.println("Elija id del Contacto a Modificar:");
                     con = enter.nextInt();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba nombre: ");
                     nombre = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba Apellido: ");
                     apellido = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba Fecha de Nacimiento en formato (yyyy-mm-dd): ");
                     fechaNacimiento = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba email: ");
                     email = enter.next();
                     pressAnyKeyToContinue();
                     
                     System.out.println("Escriba numero de Telefono: ");
                     telefono = enter.nextInt();
                     pressAnyKeyToContinue();
                     
                     Modificar(con,nombre,apellido,fechaNacimiento,email,telefono);
                     pressAnyKeyToContinue();
                     m = menu();
                     break;
                 case 5:
                     System.out.println("Elija id del Contacto a eliminar:");
                     con = enter.nextInt();
                     eliminar(con);
                     pressAnyKeyToContinue();
                     m = menu();
                     break;
                 default:
                     System.out.println("Valor no válido. Vuelva a intentarlo.");
                     pressAnyKeyToContinue();
                     m = menu();
            }
        }
        System.out.println("Hasta luego...");
        System.exit(0);
    }
    public static int menu (){
        Scanner enter = new Scanner(System.in);
        int s;
        System.out.println("-- AGENDA --");
        System.out.println("-- MENU --");        
        System.out.println("1- Todos los contactos");
        System.out.println("2- Buscar contacto");
        System.out.println("3- Agregar contacto");
        System.out.println("4- Editar contacto");
        System.out.println("5- Eliminar contacto");
        System.out.println("0 - Salir");
        System.out.println("Seleccionar:  ");
        s = enter.nextInt();
        return s;
    }
    static public void pressAnyKeyToContinue()
      { 
          String seguir;
          Scanner teclado = new Scanner(System.in);
          System.out.println("Press Enter key to continue...");
          try
            {
             seguir = teclado.nextLine();
            }   
         catch(Exception e)
          {}  
     }
    public static void mostrar(){
        Statement st;
        ResultSet rs;
        Agenda mt = new Agenda();
        
                       
                    try 
                    {
                        st = mt.con.createStatement();
                        rs = st.executeQuery("SELECT * FROM contactos");
                     while(rs.next())
                    {
                      //  String estado = rs.getDate("fechaNacimiento")  fecha ? "FELIZ CUMPLEAÑOS";
                        System.out.println(rs.getInt("idContacto") + " " + rs.getString("nombre") + " " + rs.getString("apellido") + "             " + rs.getDate("fechaNacimiento") + "       " + rs.getString("email") + "     " + rs.getInt("telefono"));
                    }
                     mt.con.close();
                    }         
                    catch (Exception e)
                    {
                        System.err.println("ERROR AL OBTENER LOS DATOS");
                    }
        
    }
    public static void mensaje(){
        Statement st;
        ResultSet rs;
        Agenda mt = new Agenda();
                            
                    try 
                    {
                        st = mt.con.createStatement();
                        String Q = "SELECT * from contactos WHERE day(fechaNacimiento)=day(NOW()) AND month(fechaNacimiento)=month(NOW());";
                        rs = st.executeQuery(Q);
                        
                        while(rs.next())
                        {
                        System.out.println(" Feliz cumpleaños " + rs.getString("nombre") + "  " + rs.getString("apellido"));
                        
                        }   
                         mt.con.close();   
                        }
                    catch (Exception e)
                    {
                        System.err.println("ERROR AL OBTENER LOS DATOS");
                    }
    }
    public static void buscar(String nombre){
        Statement st;
        ResultSet rs;
        Agenda mt = new Agenda();
                            
                    try 
                    {
                        st = mt.con.createStatement();
                        String Q = "SELECT * from contactos WHERE apellido LIKE '" + nombre + "'OR nombre LIKE '" + nombre + "';";
                        rs = st.executeQuery(Q);
                       
                        
                        while(rs.next())
                        {
                        System.out.println(rs.getInt("idContacto") + " " + rs.getString("nombre") + " " + rs.getString("apellido") + " " + rs.getDate("fechaNacimiento") + " " + rs.getString("email") + " " + rs.getInt("telefono"));
                        
                        }   
                         mt.con.close();   
                    }
                    catch (Exception e)
                    {
                        System.err.println("ERROR AL OBTENER LOS DATOS");
                    }
    } 
    public static void insertar(String name, String apellido, String fechaNacimiento, String email, int telefono){
        Statement st;
        PreparedStatement ps;
        Agenda mt = new Agenda();
                    try                 
                    {
                        String query = "insert into contactos(nombre,apellido,fechaNacimiento,email,telefono) " + "values (?,?,?,?,?)";   
                        ps = mt.con.prepareStatement(query);           
                        ps.setString(1,name);
                        ps.setString(2,apellido);
                        ps.setString(3,fechaNacimiento);
                        ps.setString(4,email);
                        ps.setInt(5,telefono);
                        ps.execute();
                        System.out.println("Datos Insertados...");
                        mt.con.close();
                    } 
                    catch (Exception e)
                    {
                    System.err.println("ERROR AL OBTENER LOS DATOS");
                    }
    }
    public static void eliminar(int con){
        Statement st;
        PreparedStatement ps;
        Agenda mt = new Agenda();
                    try 
                    {
                     String query = "delete from contactos where idContacto = ?;";   
                     ps = mt.con.prepareStatement(query);           
                     ps.setInt(1,con);
                     int resultRowCount = ps.executeUpdate();
                        if(resultRowCount > 0) 
                        {
                         System.out.println("La tarea se borro correctamente -> " + resultRowCount);
                        }
                        else    
                        {
                         System.out.println("La tarea NO se borro o no se encuentra en los registros");
                        }
                         mt.con.close();
                    } 
                    catch (Exception e)
                    {
                     System.err.println("ERROR AL OBTENER LOS DATOS");
                    }
    }
    public static void Modificar ( int IdContacto  , String name, String apellido, String fechaNacimiento, String email, int telefono){
        
        Statement st;
        PreparedStatement ps;
        Agenda mt = new Agenda();
        
        try 
        {
            st = mt.con.createStatement();
            String Q = "UPDATE contactos SET nombre =?,apellido =?, fechaNacimiento =?, email =?, telefono =? WHERE idContacto =? " ;
            
            ps = mt.con.prepareStatement(Q);
            ps.setString(1,name);
            ps.setString(2,apellido);
            ps.setString(3,fechaNacimiento);
            ps.setString(4,email);
            ps.setInt(5,telefono);
            ps.setInt(6,IdContacto);
            
            int resultRowCount = ps.executeUpdate();
           
            if(resultRowCount > 0) {
               System.out.println("La tarea se modifico correctamente -> " + resultRowCount);
            }else{
               System.out.println("La tarea NO se modifico");
            }
            
            mt.con.close();   
        } 
        
        catch (Exception e)
        {
            System.err.println("ERROR AL OBTENER LOS DATOS. " + e );
        }
    } 
}
