import java.util.ArrayList;

public class RoundRobin {
    private ArrayList<Taszk> taszkok = new ArrayList<Taszk>(10);
    private int szelet = 2;

    private Taszk aktív = null;
    private int aktívIdő = 0;
    public boolean startKisebbMintÜtem(int ütem) {
        for(Taszk taszk : taszkok) {
            if(taszk.start < ütem) {
                return true;
            }
        }
        return false;
    }

    public char Lép(int ütem) {
        if(!startKisebbMintÜtem(ütem)) {
            return ' ';
        }

        if (aktív == null) {
            char legkisebb = '{';
            for (Taszk taszk : taszkok) {
                if (taszk.start < ütem) {
                    if(taszk.jel < legkisebb) {
                        legkisebb = taszk.jel;
                    }
                }
            }
            if(legkisebb == '{')
                return ' ';
            aktív = TaszkKeres(legkisebb);
            aktív.löket--;
            aktívIdő = 1;
        }
        else {
            if(aktívIdő < szelet) {
                aktívIdő++;
                aktív.löket--;
            }
            else {
                char aktívJel = aktív.jel;
                char legkisebb = '{';
                int maxUtoljára = -1;
                for(Taszk taszk : taszkok) {
                    if(taszk.start < ütem) {
                        if(taszk.utoljárafutott > maxUtoljára && taszk.jel != aktívJel) {
                            legkisebb = taszk.jel;
                            maxUtoljára = taszk.utoljárafutott;
                        }
                    }
                }
                if(legkisebb != '{') {
                    System.out.print(aktív.jel);
                    aktív = TaszkKeres(legkisebb);
                    aktív.utoljárafutott = 0;
                }
                aktív.löket--;
                aktívIdő = 1;
            }
        }

        for(Taszk taszk : taszkok) {
            if(taszk != aktív && taszk.start < ütem)
                taszk.várakozás++;
        }
        char ret = aktív.jel;
        //System.out.println(aktív.jel + " " + aktív.löket + " " + aktív.várakozás);
        if(aktív.löket == 0) {
            aktív.lefutott = true;
            System.out.print(aktív.jel);
            taszkok.remove(aktív);
            aktív = null;
            aktívIdő = 0;
        }
        return ret;
    }

    public boolean Üres() {
        return taszkok.isEmpty();
    }

    public Taszk TaszkKeres(char legkisebb) {
        for (Taszk taszk : taszkok) {
            if (taszk.jel == legkisebb) {
                return taszk;
            }
        }
        return null;
    }

    public void Add(Taszk taszk) {
        taszkok.add(taszk);
    }
}
