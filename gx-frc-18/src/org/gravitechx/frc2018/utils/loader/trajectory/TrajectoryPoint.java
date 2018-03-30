package org.gravitechx.frc2018.utils.loader.trajectory;

public class TrajectoryPoint {
    public int dt; // ms
    public float v; // m/s
    public float a; // m/s^2

    public TrajectoryPoint(float v, float a, int dt){
        this.v = v;
        this.a = a;
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "{v: " + v + " a: " + a + " dt: " + dt + "}";
    }
}
