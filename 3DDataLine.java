public class 3DDataLine {

    private int x, y, z;
    private double[][] concatenateMatrix;

    // constructor
    public 3DDataLine(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    // getters and setters for all private variables

    public int getx1() {
        return x1;
    }

    public void setx1(int x) {
        x1 = x;
    }

    public int gety1() {
        return y1;
    }

    public void sety1(int y) {
        y1 = y;
    }

    public int getz1() {
        return z1;
    }

    public void setz1(int z) {
        z1 = z;
    }

    public int getx2() {
        return x2;
    }

    public void setx2(int x) {
        x2 = x;
    }

    public int gety2() {
        return y2;
    }

    public void sety2(int y) {
        y2 = y;
    }

    public int getz2() {
        return z2;
    }

    public void setz2(int z) {
        z2 = z;
    }

}
