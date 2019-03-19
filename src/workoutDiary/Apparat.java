package workoutDiary;

import java.sql.*;
import java.util.*;

public class Apparat extends ActiveDomainObject {
    private int id;
    private String navn;
    private String beskrivelse;
    
    public Apparat (String navn, String beskrivelse) {
        //this.id = id;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {
        return "Apparat{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                '}';
    }

    public List<Apparat> getAllApparat(Connection conn){
       try {
           List<Apparat> apparatList = new ArrayList<>();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("select * from Apparat" );
           while (rs.next()) {
               navn =  rs.getString("ApparatNavn");
               beskrivelse =  rs.getString("ApparatBeskrivelse");
               Apparat apparat = new Apparat(navn, beskrivelse);
               apparatList.add(apparat);
           }
            return apparatList;
       } catch (Exception e) {
           System.out.println("db error during select of avtale= "+e);
           return null;
       }

   }
    
    @Override
    public void save (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
            		"INSERT INTO `Apparat` VALUES ("+id+", " + "\""+navn+"\"" + "," + "\"" +beskrivelse+"\"" + ");");


        } catch (Exception e) {
            System.out.println("db error during insert of Apparat="+e);
            return;
        }
    }
}
