package rlbot;

public class ObjInfo {
    public final Vector pos, vel;
    public final CarRotation rot;
    public final double boost;
    
    public ObjInfo(Vector position, CarRotation rotation, Vector velocity, double boost) {
        pos = position;
        rot = rotation;
        vel = velocity;
        this.boost = boost;
    }
}