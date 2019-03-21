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
						+ "\n[8] Equipment number in a workout"
						+ "\n[9] Get N Notes"
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
							"Notat for exercise: ", 
							"^[A-Za-z0-9]+$");
					int dato = t.getIntFromQuestion(
							"Date for exercise [YYYYMMDD]", 
							"^[0-9]+$");
					int tidspunkt = t.getIntFromQuestion(
							"Tid for trening [HHMMSS]: ",
							"^[0-9]+$");
					int varighet = t.getIntFromQuestion(
							"Varighet for trening [i minutter]: ",
							"^[0-9]+$");
					int form = t.getIntFromQuestion(
							"Hvordan er formen [Fra 1 til 9]: ",
							"^[0-9]+$");
					int prestasjon = t.getIntFromQuestion(
							"Hvordan var prestasjonen [Fra 1 til 9]: ",
							"^[0-9]+$");

					Okt okt = new Okt(dato, tidspunkt, varighet, form, prestasjon, notat);
					okt.save(connection.conn);
					System.out.println("Lagret okt\n");
					break;
				}
				break;
			case 2:
				while (true) {
					int menu2Choice = t.getIntFromQuestion(
							"Vil du:\n" 
									+ "[0] Lage ny gruppe\n" 
									+ "[1] Vis manuelle øvelser i en bestemt gruppe\n"
									+ "[2] Vis apparatøvelser i en bestemt gruppe\n"
									+ "[3] Legg til øvelser i en bestemt gruppe\n",
							"^[0-9]+$", "Velg: ");

					switch (menu2Choice) {
						case 0:
							String gruppeNavn = t.getStringFromQuestion(
									"Skriv gruppenavn: ",
									"^[A-Za-z\\s]+$");
							String beskrivelse = t.getStringFromQuestion(
									"Skriv beskrivelse", 
									"^[A-Za-z\\s]+$");

							Gruppe gruppe = new Gruppe(gruppeNavn, beskrivelse);
							gruppe.save(connection.conn);
							System.out.println("Group has been added");
							break;
						case 1:
							int gruppeValg = t.getIntFromQuestion(
									"Velg gruppe for å vise relaterte øvelser [nummer]:", 
									"^[0-999999]+$");
							System.out.println(Gruppe.getRelatedOvelserUtenApp(connection.conn, gruppeValg));
							break;
						case 2:
							int gruppe2Valg = t.getIntFromQuestion(
									"Velg gruppe for å vise relaterte øvelser[nummer]:", 
									"^[0-999999]+$");
							System.out.println(Gruppe.getRelatedOvelserMedApp(connection.conn, gruppe2Valg));
							break;

						case 3:
							while (true) {
								int isApparat = t.getIntFromQuestion("Har øvelsen apparat? [1] yes [2] no","^[1-2]+$");
								int groupid = t.getIntFromQuestion(
										"Write group id", 
										"^[0-999]+$");
								int ovelseid = t.getIntFromQuestion(
										"write exercise id", 
										"^[0-999]+$");
								if(isApparat == 1) {
									OvelseMedApp.addRelationOvelseGruppe(connection.conn, ovelseid, groupid);
								} else {
									OvelseUtenApp.addRelationOvelseGruppe(connection.conn, ovelseid, groupid);
									
								}
								System.out.println("Added exercise sucessfully");
								break;
							}
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
							"Skriv navn på øvelsen: ", 
							"^[A-Za-z]+$");
					String beskrivelse = t.getStringFromQuestion(
							"Beskrivelse på øvelsen: ", 
							"^[A-Za-z]+$");

					OvelseUtenApp ov1 = new OvelseUtenApp(navn, beskrivelse);
					ov1.save(connection.conn);
					System.out.println("Saved ovelse successfully");
					break;
				}
				break;
			case 4:
				while (true) {
					String navn = t.getStringFromQuestion(
							"Navn på øvelse: ", 
							"^[A-Za-z ]+$");
					String beskrivelse = t.getStringFromQuestion(
							"Beskrivelse på øvelse: ", 
							"^[A-Za-z ]+$");
					
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
							"Skriv ID til Økten: ", 
							"^[0-999]+$");
					int ovelseid = t.getIntFromQuestion(
							"Skriv ID til Øvelsen", 
							"^[0-999]+$");
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
							"Skriv navn på apparatet: ", 
							"^[A-Za-z\\s]+$");
					String beskrivelse = t.getStringFromQuestion(
							"Skriv beskrivelse for apparatet: ", 
							"^[A-Za-z\\s]+$");

					Apparat a1 = new Apparat(navn, beskrivelse);
					a1.save(connection.conn);
					System.out.println("Saved apparat successfully");
					break;
				}
				break;
			case 7:
				while (true) {
					int navnInt = t.getIntFromQuestion(
							"[1]Øvelse med apparat\n"
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
							"Start date [YYYYMMDD]: ", 
							"^[0-99999999999]+$");
					int stop = t.getIntFromQuestion(
							"Stop date [YYYYMMDD]: ", 
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
				break;
			case 9:
				// Get N Notes
				int antallnotater = t.getIntFromQuestion(
						"Amount of notes to display: ", 
						"[0-999999]+$");
				System.out.println(Okt.getNNotes(connection.conn, antallnotater));
				break;
			}
		}
	}
}