package rlbot.strategies;

import rlbot.Bot.Team;
import rlbot.helper.InterceptHelper;
import rlbot.strategies.StrategyController.StrategyOutput;
import rlbot.GameConstants;
import rlbot.ObjInfo;
import rlbot.Vector;

public class StrategyCloseDistance extends Strategy {
    private final double MIN_DIST   = GameConstants.Sizes.HALF_FIELD_LENGTH * 0.2,
                         MAX_DIST   = GameConstants.Sizes.HALF_FIELD_LENGTH,
                         IDEAL_DIST = GameConstants.Sizes.HALF_FIELD_LENGTH * 0.6,
                         STRAT_STRENGTH = 1;
    
    
    
    @Override
    public double getStrategyStrength(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        Vector dist = Vector.sub(myCar.pos, ball.pos);
        dist.y = 0;
        double distSq = dist.lenSq();
        if(distSq < (MIN_DIST * MIN_DIST)) return 0;
        if(distSq > (MAX_DIST * MAX_DIST)) return 0;
        
        double distReal = dist.length();
        double minVal, maxVal;
        if(distReal < IDEAL_DIST) {
            minVal = MIN_DIST;
            maxVal = IDEAL_DIST;
        }
        else {
            minVal = IDEAL_DIST;
            maxVal = MAX_DIST;
        }
        double str = (distReal - minVal) / (maxVal - minVal);
        if(distReal >= IDEAL_DIST) str = 1 - str;
        
        return str * STRAT_STRENGTH;
    }
    
    @Override
    public StrategyOutput handleStrategy(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        Vector finalPos;
        if(team == Team.BLUE) finalPos = InterceptHelper.blueIntercept.ballEndPos;
        else finalPos = InterceptHelper.orangeIntercept.ballEndPos;
        return new StrategyOutput(myCar, new ObjInfo(finalPos, null, null, 0));
    }
    
    @Override
    public boolean canRelease() {
        return true;
    }
}