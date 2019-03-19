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

public class Okt extends ActiveDomainObject {
private int id;
private int dato;
private int tidspunkt;
private int varighet;
private int form;
private int prestasjon;
private String notat;

public Okt(int date, int time, int varighet, int form ,int prestasjon, String notat) {
        this.dato = date;
        this.tidspunkt = time;
        this.varighet = varighet;
        this.prestasjon = prestasjon;
        this.form = form;
        this.notat = notat;
        //this.id = id;
        }

@Override
public void initialize (Connection conn) {
        try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select dato, tidspunkt, varighet, form, prestasjon, notat from Treningsøkt where id=" + id);
        while (rs.next()) {
        dato =  rs.getInt("dato");
        tidspunkt =  rs.getInt("tidspunkt");
        varighet = rs.getInt("varighet");
        prestasjon = rs.getInt("prestasjon");
        form = rs.getInt("form");
        notat = rs.getString("notat");
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
        "INSERT INTO `Treningsøkt` VALUES ("+id+","+dato+","+tidspunkt+","+varighet+","+form+","+prestasjon+","+"\""+notat+"\""+");");

        } catch (Exception e) {
        System.out.println("db error during insert of okt="+e);
        return;
        }
        }
}

