package workoutDiary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class OvelseMedApp extends ActiveDomainObject {

    private int id;
    private String ovelseNavn;
    private String beskrivelse;
    private float antallKg;
    private int antallSett;
    private int appId;

    public OvelseMedApp(String ovelseNavn, String beskrivelse, float antallKg, int antallSett, int appId){
        this.ovelseNavn = ovelseNavn;
        this.beskrivelse = beskrivelse;
        this.antallKg = antallKg;
        this.antallSett = antallSett;
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "OvelseMedApp{" +
                "id=" + id +
                ", ovelseNavn='" + ovelseNavn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", antallKg=" + antallKg +
                ", antallSett=" + antallSett +
                ", appId=" + appId +
                '}';
    }

    public static List<OvelseMedApp> getOvelseMedApp(Connection conn) {
        try {
            List<OvelseMedApp> ovelseMedAppList = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ØvelseMedApp" );
            while (rs.next()) {
                String ovelseNavn = rs.getString("ØvelseNavn");
                String beskrivelse = rs.getString("Beskrivelse");
                float antallKg = rs.getFloat("AntallKg");
                int antallSett = rs.getInt("AntallSett");
                int appId = rs.getInt("ApparatID");
                OvelseMedApp oma = new OvelseMedApp(ovelseNavn, beskrivelse, antallKg, antallSett, appId);
                ovelseMedAppList.add(oma);
            }
            return ovelseMedAppList;

        } catch (Exception e) {
            System.out.println("db error during select of ovelsemedapp= "+e);
            return null;
        }

    }


    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO ØvelseMedApp VALUES ("+id+","+"\""+ovelseNavn+"\""+","+"\""+beskrivelse+"\""+","+antallKg+", "+antallSett+","+appId+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseMedApp="+e);
            return;
        }

    }
    
    public static void addRelationOvelseOkt(Connection conn, int oktid, int ovelseid) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO OktOvelseMedApp VALUES ("+oktid+","+ovelseid+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseMedApp="+e);
            return;
        }

    }
}
