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
    private int groupid;
    private int oktid;

    public OvelseMedApp(String ovelseNavn, String beskrivelse, float antallKg, int antallSett, int appId, int groupid, int oktid){
        this.ovelseNavn = ovelseNavn;
        this.beskrivelse = beskrivelse;
        this.antallKg = antallKg;
        this.antallSett = antallSett;
        this.appId = appId;
        this.groupid = groupid;
        this.oktid = oktid;
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

    public List<OvelseMedApp> getOvelseMedApp(Connection conn) {
        try {
            List<OvelseMedApp> ovelseMedAppList = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ØvelseMedApp" );
            while (rs.next()) {
                ovelseNavn = rs.getString("Ã˜velseNavn");
                beskrivelse = rs.getString("Beskrivelse");
                antallKg = rs.getFloat("AntallKg");
                antallSett = rs.getInt("AntallSett");
                appId = rs.getInt("ApparatID");
                groupid = rs.getInt("GruppeID");
                oktid = rs.getInt("ØktID");
                OvelseMedApp oma = new OvelseMedApp(ovelseNavn, beskrivelse, antallKg, antallSett, appId, groupid, oktid);
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
                    "INSERT INTO ØvelseMedApp VALUES ("+id+","+"\""+ovelseNavn+"\""+","+"\""+beskrivelse+"\""+","+antallKg+", "+antallSett+","+appId+","+groupid+","+oktid+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseMedApp="+e);
            return;
        }

    }
}
