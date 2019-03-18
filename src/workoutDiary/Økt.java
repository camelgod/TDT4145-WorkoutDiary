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

public class Økt extends ActiveDomainObject {
    private int id;
    private int dato;
    private int tidspunkt;
    private int varighet;
    private int prestasjon;
    
    public Økt (int date, int time, int varighet, int prestasjon) {
        this.dato = date;
        this.tidspunkt = time;
        this.varighet = varighet;
        this.prestasjon = prestasjon;
    }
    
    @Override
    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select dato, tidspunkt, varighet, prestasjon from Økt where id=" + id);
            while (rs.next()) {
                dato =  rs.getInt("dato");
                tidspunkt =  rs.getInt("tidspunkt");
                varighet = rs.getInt("varighet");
                prestasjon = rs.getInt("prestasjon");
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
            stmt.executeUpdate(
            		"INSERT INTO `økt`"
            		+ "(`id`, `dato`, `tidspunkt`, `varighet`, `prestasjon`) "
            		+ "VALUES ("+id+","+dato+","+tidspunkt+","+varighet+","+prestasjon+");");

        } catch (Exception e) {
            System.out.println("db error during insert of Økt="+e);
            return;
        }
    }
}
