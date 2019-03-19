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
                        "\n[1] Apparater" +
                        "\n[2] Groups" +
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
                        String value = t.getStringFromQuestion(
                                "Hva heter mammaen din?",
                                "^[A-Za-zøæåØÆÅ]+$",
                                "Skrive et navn: "
                        );
                        int år = t.getIntFromQuestion(
                                "Hvilket år er mammaen din født?",
                                "^[0-9]+$",
                                "Skrive et år: "
                        );
                        if (value.length() > 0) {
                            Okt okt = new Okt(år,1,5,9, 5,"mamma heter: " + value );
                            okt.save(connection.conn);
                        }

                        String quit = t.getStringFromQuestion(
                                "Ferdig med å legge til? [Y/n]",
                                "^[YyNn]?$",
                                "Skriv Y eller N: "
                        );
                        if (quit.length() == 0 || quit.toLowerCase().equals("y")) {
                            break;
                        }
                    }
            }
        }
    }
}
