package workoutDiary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class OvelseUtenApp extends ActiveDomainObject {

    private int id;
    private String ovelseNavn;
    private String beskrivelse;

    public OvelseUtenApp(String ovelseNavn, String beskrivelse){
        this.ovelseNavn = ovelseNavn;
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {
        return "OvelseUtenApp{" +
                "id=" + id +
                ", ovelseNavn='" + ovelseNavn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                '}';
    }

    public static List<OvelseUtenApp> getOvelseUtenAppList(Connection conn) {
        try {
            List<OvelseUtenApp>  ovelseList = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ØvelseUtenApp");
            while (rs.next()) {
                String ovelseNavn = rs.getString("ØvelseNavn");
                String beskrivelse = rs.getString("beskrivelse");
                OvelseUtenApp oua = new OvelseUtenApp(ovelseNavn, beskrivelse);
                ovelseList.add(oua);
            }
            return ovelseList;
        } catch (Exception e) {
            System.out.println("db error during select of OvelseUtenApp= "+e);
            return null;
        }
    }


    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `ØvelseUtenApp` VALUES ("+id+","+"\""+ovelseNavn+"\""+","+"\""+beskrivelse+"\""+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseUtenApp="+e);
            return;
        }

    }
    
    public static void addRelationOvelseOkt(Connection conn, int oktid, int ovelseid) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO OktOvelseUtenApp VALUES ("+oktid+","+ovelseid+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseMedApp="+e);
            return;
        }
    }
}
