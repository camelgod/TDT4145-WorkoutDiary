package workoutDiary;

import java.util.Scanner;

public class TreningsDagbok {
    public Scanner scan;

    public TreningsDagbok() {
        this.scan = new Scanner(System.in);
        System.out.println("Welcome to your workout diary!");
    }

    public int getMainMenuSelect () {
        return this.getIntFromQuestion(
                "This is the main menu." +
                        "\nMenu:" +
                        "\n[0] Quit" +
                        "\n[1] Session" +
                        "\n[2] Group" +
                        "\n[3] Workouts" +
                        "\n[4] Statistics" +
                        "\n[5] Seesions" +
                        "\n[6] Devices" +
                        "\n[7] Get performance from interval" +
                        "\n\nType number: ",
                "^[0-7]$",
                "Please provide a number between 0 and 7: "
        );
    }

    public int getIntFromQuestion (String question, String format, String ...args) {
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

    public String getStringFromQuestion (String question, String format, String ...args) {
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

        while(true) {
            int menu = t.getMainMenuSelect();

            switch(menu) {
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                case 1:
                    while (true) {
                        String notat = t.getStringFromQuestion(
                                "Note for exercise",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
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

						Okt okt = new Okt(dato,tidspunkt,varighet, form, prestasjon, notat);
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
                                "^[0-9]+$",
                                "Velg: "
                        );

                        switch(menu2Choice) {
                            case 0:
								String gruppeNavn = t.getStringFromQuestion(
										"Gruppenavn",
										"^[A-Za-z]+$",
										"Skriv gruppenavn: "
								);
								String beskrivelse = t.getStringFromQuestion(
										"Beskrivelse",
										"^[A-Za-z]+$",
										"Skriv beskrivelse: "
								);

								Gruppe gruppe = new Gruppe(gruppeNavn, beskrivelse);
								gruppe.save(connection.conn);
                            	break;
                            case 1:
								int gruppeValg = t.getIntFromQuestion(
										"Hvilken gruppe vil du vise?",
										"^[0-999999]+$",
										"Velg gruppe [nummer]: "
								);
								System.out.println(Gruppe.getRelatedOvelserUtenApp(connection.conn, gruppeValg));
								break;
                            case 2:
								int gruppe2Valg = t.getIntFromQuestion(
										"Hvilken gruppe vil du vise?",
										"^[0-999999]+$",
										"Velg gruppe [nummer]: "
								);
								System.out.println(Gruppe.getRelatedOvelserMedApp(connection.conn, gruppe2Valg));
								break;
							default:
								break;
                        }
                    }
                case 3:
                    while (true) {
                        String navn = t.getStringFromQuestion(
                                "Name for exercise",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
                        String beskrivelse = t.getStringFromQuestion(
                                "Description for exercise",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
                        int gruppeid = t.getIntFromQuestion(
                                "Group? (0 for none)",
                                "^[0-999999999]+$"
                        );
                        int oktid = t.getIntFromQuestion(
                                "Okt? (0 for none)",
                                "^[0-9999999999999]+$"
                        );

						OvelseUtenApp ov1 = new OvelseUtenApp(navn, beskrivelse, gruppeid, oktid);
						ov1.save(connection.conn);
						System.out.println("Saved ovelse successfully");
						break;
                    }
                case 4:
                    while (true) {
                        String navn = t.getStringFromQuestion(
                                "Name for exercise",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
                        String beskrivelse = t.getStringFromQuestion(
                                "Description for exercise",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
                        int gruppeid = t.getIntFromQuestion(
                                "Group?",
                                "^[0-9]+$"
                        );
                        int apparatid = t.getIntFromQuestion(
                                "Apparat?",
                                "^[0-9]+$"
                        );
                        int antallkg = t.getIntFromQuestion(
                                "Kilos:", 
                                "^[0-9]+$"
                        );
                        int antallsett = t.getIntFromQuestion(
                                "Sets:",
                                "^[0-9]+$"
                        );
                        int oktid = t.getIntFromQuestion(
                                "Oktid:",
                                "^[0-99999999]+$"
                        );

						OvelseMedApp ov2 = new OvelseMedApp(navn, beskrivelse, antallkg, antallsett, apparatid, gruppeid, oktid);
						ov2.save(connection.conn);
						System.out.println("Saved ovelse successfully");
						break;
                    }
                case 6:
                    while (true) {
                        String navn = t.getStringFromQuestion(
                                "Name for device",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );
                        String beskrivelse = t.getStringFromQuestion(
                                "Description for device",
                                "^[A-Za-z]+$",
                                "Skriv notat: "
                        );

						Apparat a1 = new Apparat(navn, beskrivelse);
						a1.save(connection.conn);
						System.out.println("Saved apparat successfully");
						break;
                    }
                case 7:
                    while (true) {
                        int start = t.getIntFromQuestion(
                                "Start date",
                                "^[0-99999999999]+$"
                        );
                        int stop = t.getIntFromQuestion(
                                "Stop date",
                                "^[0-99999999999]+$"
                        );
                        int okt = t.getIntFromQuestion(
                                "Okt ID",
                                "^[0-99999999999]+$"
                        );
                        
                        System.out.println(Okt.getResultLog(connection.conn, start, stop, okt));
						break;
                    }

            }
        }
    }
}
