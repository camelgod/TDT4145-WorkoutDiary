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
	public static List<Apparat> getApparatsInOkt(Connection conn, int oktid) {
		try {
			List<Apparat> oktNotatList = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT `Trenings�kt`.*, `OktOvelseMedApp`.*, `�velseMedApp`.*, `Apparat`.*\r\n" + 
							"FROM `Apparat`\r\n" + 
							"    LEFT JOIN `�velseMedApp` ON `�velseMedApp`.`ApparatID` = `Apparat`.`ApparatID`\r\n" + 
							"    LEFT JOIN `Trenings�kt` ON `�velseMedApp`.`�velseMedAppID` = `Trenings�kt`.`Trenings�ktID`\r\n" + 
							"    LEFT JOIN `OktOvelseMedApp` ON `OktOvelseMedApp`.`OktID` = `Trenings�kt`.`Trenings�ktID` WHERE `Trenings�kt`.`Trenings�ktID`= "+oktid+" ");

			while (rs.next()) {
				String appnavn = rs.getString("ApparatNavn");
				String appbeskrivelse = rs.getString("ApparatBeskrivelse");
				oktNotatList.add(new Apparat(appnavn, appbeskrivelse));
			}
			
			System.out.println(oktNotatList);
			return oktNotatList;

		} catch (Exception e) {
			System.out.println("db error during select of getresultlog= " + e);
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
