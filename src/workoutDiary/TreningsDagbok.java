package workoutDiary;


import java.sql.*;
import java.sql.Date;
import java.util.*;


public class TreningsDagbok {

	public static void main(String[] args) {
		
        // TODO code application logic here
        DBConn connection = new DBConn();
        
        connection.connect();
        
        // Økt økt = new Økt(dato,tidspunkt,varighet,prestasjon);

        Apparat app1 = new Apparat("Hei", "yo");
        app1.save(connection.conn);
        
        
        Økt økt = new Økt(1,1,5,9);
        økt.save(connection.conn);
        

	}
}
