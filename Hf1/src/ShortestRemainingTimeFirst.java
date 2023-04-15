import java.util.ArrayList;

public class ShortestRemainingTimeFirst {
    private ArrayList<Taszk> taszkok = new ArrayList<Taszk>(10);
    private Taszk aktív = null;
    private boolean futás = false;

    public void NincsPrio(int ütem) {
        if(futás){
            System.out.print(aktív.jel);
            futás = false;
        }
        for(Taszk taszk : taszkok) {
            if(taszk.start < ütem)
                taszk.várakozás++;
        }
    }

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
        char legkisebb;
        int minLöket;
        if(aktív == null) {
            legkisebb = '{';
            minLöket = taszkok.get(0).löket + 1;
        }
        else {
            legkisebb = '{';
            minLöket = aktív.löket;
        }
        for(Taszk taszk : taszkok) {
            if(taszk.start < ütem) {
                if(taszk.löket < minLöket) {
                    legkisebb = taszk.jel;
                    minLöket = taszk.löket;
                }
            }
        }
        if(legkisebb != '{' && aktív != null) {
            if(futás)
                System.out.print(aktív.jel);
            aktív = TaszkKeres(legkisebb);
        }
        if(aktív == null) {
            aktív = TaszkKeres(legkisebb);
        }
        aktív.löket--;

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
        }
        futás = true;
        return ret;
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
