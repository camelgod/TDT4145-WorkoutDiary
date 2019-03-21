package workoutDiary;

import java.util.Scanner;

public class TreningsDagbok {
	public Scanner scan;

	public TreningsDagbok() {
		this.scan = new Scanner(System.in);
		System.out.println("Welcome to your workout diary!");
	}

	public int getMainMenuSelect() {
		return this.getIntFromQuestion(
				"This is the main menu." 
						+ "\nMenu:" 
						+ "\n[0] Quit" 
						+ "\n[1] Add workout" 
						+ "\n[2] Group" 
						+ "\n[3] Add exercise without equipment"
						+ "\n[4] Add exercise with equipment" 
						+ "\n[5] Add exercise to workout"
						+ "\n[6] Add equipment" 
						+ "\n[7] Get results log"
						+ "\n[8] Get performance from interval"
						+ "\n[9] Equipment number in a workout"
						+ "\n\nType number: ",
				"^[0-9]$", 
				"Please provide a number between 0 and 9: ");
	}

	public int getIntFromQuestion(String question, String format, String... args) {
		System.out.println("\n\n" + question);

		int selected;

		while (true) {
			String str = this.scan.nextLine();

			if (str.matches(format)) {
				selected = str.length() > 0 ? Integer.parseInt(str) : 0;
				break;
			}

			if (args.length > 0) {
				System.out.println(args[0]);
			} else {
				System.out.println(question);
			}
		}

		return selected;
	}

	public String getStringFromQuestion(String question, String format, String... args) {
		System.out.println("\n\n" + question);

		String selected;

		while (true) {
			String str = this.scan.nextLine();

			if (str.matches(format)) {
				selected = str.length() > 0 ? str : "";
				break;
			}

			if (args.length > 0) {
				System.out.println(args[0]);
			} else {
				System.out.println(question);
			}
		}

		return selected;
	}

