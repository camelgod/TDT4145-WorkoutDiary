package workoutDiary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select ØvelseNavn, Beskrivelse, AntallKg, AntallSett, ApparatID from Gruppe where id=" + id);
            while (rs.next()) {
                ovelseNavn = rs.getString("ØvelseNavn");
                beskrivelse = rs.getString("Beskrivelse");
                antallKg = rs.getFloat("AntallKg");
                antallSett = rs.getInt("AntallSett");
                appId = rs.getInt("ApparatID");
            }

        } catch (Exception e) {
            System.out.println("db error during select of ovelsemedapp= "+e);
            return;
        }

    }

    @Override
    public void refresh(Connection conn) {
        initialize (conn);
    }

    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `ØvelseMedApp` VALUES ("+id+","+"\""+ovelseNavn+"\""+","+"\""+beskrivelse+"\""+","+antallKg+", "+antallSett+","+appId+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseMedApp="+e);
            return;
        }

    }
}
