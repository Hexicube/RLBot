package rlbot.strategies;

import rlbot.GameConstants;
import rlbot.Bot.Team;
import rlbot.CarRotation;
import rlbot.helper.InterceptHelper;
import rlbot.helper.InterceptHelper.Interception;
import rlbot.strategies.StrategyController.StrategyOutput;
import rlbot.ObjInfo;
import rlbot.Vector;

public class StrategyGoalTend extends Strategy {
    private final double ALLOWABLE_TIME_DIFF = -0.2;
    
    @Override
    public double getStrategyStrength(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        Interception myInter, otherInter;
        if(team == Team.BLUE) {
            myInter = InterceptHelper.blueIntercept;
            otherInter = InterceptHelper.orangeIntercept;
        }
        else {
            myInter = InterceptHelper.orangeIntercept;
            otherInter = InterceptHelper.blueIntercept;
        }
        double diff = otherInter.interceptTime - myInter.interceptTime;
        
        if(diff < ALLOWABLE_TIME_DIFF) return 99;
        else return -99;
    }
    
    @Override
    public StrategyOutput handleStrategy(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        Vector finalPos;
        if(team == Team.BLUE) finalPos = GameConstants.Locations.BLUE_GOAL;
        else finalPos = GameConstants.Locations.ORANGE_GOAL;
        return new StrategyOutput(myCar, new ObjInfo(finalPos, new CarRotation(finalPos.invert(true).normalize(false), new Vector(0, 1, 0)), Vector.ZERO, 20));
    }
    
    @Override
    public boolean canRelease() {
        return true;
    }
}