	public static void main(String[] args) {

		DBConn connection = new DBConn();
		connection.connect();

		TreningsDagbok t = new TreningsDagbok();

		while (true) {
			int menu = t.getMainMenuSelect();

			switch (menu) {
			case 0:
				System.out.println("Bye!");
				System.exit(0);
			case 1:
				while (true) {
					String notat = t.getStringFromQuestion(
							"Note for exercise", 
							"^[A-Za-z ]+$", 
							"Skriv notat: ");
					int dato = t.getIntFromQuestion(
							"Date for exercise", 
							"^[0-9]+$", 
							"Dato for trening [YYYYMMDD] : "
							);
					int tidspunkt = t.getIntFromQuestion(
							"Time for exercise?", 
							"^[0-9]+$",
							"Dato for trening [HHMMSS]: "
							);
					int varighet = t.getIntFromQuestion(
							"Duration for exercise?", 
							"^[0-9]+$",
							"Varighet for trening [i minutter]: "
							);
					int form = t.getIntFromQuestion(
							"Form for exercise?", 
							"^[0-9]+$",
							"Hvordan er formen [Fra 1 til 10]: "
							);
					int prestasjon = t.getIntFromQuestion(
							"Performance for exercise?", 
							"^[0-9]+$",
							"Hvordan var prestasjonen [Fra 1 til 10]: "
							);

					Okt okt = new Okt(dato, tidspunkt, varighet, form, prestasjon, notat);
					okt.save(connection.conn);
					System.out.println("Saved okt successfully");
					break;
				}
			case 2:
				while (true) {
					int menu2Choice = t.getIntFromQuestion(
							"Vil du:\n" 
									+ "[0] Lage ny gruppe\n" 
									+ "[1] Vis manuelle øvelser i en bestemt gruppe\n"
									+ "[2] Vis apparatøvelser i en bestemt gruppe",
							"^[0-9]+$", "Velg: ");

					switch (menu2Choice) {
					case 0:
						String gruppeNavn = t.getStringFromQuestion(
								"Gruppenavn", 
								"^[A-Za-z\\s]+$", 
								"Skriv gruppenavn: ");
						String beskrivelse = t.getStringFromQuestion(
								"Beskrivelse", 
								"^[A-Za-z\\s]+$",
								"Skriv beskrivelse: ");

						Gruppe gruppe = new Gruppe(gruppeNavn, beskrivelse);
						gruppe.save(connection.conn);
						System.out.println("Group has been sucsessfully added");
						break;
					case 1:
						int gruppeValg = t.getIntFromQuestion(
								"Hvilken gruppe vil du vise?", 
								"^[0-999999]+$",
								"Velg gruppe [nummer]: ");
						System.out.println(Gruppe.getRelatedOvelserUtenApp(connection.conn, gruppeValg));
						break;
					case 2:
						int gruppe2Valg = t.getIntFromQuestion(
								"Hvilken gruppe vil du vise?", 
								"^[0-999999]+$",
								"Velg gruppe [nummer]: ");
						System.out.println(Gruppe.getRelatedOvelserMedApp(connection.conn, gruppe2Valg));
						break;
					default:
						break;
					}
					break;
				}
				break;
			case 3:
				while (true) {
					String navn = t.getStringFromQuestion(
							"Name for exercise", 
							"^[A-Za-z]+$", 
							"Skriv navn: ");
					String beskrivelse = t.getStringFromQuestion(
							"Description for exercise", 
							"^[A-Za-z]+$",
							"Skriv beskrivelse: ");

					OvelseUtenApp ov1 = new OvelseUtenApp(navn, beskrivelse);
					ov1.save(connection.conn);
					System.out.println("Saved ovelse successfully");
					break;
				}
				break;
			case 4:
				while (true) {
					String navn = t.getStringFromQuestion(
							"Name for exercise", 
							"^[A-Za-z ]+$", 
							"Skriv notat: ");
					String beskrivelse = t.getStringFromQuestion(
							"Description for exercise", 
							"^[A-Za-z ]+$",
							"Skriv notat: ");
					
					int apparatid = t.getIntFromQuestion("ApparatID?", "^[0-99999]+$");
					int antallkg = t.getIntFromQuestion("Kilos:", "^[0-9999]+$");
					int antallsett = t.getIntFromQuestion("Sets:", "^[0-9999]+$");

					OvelseMedApp ov2 = new OvelseMedApp(navn, beskrivelse, antallkg, antallsett, apparatid);
					ov2.save(connection.conn);
					System.out.println("Saved ovelse successfully");
					break;
				}
				break;
			case 5:
				while (true) {
					int isApparat = t.getIntFromQuestion("Does it have an equipment? [1] yes [2] no","^[1-2]+$");
					int oktid = t.getIntFromQuestion(
							"Write workout id", 
							"^[0-999]+$", 
							"Skriv notat: ");
					int ovelseid = t.getIntFromQuestion(
							"write exercise id", 
							"^[0-999]+$"
							 );
					if(isApparat == 1) {
						OvelseMedApp.addRelationOvelseOkt(connection.conn, oktid, ovelseid);
					} else {
						OvelseUtenApp.addRelationOvelseOkt(connection.conn, oktid, ovelseid);
						
					}
					System.out.println("Added exercise sucessfully");
					break;
				}
				break;
			case 6:
				while (true) {
					String navn = t.getStringFromQuestion(
							"Name for device", 
							"^[A-Za-z\\s]+$", 
							"Skriv notat: ");
					String beskrivelse = t.getStringFromQuestion(
							"Description for device", 
							"^[A-Za-z\\s]+$",
							"Skriv notat: ");

					Apparat a1 = new Apparat(navn, beskrivelse);
					a1.save(connection.conn);
					System.out.println("Saved apparat successfully");
					break;
				}
				break;
			case 7:
				while (true) {
					int navnInt = t.getIntFromQuestion(
							"[1]Øvelse med apparat eller "
							+ "[2]Øvelse uten apparat", 
							"[1-2]+$");
					String ovelseNavn = "";
					if (navnInt == 1) {
						System.out.println(OvelseMedApp.getOvelseMedApp(connection.conn));
						ovelseNavn = "OktOvelseMedApp";
					} else {
						System.out.println(OvelseUtenApp.getOvelseUtenAppList(connection.conn));
						ovelseNavn = "OktOvelseUtenApp";
					}
					System.out.println();
					int ovelseid = t.getIntFromQuestion(
							"OvelseID: ", 
							"^[0-99999999999]+$");
					int start = t.getIntFromQuestion(
							"Start date: ", 
							"^[0-99999999999]+$");
					int stop = t.getIntFromQuestion(
							"Stop date: ", 
							"^[0-99999999999]+$");

					System.out.println(Okt.getResultLog(connection.conn, start, stop, ovelseid, ovelseNavn));
					break;
				}
				break;
			case 8:
				while (true) {
					int oktid = t.getIntFromQuestion(
							"Write workout id: ", 
							"[0-999999]+$");
					
					System.out.println(Apparat.getApparatsInOkt(connection.conn, oktid));
					break;
				}
			}
		}
	}
}
