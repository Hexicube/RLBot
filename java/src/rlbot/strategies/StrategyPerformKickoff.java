package rlbot.strategies;

import rlbot.Bot.Team;
import rlbot.ObjInfo;
import rlbot.Vector;
import rlbot.strategies.StrategyController.StrategyOutput;

public class StrategyPerformKickoff extends Strategy {
    private boolean release = false;
    
    @Override
    public double getStrategyStrength(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        if(ball.pos.x == 0 && ball.pos.z == 0 && ball.vel.equals(new Vector(0,0,0))) return 999;
        return -99;
    }
    
    @Override
    public StrategyOutput handleStrategy(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team) {
        if(ball.pos.x != 0 || ball.pos.z != 0) release = true;
        else release = false;
        //TODO: make use of raw output to directly control for kickoffs
        //TODO: implement optimal kickoffs for all 3 positions
        return new StrategyOutput(myCar, new ObjInfo(ball.pos, null, null, 0));
    }
    
    @Override
    public boolean canRelease() {
        return release;
    }
}