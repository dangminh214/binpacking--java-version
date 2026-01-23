package model.binpacking.greedy;

public class ToPlacePosition {

    private int cid;
    private double x;
    private double y;
    private Boolean shouldRotate; // nullable = optional

    public ToPlacePosition(int cid, double x, double y) {
        this.cid = cid;
        this.x = x;
        this.y = y;
        this.shouldRotate = null;
    }

    public ToPlacePosition(int cid, double x, double y, Boolean shouldRotate) {
        this.cid = cid;
        this.x = x;
        this.y = y;
        this.shouldRotate = shouldRotate;
    }

    public int getCid() {
        return cid;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Boolean getShouldRotate() {
        return shouldRotate;
    }
}
