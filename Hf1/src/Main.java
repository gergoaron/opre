import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Taszk> taszkok = new ArrayList<Taszk>(10);
    private static ArrayList<Taszk> sorbanTaszkok = new ArrayList<Taszk>(10);
    private static RoundRobin RR = new RoundRobin();
    private static ShortestRemainingTimeFirst SRTF = new ShortestRemainingTimeFirst();

    private static int ütem = 0;
    public static void Kiir() {
        for (Taszk taszk : taszkok) {
            taszk.Kiir();
        }
    }

    public static Taszk TaszkKeres(char c) {
        for (Taszk taszk : taszkok) {
            if (taszk.jel == c) {
                return taszk;
            }
        }
        return null;
    }
    public static void VarakozasKiir() {
        int i = 0;
        for (Taszk taszk : sorbanTaszkok) {
            System.out.print(taszk.jel + ":" + taszk.várakozás);
            if(i < sorbanTaszkok.size() - 1)
                System.out.print(",");
            i++;
        }
    }

    public static boolean vanMárTaszk(char c) {
        for(Taszk taszk : sorbanTaszkok) {
            if(taszk.jel == c)
                return true;
        }
        return false;
    }

    public static boolean Lefutott() {
        for (Taszk taszk : taszkok) {
            if(!taszk.lefutott)
                return false;
        }
        return true;
    }

    public static boolean vanRR() {
        for(Taszk taszk : taszkok) {
            if(taszk.prio == 1 && !taszk.lefutott && taszk.start < ütem)
                return true;
        }
        return false;
    }


    public static void TaszkBeolvas() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if(parts.length != 4) {
                return;
            }
            char jel = parts[0].charAt(0);
            int prio = Integer.parseInt(parts[1]);
            int start = Integer.parseInt(parts[2]);
            int löket = Integer.parseInt(parts[3]);
            Taszk taszk = new Taszk(jel, prio, start, löket);
            taszkok.add(taszk);
            if(taszk.prio == 1) {
                RR.Add(taszk);
            }
            else {
                SRTF.Add(taszk);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        TaszkBeolvas();
        //Kiir();
        char c;
        while(!Lefutott()) {
            ütem++;
            if(vanRR()) {
                SRTF.NincsPrio(ütem);
                c = RR.Lép(ütem);
            }
            else {
                c = SRTF.Lép(ütem);
            }
            if(c != ' ') {
                if(!vanMárTaszk(c)) {
                    sorbanTaszkok.add(TaszkKeres(c));
                }
            }
        }
        System.out.println();
        VarakozasKiir();
    }

}