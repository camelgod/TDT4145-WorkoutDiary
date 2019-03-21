/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutDiary;


import java.sql.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Okt extends ActiveDomainObject {
	private int id;
	private int dato;
	private int tidspunkt;
	private int varighet;
	private int form;
	private int prestasjon;
	private String notat;

	public Okt(int date, int time, int varighet, int form, int prestasjon, String notat) {
		this.dato = date;
		this.tidspunkt = time;
		this.varighet = varighet;
		this.prestasjon = prestasjon;
		this.form = form;
		this.notat = notat;
		// this.id = id;
	}

	@Override
	public String toString() {
		return "Okt{" + "id=" + id + ", dato=" + dato + ", tidspunkt=" + tidspunkt + ", varighet=" + varighet
				+ ", form=" + form + ", prestasjon=" + prestasjon + ", notat='" + notat + '\'' + '}';
	}

	public List<Okt> getAllOkt(Connection conn) {
		try {
			List<Okt> oktList = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Treningsøkt ");
			while (rs.next()) {
				Date d1 = rs.getDate("dato");
				String patternDate = "yyyyMMdd";
				DateFormat df = new SimpleDateFormat(patternDate);
				String dateAsString = df.format(d1);
				int dato = Integer.valueOf(dateAsString);
				Time t1 = rs.getTime("tidspunkt");
				String patterTime = "HHmmss";
				DateFormat tf = new SimpleDateFormat(patterTime);
				String timeAsString = tf.format(t1);
				varighet = rs.getInt("varighet");
				prestasjon = rs.getInt("prestasjon");
				form = rs.getInt("form");
				notat = rs.getString("notat");
				Okt okt = new Okt(dato, tidspunkt, varighet, prestasjon, form, notat);
				oktList.add(okt);
			}
			System.out.println(oktList.toString());
			return oktList;
		} catch (Exception e) {
			System.out.println("db error during select of avtale= " + e);
			return null;
		}
	}

	@Override
	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO `Treningsøkt` VALUES (" + id + "," + dato + "," + tidspunkt + ","
					+ varighet + "," + form + "," + prestasjon + "," + "\"" + notat + "\"" + ");");

		} catch (Exception e) {
			System.out.println("db error during insert of okt=" + e);
			return;
		}
	}

	public static List<String> getResultLog(Connection conn, int start, int stop, int ovelseid, String ovelseNavn) {
		try {
			List<String> oktNotatList = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT "+ovelseNavn+" .*, `Treningsøkt`.* FROM `Treningsøkt` "
							+ "LEFT JOIN "+ovelseNavn+" ON "+ovelseNavn+".`OktID` = `Treningsøkt`.`TreningsøktID` WHERE "
							+ ""+ovelseNavn+".`OvelseID` = "+ovelseid+" AND `Treningsøkt`.`Dato` between '"+start+"' and '"+stop+"' ");
			
			while (rs.next()) {
				int prestasjon = rs.getInt("prestasjon");
				int form = rs.getInt("form");
				String notat = rs.getString("notat");
				oktNotatList.add("Prestasjon: " + prestasjon + ", Form: " + form + ", Notat: " + notat);
			}
			
			return oktNotatList;

		} catch (Exception e) {
			System.out.println("db error during select of getresultlog= " + e);
			return null;
		}
	}
	
	public List<String> getNNotes(Connection conn, int n) {
		try {
			List<String> oktNotatList = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Treningsøkt order by Dato desc, Tidspunkt desc limit " + n);
			while (rs.next()) {

				notat = rs.getString("notat");

				oktNotatList.add(notat);
			}
			System.out.println(oktNotatList);
			return oktNotatList;
		} catch (Exception e) {
			System.out.println("db error during select of avtale= " + e);
			return null;
		}
	}

}
