/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutDiary;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Apparat extends ActiveDomainObject {
    private int id;
    private String navn;
    private String beskrivelse;
    
    public Apparat (String navn, String beskrivelse) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
    }
    
    @Override
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select navn, beskrivelse, id=" + id);
            while (rs.next()) {
                navn =  rs.getString("navn");
                beskrivelse =  rs.getString("beskrivelse");
            }

        } catch (Exception e) {
            System.out.println("db error during select of avtale= "+e);
            return;
        }
    
    }
    
    @Override
    public void refresh (Connection conn) {
        initialize (conn);
    }
    
    @Override
    public void save (Connection conn) {
        try {    
            Statement stmt = conn.createStatement(); 
            System.out.println(navn);
            stmt.executeUpdate(
            		"INSERT INTO `apparat` VALUES ("+id+","+navn+","+beskrivelse+");");

        } catch (Exception e) {
            System.out.println("db error during insert of Apparat="+e);
            return;
        }
    }
}
