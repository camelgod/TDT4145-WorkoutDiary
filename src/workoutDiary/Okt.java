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
    public String toString() {
        return "Okt{" +
                "id=" + id +
                ", dato=" + dato +
                ", tidspunkt=" + tidspunkt +
                ", varighet=" + varighet +
                ", form=" + form +
                ", prestasjon=" + prestasjon +
                ", notat='" + notat + '\'' +
                '}';
    }

    public List<Okt> getAllOkt(Connection conn){
            try {
                List<Okt> oktList = new ArrayList<>();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Treningsøkt ");
                while (rs.next()) {
                    dato =  rs.getInt("dato");
                    tidspunkt =  rs.getInt("tidspunkt");
                    varighet = rs.getInt("varighet");
                    prestasjon = rs.getInt("prestasjon");
                    form = rs.getInt("form");
                    notat = rs.getString("notat");
                    Okt okt = new Okt(dato, tidspunkt,varighet,prestasjon, form
                    , notat);
                    oktList.add(okt);
                }
                System.out.println(oktList.toString());
                return oktList;
            } catch (Exception e) {
                System.out.println("db error during select of avtale= "+e);
                return null;
            }
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


