package eu.kudan.ar;

public class ObjectCorrection {
    private double scale = 1;
    private double y = 0;
    private double x = 0;
    private double z = 0;

    public ObjectCorrection(double scale, double y, double x, double z) {
        this.scale = scale;
        this.y = y;
        this.x = x;
        this.z = z;
    }

    public ObjectCorrection() {
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
