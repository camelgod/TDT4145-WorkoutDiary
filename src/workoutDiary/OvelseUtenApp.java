package workoutDiary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OvelseUtenApp extends ActiveDomainObject {

    private int id;
    private String ovelseNavn;
    private String beskrivelse;

    public OvelseUtenApp(String ovelseNavn, String beskrivelse){
        this.ovelseNavn = ovelseNavn;
        this.beskrivelse = beskrivelse;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select ØvelseNavn, beskrivelse from ØvelseUtenApp where id=" + id);
            while (rs.next()) {
                ovelseNavn = rs.getString("ØvelseNavn");
                beskrivelse = rs.getString("beskrivelse");
            }

        } catch (Exception e) {
            System.out.println("db error during select of OvelseUtenApp= "+e);
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
                    "INSERT INTO `ØvelseUtenApp` VALUES ("+id+","+"\""+ovelseNavn+"\""+","+"\""+beskrivelse+"\""+");");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelseUtenApp="+e);
            return;
        }

    }
}
