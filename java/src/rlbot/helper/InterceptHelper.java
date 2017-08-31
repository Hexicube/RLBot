package rlbot.helper;

import rlbot.GameConstants;
import rlbot.ObjInfo;
import rlbot.Vector;

public class InterceptHelper {
    public static Interception blueIntercept, orangeIntercept;
    
    public static class Interception {
        public final double interceptTime;
        public final Vector ballEndPos;
        
        private Interception(double time, Vector pos) {
            interceptTime = time;
            ballEndPos = pos;
        }
    }
    
    public static Interception getIntercept(ObjInfo car, ObjInfo ball) {
        Vector relativePos = Vector.sub(ball.pos, car.pos);
        double interceptTime = relativePos.length() / (car.boost > 0 ? GameConstants.Speeds.TOP_SPEED_BOOST : GameConstants.Speeds.TOP_SPEED_NORMAL);
        Vector finalPos = new Vector(ball.pos);
        Vector spd = new Vector(ball.vel);
        spd.y = 0;
        finalPos.add(spd.mul(interceptTime));
        finalPos.y = 0;
        
        return new Interception(interceptTime, finalPos);
    }
    
    public static double timeToFloor(Vector pos, Vector vel, double radius) {
        //TODO: work out when it will land
        return 0;
    }
}