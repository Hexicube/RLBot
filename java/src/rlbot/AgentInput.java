package rlbot;

import java.util.ArrayList;

public class AgentInput {
    public final int blueScore;
    public final int orangeScore;
    public final int blueDemo;
    public final int orangeDemo;
    public final Vector ballPosition;
    public final Vector ballVelocity;
    public final Vector orangePosition;
    public final Vector bluePosition;
    public final CarRotation blueRotation;
    public final CarRotation orangeRotation;
    public final double orangeBoost;
    public final double blueBoost;
    public final Vector orangeVelocity;
    public final Vector blueVelocity;
    
    public AgentInput(ArrayList<ArrayList<Double>> input) {
        ArrayList<Double> neuralInputs = input.get(0);
        ArrayList<Double> scoring = input.get(1);
        
        blueScore = scoring.get(0).intValue();
        orangeScore = scoring.get(1).intValue();
        blueDemo = scoring.get(2).intValue();
        orangeDemo = scoring.get(3).intValue();
        
        ballPosition = new Vector(neuralInputs.get(7), neuralInputs.get(6), neuralInputs.get(2));
        ballVelocity = new Vector(neuralInputs.get(31), neuralInputs.get(32), neuralInputs.get(33));
        
        orangePosition = new Vector(neuralInputs.get(18), neuralInputs.get(17), neuralInputs.get(3));
        orangeVelocity = new Vector(neuralInputs.get(34), neuralInputs.get(35), neuralInputs.get(36));
        orangeRotation = new CarRotation(new Vector(neuralInputs.get(19), neuralInputs.get(25), neuralInputs.get(22)),
                                         new Vector(neuralInputs.get(21), neuralInputs.get(24), neuralInputs.get(27)));
        orangeBoost = neuralInputs.get(37);
        
        bluePosition = new Vector(neuralInputs.get(5), neuralInputs.get(4), neuralInputs.get(1));
        blueVelocity = new Vector(neuralInputs.get(28), neuralInputs.get(29), neuralInputs.get(30));
        blueRotation = new CarRotation(new Vector(neuralInputs.get(8), neuralInputs.get(14), neuralInputs.get(11)),
                                       new Vector(neuralInputs.get(10), neuralInputs.get(16),  neuralInputs.get(13)));
        blueBoost = neuralInputs.get(0);
    }
}
