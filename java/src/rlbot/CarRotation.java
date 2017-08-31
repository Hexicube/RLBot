package rlbot;

public class CarRotation {
    public Vector forward, up;
    
    public CarRotation(Vector nose, Vector roof) {
        forward = nose;
        up = roof;
    }
}
