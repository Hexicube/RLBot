package rlbot.helper;

import rlbot.AgentOutput;
import rlbot.CarRotation;
import rlbot.ObjInfo;
import rlbot.Vector;

public class MovementActionLand extends MovementAction {
    private final Vector targetPos;
    public MovementActionLand(Vector target) {
        targetPos = target;
    }
    
    @Override
    public boolean isDone(ObjInfo car) {
        if(car.pos.y < 0.35) return true;
        return false;
    }
    
    @Override
    public AgentOutput doMovement(ObjInfo car) {
        double timeToLand = InterceptHelper.timeToFloor(car.pos, car.vel, 0);
        if(car.boost > 0 && timeToLand < 0.75) {
            return MovementHelper.getCorrectiveOutput(car.rot, new CarRotation(targetPos.sub(car.pos), new Vector(0, 1, 0)));
        }
        else {
            return MovementHelper.getCorrectiveOutput(car.rot, new CarRotation(new Vector(0, -1, 0), targetPos.sub(car.pos))).withBoost(true);
        }
    }
}