package eu.kudan.ar;

import org.rajawali3d.math.Quaternion;

public class ModelPosition {
    private Quaternion quaternion;
    private double y = 0;
    private double x = 0;
    private double z = 0;

    public ModelPosition(Quaternion quaternion, double y, double x, double z) {
        this.quaternion = quaternion;
        this.y = y;
        this.x = x;
        this.z = z;
    }

    public ModelPosition() {
    }

    public Quaternion getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
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
