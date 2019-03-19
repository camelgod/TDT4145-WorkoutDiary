package workoutDiary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Gruppe extends ActiveDomainObject{

    private String gruppeNavn;
    private String beskrivelse;
    private int id;

    public Gruppe(String gruppeNavn, String beskrivelse){
        this.gruppeNavn = gruppeNavn;
        this.beskrivelse = beskrivelse;

    }
    @Override
    public void initialize(Connection conn) {

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select gruppeNavn, beskrivelse from Gruppe where id=" + id);
            while (rs.next()) {
                gruppeNavn = rs.getString("gruppeNavn");
                beskrivelse = rs.getString("beskrivelse");
            }

        } catch (Exception e) {
            System.out.println("db error during select of avtale= "+e);
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
                    "INSERT INTO `Gruppe` VALUES ("+id+","+"\""+gruppeNavn+"\""+","+"\""+beskrivelse+"\""+");");

        } catch (Exception e) {
            System.out.println("db error during insert of Gruppe="+e);
            return;
        }
    }
}
