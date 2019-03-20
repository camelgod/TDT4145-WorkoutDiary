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
                        "\n\nType number: ",
                "^[0-5]$",
                "Please provide a number between 0 and 5: "
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

        // TODO code application logic here
        DBConn connection = new DBConn();
        connection.connect();

        // Økt økt = new Økt(dato,tidspunkt,varighet,prestasjon);

        //Apparat app1 = new Apparat("Hei", "yo");
        //app1.save(connection.conn);


        //Økt økt = new Økt(20190318,1,5,9, 5, "mamma" );
        //økt.save(connection.conn);

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
						break;
                    }
                case 2:
                    while (true) {
                        int menu2Choice = t.getIntFromQuestion(
                                "Vil du:\n"
                                + "[0] Lage ny gruppe\n"
                                + "[1] Vis manuelle �velser i en bestemt gruppe\n"
                                + "[2] Vis apparat�velser i en bestemt gruppe",
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
            }
        }
    }
}
