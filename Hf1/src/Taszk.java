public class Taszk {
    char jel;
    int prio;
    int start;
    int löket;
    int várakozás;
    boolean elkezdődött;
    boolean lefutott;
    int utoljárafutott;

    public Taszk(char jel, int prio, int start, int löket) {
        this.jel = jel;
        this.prio = prio;
        this.start = start;
        this.löket = löket;
        this.várakozás = 0;
        this.lefutott = false;
        utoljárafutott = 0;
        elkezdődött = false;
    }


    public void Kiir() {
        System.out.println(jel + "," + prio + "," + start + "," + löket);
    }
}
