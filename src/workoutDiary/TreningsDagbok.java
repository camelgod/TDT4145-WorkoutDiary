package workoutDiary;


import java.sql.Date;
import java.sql.Time;

public class TreningsDagbok {

	public static void main(String[] args) {
		
        // TODO code application logic here
        DBConn connection = new DBConn();
        
        connection.connect();
        
        //Okt okt = new Okt(dato,tidspunkt,varighet,prestasjon);


        Okt okt = new Okt(20190325, 12 ,22,9, 5, "Dette gikk bra");
        okt.save(connection.conn);


        Apparat app = new Apparat("Heia", "yo");
        app.save(connection.conn);

        Okt okt1 = new Okt(20190325, 13 ,22,9, 5, "Dette gikk bra");
        okt1.save(connection.conn);

        Gruppe gruppe = new Gruppe("Cardio", "Trenen hjertet");
        gruppe.save(connection.conn);

        OvelseMedApp ov = new OvelseMedApp("benkpress", "presse på en benk", 9, 20, 2);
        ov.save(connection.conn);

        OvelseUtenApp ovu = new OvelseUtenApp("Løpe ute", "Løpe ute");
        ovu.save(connection.conn);
	}
}
