package event.plus.event.impl;

import event.plus.event.CancellableEvent;
import net.minecraft.util.math.Vec3d;

public class MoveEvent extends CancellableEvent {
    private double x;
    private double y;
    private double z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoveEvent(Vec3d vec) {
        this(vec.x, vec.y, vec.z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d toVec3d() {
        return new Vec3d(x, y, z);
    }
}